package com.jamal.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * crawler
 * 2019/9/30 17:20
 * Java 爬虫性能太慢，试着更换你的架构
 * Java 爬虫利器 webmagic，大爷你想试试吗？
 * 都知道 webmagic 这些东西了，你还搞不定它？
 * 多线程爬虫
 * 采集 csdn 文章列表
 * 涉及到两个角色
 * 生产者：采集文章 url 存入阻塞队列
 * 消费者：从阻塞队列中取出 url 完成采集
 *
 * @author
 **/
public class CrawlerCloud {


    public static void main(String[] args) throws Exception {

        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(200);
        ExecutorService service = Executors.newCachedThreadPool();  //缓存线程池
        // 两个生产者
        service.execute(new Consumer(1, 4, queue));
        service.execute(new Consumer(4, 7, queue));

        // 五个消费者
        service.execute(new Producer(queue));
        service.execute(new Producer(queue));
        service.execute(new Producer(queue));
        service.execute(new Producer(queue));
        service.execute(new Producer(queue));
    }
}

/**
 * 生产者：维护待抓取 url 列表
 */
class Consumer implements Runnable {

    String CSDN__HOME_URL = "https://blog.csdn.net/shenziheng1/article/list/";

    ArrayBlockingQueue<String> queue;
    // 起始页
    int startPage;
    // 结束页
    int endPage;

    public Consumer(int start, int end, ArrayBlockingQueue<String> queue) {
        this.startPage = start;
        this.endPage = end;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = startPage; i < endPage; i++) {
                    // 我们需要拼接url，带上页码
                    String url = CSDN__HOME_URL + i;
                    Document doc = Jsoup.connect(url).get();
                    // 根据css选择器
                    Elements elements = doc.select(".article-list div h4 a");
                    // 模拟操作
                    Thread.sleep(1000);
                    // 存放链接
                    for (Element element : elements) {
                        System.out.println("生产者 " + Thread.currentThread().getName() + " 开始生产:" + element.absUrl("href"));
                        // 获取绝对路径
                        queue.add(element.absUrl("href"));

                    }
                }
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

/**
 * 消费者：消费队列中的 url
 */
class Producer implements Runnable {


    ArrayBlockingQueue<String> queue;

    public Producer(ArrayBlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + "开始消费任务---" + queue.take());//消费者从阻塞队列中消费一个随机数
                // 模拟操作
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

