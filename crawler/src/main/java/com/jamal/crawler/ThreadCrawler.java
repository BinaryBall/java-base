package com.jamal.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 多线程爬虫
 */
public class ThreadCrawler implements Runnable {

    // 采集的文章数
    private final AtomicLong pageCount = new AtomicLong(0);

    // 列表页链接正则表达式
    public static final String URL_LIST = "https://voice.hupu.com/nba";

    protected Logger logger = LoggerFactory.getLogger(getClass());

    // 待采集的队列
    LinkedBlockingQueue<String> taskQueue;

    // 采集过的链接列表
    HashSet<String> visited;

    // 线程池
    CountableThreadPool threadPool;


    /**
     *
     * @param url 起始页
     * @param threadNum 线程数
     * @throws InterruptedException
     */
    public ThreadCrawler(String url, int threadNum) throws InterruptedException {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threadPool = new CountableThreadPool(threadNum);
        this.visited = new HashSet<>();
        this.taskQueue.put(url);
    }

    @Override
    public void run() {
        logger.info("Spider started!");
        while (!Thread.currentThread().isInterrupted()) {
            final String request = taskQueue.poll();

            // 如果获取 request 为空，并且当前的线程采已经没有线程在运行
            if (request == null) {
                if (threadPool.getThreadAlive() == 0) {
                    break;
                }
            } else {
                // 执行采集任务
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            processRequest(request);
                        } catch (Exception e) {
                            logger.error("process request " + request + " error", e);
                        } finally {
                            // 采集页面 +1
                            pageCount.incrementAndGet();
                        }
                    }
                });
            }
        }
        threadPool.shutdown();
        logger.info("Spider closed! {} pages downloaded.", pageCount.get());
    }

    /**
     * 处理采集请求
     * @param url
     */
    protected void processRequest(String url) {
        // 判断是否为列表页
        if (url.matches(URL_LIST)) {
            processTaskQueue(url);
        } else {
            processPage(url);
        }
    }


    /**
     * 处理链接采集
     * 处理列表页，将 url 添加到队列中
     *
     * @param url
     */
    protected void processTaskQueue(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            // 详情页链接
            Elements elements = doc.select(" div.news-list > ul > li > div.list-hd > h4 > a");
            elements.stream().forEach((element -> {
                String request = element.attr("href");
                // 判断该链接是否存在队列或者已采集的 set 中，不存在则添加到队列中
                if (!visited.contains(request) && !taskQueue.contains(request)) {
                    try {
                        taskQueue.put(request);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
            // 列表页链接
            Elements list_urls = doc.select("div.voice-paging > a");
            list_urls.stream().forEach((element -> {
                String request = element.absUrl("href");
                // 判断是否符合要提取的列表链接要求
                if (request.matches(URL_LIST)) {
                    // 判断该链接是否存在队列或者已采集的 set 中，不存在则添加到队列中
                    if (!visited.contains(request) && !taskQueue.contains(request)) {
                        try {
                            taskQueue.put(request);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析页面
     *
     * @param url
     */
    protected void processPage(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String title = doc.select("body > div.hp-wrap > div.voice-main > div.artical-title > h1").first().ownText();

            System.out.println(Thread.currentThread().getName() + " 在 " + new Date() + " 采集了虎扑新闻 " + title);
            // 将采集完的 url 存入到已经采集的 set 中
            visited.add(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            new ThreadCrawler("https://voice.hupu.com/nba", 5).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 线程池，来源 webmagic
 */
class CountableThreadPool {

    private int threadNum;

    private AtomicInteger threadAlive = new AtomicInteger();

    private ReentrantLock reentrantLock = new ReentrantLock();

    private Condition condition = reentrantLock.newCondition();

    public CountableThreadPool(int threadNum) {
        this.threadNum = threadNum;
        this.executorService = Executors.newFixedThreadPool(threadNum);
    }

    public CountableThreadPool(int threadNum, ExecutorService executorService) {
        this.threadNum = threadNum;
        this.executorService = executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public int getThreadAlive() {
        return threadAlive.get();
    }

    public int getThreadNum() {
        return threadNum;
    }

    private ExecutorService executorService;

    public void execute(final Runnable runnable) {


        if (threadAlive.get() >= threadNum) {
            try {
                reentrantLock.lock();
                while (threadAlive.get() >= threadNum) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                    }
                }
            } finally {
                reentrantLock.unlock();
            }
        }
        threadAlive.incrementAndGet();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {
                    try {
                        reentrantLock.lock();
                        threadAlive.decrementAndGet();
                        condition.signal();
                    } finally {
                        reentrantLock.unlock();
                    }
                }
            }
        });
    }

    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    public void shutdown() {
        executorService.shutdown();
    }


}


