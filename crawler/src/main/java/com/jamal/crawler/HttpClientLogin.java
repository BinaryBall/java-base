package com.jamal.crawler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * httpclient 模拟登陆
 */
public class HttpClientLogin {
    public static void main(String[] args) throws Exception{
        login();
    }
    // 登陆接口
    static String login_url = "https://accounts.douban.com/j/mobile/login/basic";

    // 账号管理
    static String setting_url = "https://accounts.douban.com/passport/setting";

    public static void login() throws Exception{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpUriRequest login = RequestBuilder.post()
                .setUri(new URI(login_url))// 登陆url
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
        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity, "utf-8");
            System.out.println(body);
            // 请求账号管理
            HttpGet httpGet = new HttpGet(setting_url);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            HttpEntity entity1 = response1.getEntity();
            String body1 = EntityUtils.toString(entity1, "utf-8");
            System.out.println(body1);

        } finally {
            response.close();
        }
    }
}
