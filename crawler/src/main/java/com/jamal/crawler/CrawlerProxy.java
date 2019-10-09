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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class CrawlerProxy {

    /**
     * user agent 列表
     */
    static List<String> USER_AGENT = new ArrayList<String>(20) {
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
     * 随机获取user agent
     *
     * @return
     */
    public String getUserAgent() {
        Random random = new Random();
        int num = random.nextInt(USER_AGENT.size());
        return USER_AGENT.get(num);
    }

    /**
     * 获取ip代理
     *
     * @return
     */
    public Map<String, String> getProxyIp() throws IOException {

        // 每次能随机获取一个代理ip
        String proxyUrl = "http://ip.jiangxianli.com/api/proxy_ip";

        Map<String, String> map = new HashMap<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(proxyUrl);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "utf-8");
            // 将请求结果格式化成json
            JSONObject jsonObject = JSON.parseObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            String host = data.getString("ip");
            String port = data.getString("port");
            map.put("host", host);
            map.put("port", port);
        }
        return map;
    }


    /**
     * jsoup IP代理测试
     *
     * @param url
     * @return
     */
    public String testJsoupProxy(String url) {
        String result = "";
        try {

            // 获取ip代理池
            Map<String, String> proxy = getProxyIp();
            Connection connection = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .timeout(10000);

            // 如果代理不为空，则设置代理
            if (!proxy.isEmpty()) {
                connection.proxy(proxy.get("host"), Integer.parseInt(proxy.get("port")));
            }

            Connection.Response Response = connection.execute();
            if (Response.statusCode() == 200) {
                Document document = Response.parse();
                String info = document.select("body > p:nth-child(1)").first().ownText();
                result = info;
            } else {
                result = "IP代理不可用！！！";
            }
        } catch (Exception e) {
            result = "IP代理不可用！！！";
        }

        return result;

    }

    /**
     * 设置ip代理
     */
    public String httpclientProxy(String url) {
        String result = "ip 不可用";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            HttpHost proxy = null;
            // 获取ip代理池
            Map<String, String> proxyInfo = getProxyIp();
            if (!proxyInfo.isEmpty()) {
                proxy = new HttpHost(proxyInfo.get("host"), Integer.parseInt(proxyInfo.get("port")));
            }
            RequestConfig requestConfig = RequestConfig.custom()
                    .setProxy(proxy)
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectionRequestTimeout(3000)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity, "utf-8");
                Document document = Jsoup.parse(body);
                String info = document.select("body > p:nth-child(1)").first().ownText();

                result = info;
            }

        } catch (Exception e) {

        }

        return result;
    }


    /**
     * 从自己搭建的ip服务器上获取代理ip
     *
     * @return
     */
    public void getProxyInfo(ArrayBlockingQueue<String> queue) throws IOException {


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
     * 从自己搭建的ip服务器上获取代理ip
     *
     * @return
     */
    public String getOneProxyInfo() throws IOException {


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
     * 测试 jhao104 代理IP池
     *
     * @param url
     * @return
     */
    public String testJsoupByJhaoProxy(String url, ArrayBlockingQueue<String> queue) {
        String result = "";
        try {

            Connection connection = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .timeout(10000);

            // 如果代理ip队列为空，则重新获取ip代理
            if (queue.size() == 0) getProxyInfo(queue);
            // 如果代理不为空，则设置代理
            String[] proxy = queue.take().split(":");
            connection.proxy(proxy[0], Integer.parseInt(proxy[1]));

            Connection.Response Response = connection.execute();
            if (Response.statusCode() == 200) {
                Document document = Response.parse();
                String info = document.select("body > p:nth-child(1)").first().ownText();
                result = info;
            } else {
                result = "IP代理不可用！！！";
            }
        } catch (Exception e) {
            result = "IP代理不可用！！！";
        }

        return result;

    }

    /**
     * 豆瓣电影链接列表
     *
     * @return
     */
    public List<String> movieList() {
        // 获取60条电影链接
        String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=热门&sort=recommend&page_limit=40&page_start=0";
        List<String> movies = new ArrayList<>(500);
        try {
            String proxyInfo = getOneProxyInfo();
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            HttpHost proxy = null;
            if (StringUtils.isNotBlank(proxyInfo)) {
                System.out.println(proxyInfo);
                String[] proxyList = proxyInfo.split(":");
                proxy = new HttpHost(proxyList[0], Integer.parseInt(proxyList[1]));
            }
            httpGet.setHeader("User-Agent",getUserAgent());
            RequestConfig requestConfig = RequestConfig.custom()
                    .setProxy(proxy)
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectionRequestTimeout(3000)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = client.execute(httpGet);
            System.out.println(response.getStatusLine().getStatusCode());
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
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(100);

        // 该网站能够获取到你当前访问的ip
        String check_url = "http://2000019.ip138.com/";


        // 一次获取10个ip代理
        String proxy_ip_list_url = "http://ip.jiangxianli.com/api/proxy_ips?page=1";

        List<String> list = new ArrayList<>(20);
        try {
//            new CrawlerProxy().getProxyInfo(queue);
//
//            for (int i = 0; i < 50; i++) {
////                String result = new CrawlerProxy().testJsoupProxy(check_url);
//                String result = new CrawlerProxy().testJsoupByJhaoProxy(check_url, queue);
//                list.add(result);
//            }
//            for (int i = 0; i < 50; i++) {
//
//                System.out.println(list.get(i));
//            }
            CrawlerProxy crawlerProxy = new CrawlerProxy();
            crawlerProxy.getProxyInfo(queue);
            List<String> movies = crawlerProxy.movieList();

            //创建固定大小的线程池
            ExecutorService exec = Executors.newFixedThreadPool(2);
            for (String url : movies) {
                //执行线程
                exec.execute(new CrawlMovieThread(url, queue,crawlerProxy));
            }
            //线程关闭
            exec.shutdown();
//
//            for (int i = 0; i < movies.size(); i++) {
//                System.out.println(movies.get(i));
//            }
//            for (int i = 1; i < 10; i++) {
//                System.out.println(new CrawlerProxy().getUserAgent());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/**
 * 采集豆瓣电影线程
 */
class CrawlMovieThread extends Thread {
    // 待采集链接
    String url ;
    // 代理ip队列
    ArrayBlockingQueue<String> queue;
    // 代理类
    CrawlerProxy crawlerProxy;

    public CrawlMovieThread(String url, ArrayBlockingQueue<String> queue, CrawlerProxy crawlerProxy) {
        this.url = url;
        this.queue = queue;
        this.crawlerProxy = crawlerProxy;
    }

    public void run() {
        String proxy = null;
        try {
            Connection connection = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .timeout(50000);
            // 如果代理ip队列为空，则重新获取ip代理
            if (queue.size() == 0) crawlerProxy.getProxyInfo(queue);
            // 从队列中获取代理ip
            proxy = queue.take();
            // 解析代理ip
            String[] proxys = proxy.split(":");
            // 设置代理ip
            connection.proxy(proxys[0], Integer.parseInt(proxys[1]));
            // 设置 user agent
            connection.header("User-Agent", crawlerProxy.getUserAgent());
            Connection.Response Response = connection.execute();
            System.out.println("采集豆瓣电影，使用代理ip为：" + proxy + " ,返回状态码：" + Response.statusCode());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("采集豆瓣电影，使用代理ip为：" + proxy + " ,采集出异常");
        }
    }
}
