package com.jamal.crawler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 网页采集遇到登录问题，我该怎么办？
 * 豆瓣个人主页为例
 * <p>
 * 1、手动设置cookies
 * <p>
 * 2、模拟登录
 * crawler
 * 2019/9/29 16:32
 *
 * @author 曾小辉
 **/
public class CrawleLogin {


    public static void main(String[] args) throws Exception {
        // 个人中心url
        String user_info_url = "https://www.douban.com/people/150968577/";

        // 登陆接口
        String login_url = "https://accounts.douban.com/j/mobile/login/basic";

//        new CrawleLogin().setCookies(user_info_url);
//        new CrawleLogin().jsoupLogin(login_url,user_info_url);
        new CrawleLogin().httpClientLogin(login_url,user_info_url);
    }

    /**
     * 手动设置 cookies
     * 先从网站上登录，然后查看 request headers 里面的 cookies
     * @param url
     * @throws IOException
     */
    public void setCookies(String url) throws IOException {

        Document document = Jsoup.connect(url)
                // 手动设置cookies
                .header("Cookie", "bid=NyqwcukiSHw; douban-fav-remind=1; __yadk_uid=7yuke9lL5hjpmXD5dry1qCGPPAFrvEge; trc_cookie_storage=taboola%2520global%253Auser-id%3D439917a7-a8eb-4fff-bd11-6717c9253b8f-tuct47cce2f; ll=\"118160\"; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1569747339%2C%22https%3A%2F%2Fcn.bing.com%2F%22%5D; _pk_ses.100001.8cb4=*; __utma=30149280.933949496.1568948318.1568948318.1569747345.2; __utmc=30149280; __utmz=30149280.1569747345.2.2.utmcsr=cn.bing.com|utmccn=(referral)|utmcmd=referral|utmcct=/; __utmt=1; dbcl2=\"150968577:bB7eiyTqA0s\"; ck=S0XP; ap_v=0,6.0; push_noty_num=0; push_doumail_num=0; __utmv=30149280.15096; douban-profile-remind=1; _pk_id.100001.8cb4=b366dc92e126c244.1568948315.2.1569747378.1568948315.; __utmb=30149280.5.10.1569747345")
                .get();
        if (document != null) {
            Element element = document.select(".info h1").first();
            if (element == null) {
                System.out.println("没有找到 .info h1 标签");
                return;
            }
            String userName = element.ownText();
            System.out.println("豆瓣我的网名为：" + userName);
        } else {
            System.out.println("出错啦！！！！！");
        }
    }

    /**
     * Jsoup 模拟登录豆瓣 访问个人中心
     * 在豆瓣登录时先输入一个错误的账号密码，查看到登录所需要的参数
     * 先构造登录请求参数，成功后获取到cookies
     * 设置request cookies，再次请求
     * @param loginUrl 登录url
     * @param userInfoUrl 个人中心url
     * @throws IOException
     */
    public void jsoupLogin(String loginUrl,String userInfoUrl)  throws IOException {

        // 登录数据
        Map<String,String> data = new HashMap<>();
        data.put("name","your_account");
        data.put("password","your_password");
        data.put("remember","false");
        data.put("ticket","");
        data.put("ck","");
        Connection.Response login = Jsoup.connect(loginUrl)
                .ignoreContentType(true) // 忽略类型验证
                .followRedirects(false) // 禁止重定向
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
        // login 中已经获取到登录成功之后的cookies
        // 构造访问个人中心的请求
        Document document = Jsoup.connect(userInfoUrl)
                // 取出login对象里面的cookies
                .cookies(login.cookies())
                .get();
        if (document != null) {
            Element element = document.select(".info h1").first();
            if (element == null) {
                System.out.println("没有找到 .info h1 标签");
                return;
            }
            String userName = element.ownText();
            System.out.println("豆瓣我的网名为：" + userName);
        } else {
            System.out.println("出错啦！！！！！");
        }
    }

    /**
     * httpclient 的方式模拟登录豆瓣
     * httpclient 跟jsoup差不多，不同的地方在于 httpclient 有session的概念
     * 在同一个httpclient 内不需要设置cookies ，会默认缓存下来
     * @param loginUrl
     * @param userInfoUrl
     */
    public void httpClientLogin(String loginUrl,String userInfoUrl) throws Exception{

        CloseableHttpClient httpclient = HttpClients.createDefault();


        HttpUriRequest login = RequestBuilder.post()
                .setUri(new URI(loginUrl))// 登陆url
                .setHeader("Upgrade-Insecure-Requests","1")
                .setHeader("Accept","application/json")
                .setHeader("Content-Type","application/x-www-form-urlencoded")
                .setHeader("X-Requested-With","XMLHttpRequest")
                .setHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                // 设置账号信息
                .addParameter("name","your_account")
                .addParameter("password","your_password")
                .addParameter("remember","false")
                .addParameter("ticket","")
                .addParameter("ck","")
                .build();
        // 模拟登陆
        CloseableHttpResponse response = httpclient.execute(login);

        if (response.getStatusLine().getStatusCode() == 200){
            // 构造访问个人中心请求
            HttpGet httpGet = new HttpGet(userInfoUrl);
            CloseableHttpResponse user_response = httpclient.execute(httpGet);
            HttpEntity entity = user_response.getEntity();
            //
            String body = EntityUtils.toString(entity, "utf-8");

            // 偷个懒，直接判断 缺心眼那叫单纯 是否存在字符串中
            System.out.println("缺心眼那叫单纯是否查找到？"+(body.contains("缺心眼那叫单纯")));
        }else {
            System.out.println("httpclient 模拟登录豆瓣失败了!!!!");
        }
    }
}
