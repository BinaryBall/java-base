package com.jamal.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 网页采集 IP 被封，我该怎么办？
 * Java 爬虫 IP 被封，不要怕，换个服务器试试
 * 以豆瓣电影为例
 * <p>
 * 添加ip代理，免费的ip代理比较慢，性能底下
 * jsoup
 * httpclient
 * 设置ip代理，免费代理不可用较多，平时测试测试还算可以
 * 伪造 User-Agent，每次请求随机设置一个 User-Agent
 * 定时任务不要有规律 不要每隔2分钟请求一次，可以设置一个范围 1-5分钟，每次间隔时间随机的
 * <p>
 * 搭建 ip 代理池
 * <p>
 * python ：https://github.com/jhao104/proxy_pool
 * crawler
 * 2019/9/29 16:34
 *
 * @author
 **/
public class CrawlerMovieProxy {

    /**
     * 常用 user agent 列表
     */
    static List<String> USER_AGENT = new ArrayList<String>(10) {
        {
            add("Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19");
            add("Mozilla/5.0 (Linux; U; Android 4.0.4; en-gb; GT-I9300 Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
            add("Mozilla/5.0 (Linux; U; Android 2.2; en-gb; GT-P1000 Build/FROYO) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
            add("Mozilla/5.0 (Windows NT 6.2; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
            add("Mozilla/5.0 (Android; Mobile; rv:14.0) Gecko/14.0 Firefox/14.0");
            add("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.94 Safari/537.36");
            add("Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
            add("Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");
            add("Mozilla/5.0 (iPod; U; CPU like Mac OS X; en) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/3A101a Safari/419.3");
        }
    };

    /**
     * 随机获取 user agent
     *
     * @return
     */
    public String randomUserAgent() {
        Random random = new Random();
        int num = random.nextInt(USER_AGENT.size());
        return USER_AGENT.get(num);
    }

    /**
     * 设置代理ip池
     *
     * @param queue 队列
     * @throws IOException
     */
    public void proxyIpPool(LinkedBlockingQueue<String> queue) throws IOException {


        // 每次能随机获取一个代理ip
        String proxyUrl = "http://192.168.99.100:5010/get_all/";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(proxyUrl);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "utf-8");

            JSONArray jsonArray = JSON.parseArray(body);
            int size = Math.min(100, jsonArray.size());
            for (int i = 0; i < size; i++) {
                // 将请求结果格式化成json
                JSONObject data = jsonArray.getJSONObject(i);
                String proxy = data.getString("proxy");
                queue.add(proxy);
            }
        }
        response.close();
        httpclient.close();
        return;
    }


    /**
     * 随机获取一个代理ip
     *
     * @return
     * @throws IOException
     */
    public String randomProxyIp() throws IOException {

        // 每次能随机获取一个代理ip
        String proxyUrl = "http://192.168.99.100:5010/get/";

        String proxy = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(proxyUrl);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "utf-8");
            // 将请求结果格式化成json
            JSONObject data = JSON.parseObject(body);
            proxy = data.getString("proxy");
        }
        return proxy;
    }

    /**
     * 豆瓣电影链接列表
     *
     * @return
     */
    public List<String> movieList(LinkedBlockingQueue<String> queue) {
        // 获取60条电影链接
        String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=热门&sort=recommend&page_limit=40&page_start=0";
        List<String> movies = new ArrayList<>(40);
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            // 设置 ip 代理
            HttpHost proxy = null;
            // 随机获取一个代理IP
            String proxy_ip = randomProxyIp();
            if (StringUtils.isNotBlank(proxy_ip)) {
                String[] proxyList = proxy_ip.split(":");
                System.out.println(proxyList[0]);
                proxy = new HttpHost(proxyList[0], Integer.parseInt(proxyList[1]));
            }
            // 随机获取一个请求头
            httpGet.setHeader("User-Agent", randomUserAgent());
            RequestConfig requestConfig = RequestConfig.custom()
                    .setProxy(proxy)
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectionRequestTimeout(3000)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = client.execute(httpGet);
            System.out.println("获取豆瓣电影列表，返回验证码：" + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity, "utf-8");
                // 将请求结果格式化成json
                JSONObject jsonObject = JSON.parseObject(body);
                JSONArray data = jsonObject.getJSONArray("subjects");
                for (int i = 0; i < data.size(); i++) {
                    JSONObject movie = data.getJSONObject(i);
                    movies.add(movie.getString("url"));
                }
            }
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return movies;
    }


    public static void main(String[] args) {
        // 存放代理ip的队列
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue(100);

        try {
            CrawlerMovieProxy crawlerProxy = new CrawlerMovieProxy();
            // 初始化ip代理队列
            crawlerProxy.proxyIpPool(queue);
            // 获取豆瓣电影列表
            List<String> movies = crawlerProxy.movieList(queue);

            //创建固定大小的线程池
            ExecutorService exec = Executors.newFixedThreadPool(5);
            for (String url : movies) {
                //执行线程
                exec.execute(new CrawlMovieProxyThread(url, queue, crawlerProxy));
            }
            //线程关闭
            exec.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/**
 * 采集豆瓣电影线程
 */
class CrawlMovieProxyThread extends Thread {
    // 待采集链接
    String url;
    // 代理ip队列
    LinkedBlockingQueue<String> queue;
    // 代理类
    CrawlerMovieProxy crawlerProxy;

    public CrawlMovieProxyThread(String url, LinkedBlockingQueue<String> queue, CrawlerMovieProxy crawlerProxy) {
        this.url = url;
        this.queue = queue;
        this.crawlerProxy = crawlerProxy;
    }

    public void run() {
        String proxy;
        String[] proxys = new String[2];
        try {
            Connection connection = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .timeout(50000);

            // 如果代理ip队列为空，则重新获取ip代理
            if (queue.size() == 0) crawlerProxy.proxyIpPool(queue);
            // 从队列中获取代理ip
            proxy = queue.poll();
            // 解析代理ip
            proxys = proxy.split(":");
            // 设置代理ip
            connection.proxy(proxys[0], Integer.parseInt(proxys[1]));
            // 设置 user agent
            connection.header("User-Agent", crawlerProxy.randomUserAgent());
            Connection.Response Response = connection.execute();
            System.out.println("采集豆瓣电影,返回状态码：" + Response.statusCode() + " ,请求ip：" + proxys[0]);
        } catch (Exception e) {
            System.out.println("采集豆瓣电影,采集出异常：" + e.getMessage() + " ,请求ip：" + proxys[0]);
        }
    }
}
