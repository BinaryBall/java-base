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
 * jsoup ip 代理
 */
public class HttpclientProxy {

    static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue(20);
    static String check_url = "http://2000019.ip138.com/";

    static String proxy_ip_url = "http://ip.jiangxianli.com/api/proxy_ip";

    static String proxy_ip_list_url = "http://ip.jiangxianli.com/api/proxy_ips?page=1";

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
