package com.jamal.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 网页采集 IP 被封，我该怎么办？
 * 以豆瓣电影为例
 *
 * 添加ip代理，免费的ip代理比较慢，性能底下
 * jsoup
 * httpclient
 * 伪造 User-Agent，每次请求随机设置一个 User-Agent
 * 定时任务不要有规律 不要每隔2分钟请求一次，可以设置一个范围 1-5分钟，每次间隔时间随机的
 *
 * 手写 ip 代理池
 *
 * crawler
 * 2019/9/29 16:34
 *
 * @author 曾小辉
 **/
public class CrawlerProxy {

    // 存放代理ip的队列
    static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(20);

    // 该网站能够获取到你当前访问的ip
    static String check_url = "http://2000019.ip138.com/";

    // 每次能随机获取一个代理ip
    static String proxy_ip_url = "http://ip.jiangxianli.com/api/proxy_ip";

    // 一次获取10个ip代理
    static String proxy_ip_list_url = "http://ip.jiangxianli.com/api/proxy_ips?page=1";

    // 异步加载 https://www.ip.cn/

    public static void main(String[] args){
        try {
            getIp();
            for (int i =0;i<30;i++) {
                proxy();
            }
        }catch (Exception e){
            proxy();
            e.printStackTrace();
        }


    }


    /**
     * 先通过免费ip接口，获取id，存入队列
     * @throws Exception
     */
    public static void getIp() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(proxy_ip_list_url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "utf-8");
            JSONObject jsonObject = JSON.parseObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray array = data.getJSONArray("data");

            for (int i=0;i<array.size();i++) {
                JSONObject json = array.getJSONObject(i);
                String ip = json.getString("ip");
                String port = json.getString("port");
                String ip_info = ip+","+port;
                queue.add(ip_info);
            }
        }
    }

    /**
     * 设置ip代理
     */
    public static void proxy(){
        try {
            if (queue.size() == 0) {
                return;
            }
            System.out.println("取ip：" + queue.peek() + ",还剩代理数：" + queue.size());
            /**
             * 从队列中获取ip
             */
            String[] ip_info = queue.take().split(",");

            Document document = Jsoup.connect(check_url)
                    .timeout(10000)
                    // 设置ip
                    .proxy(ip_info[0], Integer.parseInt(ip_info[1]))
                    .get();
            String info = document.select("body > p:nth-child(1)").first().ownText();

            System.out.println(info);
        }catch (Exception e){

        }

    }


    /**
     * 设置ip代理
     */
    public static void httpclientProxy(){
        try {
            if (queue.size() == 0) {
                return;
            }
            System.out.println("取ip：" + queue.peek() + ",还剩代理数：" + queue.size());
            /**
             * 从队列中获取ip
             */
            String[] ip_info = queue.take().split(",");
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(check_url);
            HttpHost proxy = new HttpHost(ip_info[0], Integer.parseInt(ip_info[1]));
            RequestConfig requestConfig = RequestConfig.custom()
                    .setProxy(proxy)
                    .setConnectTimeout(10000)
                    .setSocketTimeout(10000)
                    .setConnectionRequestTimeout(3000)
                    .build();
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity,"utf-8");
                Document document = Jsoup.parse(body);
                String info = document.select("body > p:nth-child(1)").first().ownText();

                System.out.println(info);
            }

        }catch (Exception e){

        }

    }

}
