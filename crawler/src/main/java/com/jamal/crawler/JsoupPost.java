package com.jamal.crawler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * jsoup get 方式网页采集，以 CSDN 博客为例 https://jsoup.org/cookbook/input/parse-document-from-string
 * https://www.jianshu.com/p/d04bfd6b6146
 * https://www.jb51.net/article/159928.htm
 */
public class JsoupPost {
    public static void main(String[] args) throws Exception {
//        Map<String, String> cookies = login();
//        people(new HashMap<>());
//        httplogin();
        httplogin1();
    }

    public static void httplogin() throws Exception{
        RequestConfig config = RequestConfig.custom().setRedirectsEnabled(false).build();//不允许重定向

        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
//                .setDefaultRequestConfig(config)
                .build();
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("username","18870447531"));
        nvps.add(new BasicNameValuePair("password","zxh1993."));
        // https://accounts.douban.com/j/mobile/login/basic
        HttpUriRequest login = RequestBuilder.post()
                .setUri(new URI("https://accounts.douban.com/j/mobile/login/basic"))
                .setHeader("Upgrade-Insecure-Requests","1")
                .setHeader("Accept","application/json")
                .setHeader("Content-Type","application/x-www-form-urlencoded")
                .setHeader("X-Requested-With","XMLHttpRequest")
                .setHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .addParameter("username","18870447531")
                .addParameter("password","zxh1993.")
                .addParameter("remember","false")
                .addParameter("ticket","")
                .addParameter("ck","")
                .build();
        CloseableHttpResponse response2 = httpclient.execute(login);
        System.out.println(response2.getStatusLine().getStatusCode());
        try {
            HttpEntity entity = response2.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
               String body = EntityUtils.toString(entity, "utf-8");
               ByteArrayInputStream in = new ByteArrayInputStream(body.getBytes());
               //读取文件(字节流)
//                InputStream in = new FileInputStream("d:\\1.txt");
                //写入相应的文件
                OutputStream out = new FileOutputStream("test.html");
                //读取数据
                //一次性取多少字节
                byte[] bytes = new byte[2048];
                //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
                int n = -1;
                //循环取出数据
                while ((n = in.read(bytes,0,bytes.length)) != -1) {
                    //转换成字符串
                    String str = new String(bytes,0,n,"utf-8");
                    System.out.println(str);
                    //写入相关文件
                    out.write(bytes, 0, n);
                }
                //关闭流
                in.close();
                out.close();

            }

//            System.out.println("Post logon cookies:");
            List<Cookie> cookies = cookieStore.getCookies();
            if (cookies.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cookies.size(); i++) {
                    System.out.println("- " + cookies.get(i).toString());
                }
            }
        } finally {
            response2.close();
        }
    }
    
    public static void httplogin1() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://accounts.douban.com/j/mobile/login/basic");
        httpPost.setHeader("Accept","application/json");
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
        httpPost.setHeader("X-Requested-With","XMLHttpRequest");
        httpPost.setHeader("Upgrade-Insecure-Requests","1");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("username","18870447531"));
        nvps.add(new BasicNameValuePair("password","zxh1993."));
        nvps.add(new BasicNameValuePair("remember","false"));
        nvps.add(new BasicNameValuePair("ticket",""));
        nvps.add(new BasicNameValuePair("ck",""));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);
        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity = response2.getEntity();
            String body = EntityUtils.toString(entity, "utf-8");
            ByteArrayInputStream in = new ByteArrayInputStream(body.getBytes());
            //读取文件(字节流)
//                InputStream in = new FileInputStream("d:\\1.txt");
            //写入相应的文件
            OutputStream out = new FileOutputStream("test.html");
            //读取数据
            //一次性取多少字节
            byte[] bytes = new byte[2048];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n = -1;
            //循环取出数据
            while ((n = in.read(bytes,0,bytes.length)) != -1) {
                //转换成字符串
                String str = new String(bytes,0,n,"utf-8");
                System.out.println(str);
                //写入相关文件
                out.write(bytes, 0, n);
            }
            //关闭流
            in.close();
            out.close();
            // do something useful with the response body
            // and ensure it is fully consumed
        } finally {
            response2.close();
        }
    }
    public static Map<String, String> login()throws Exception{
        // 登录url
//        String login_url = "https://accounts.douban.com/passport/login";
//        String login_url = "https://www.douban.com/";
        String login_url = "https://accounts.douban.com/j/mobile/login/basic";

        // 先访问一遍，获取基础的cookie
//        Connection.Response res = Jsoup.connect(login_url).execute();
//        Map<String, String> cookies = res.cookies();
//        Map<String, String> headers = res.headers();
        // 登录数据
        Map<String,String> data = new HashMap<>();
        data.put("username","18870447531");
        data.put("password","zxh1993.");
        data.put("remember","false");
        Connection connection = Jsoup.connect("");
        Connection.Response login = Jsoup.connect(login_url)
                .followRedirects(true)
                .postDataCharset("utf-8")
                .header("Upgrade-Insecure-Requests","1")
                .header("Accept","application/json")
                .header("Content-Type","application/x-www-form-urlencoded")
                .header("X-Requested-With","XMLHttpRequest")
                .header("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .data(data)
                .method(Connection.Method.POST)
                .execute();
        login.charset("UTF-8");
//        res = Jsoup.connect(login_url).execute();
//        System.out.println(login.parse());
        System.out.println(login.cookies());
        return login.cookies();
//        Document document = login.parse();
//        System.out.println(document);
    }

    /**
     * 个人中心
     * @throws Exception
     */
    public static void people(Map<String, String> cookies) throws Exception{
//        System.out.println(cookies);
        String people_url = "https://my.oschina.net/u/3177126";
        Document document = Jsoup.connect(people_url)
                .header("Upgrade-Insecure-Requests","1")
//                .cookies(cookies)
                .header("Cookie","_user_behavior_=d5ade99d-1a31-4893-a12e-21a3bba248b8; Hm_lvt_a411c4d1664dd70048ee98afe7b28f0b=1566990154,1567172183,1567691820,1568209797; _reg_key_=u8TFjzhk8Oxhypn7RkUj; oscid=59JEDP2gKvyE5cpX1h5y%2BFYAB5rCZFz5qBZ6PM%2BHs0VhgS7F9qUZP%2F3qel%2F%2Ft4Sk7pP48OIRFxSJauT4Ec9AZbbKtpE%2BoFOb%2FVrbZs9xdX5qy2TRNI%2BTcDQgkGBQ3IkJtCkbHp3Z9Hdnu92PHvHwOkqFooRF1mnp; aliyungf_tc=AQAAAI07DxDvDgkApDzSt85YmT3GqBtv; Hm_lpvt_a411c4d1664dd70048ee98afe7b28f0b=1568209886\n")
                .get();
        System.out.println(document);
    }
}
