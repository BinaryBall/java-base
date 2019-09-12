package com.jamal.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;


/**
 * jsoup 模拟登陆豆瓣
 */
public class JsoupLogin {

    // 登录url
    static String login_url = "https://accounts.douban.com/j/mobile/login/basic";
    // 账号管理
    static String setting_url = "https://accounts.douban.com/passport/setting";

    public static void main(String[] args) throws Exception {
        Map<String, String> cookies = login();
        getSettingPage(cookies);
    }

    /**
     * 模拟登陆
     * @return
     * @throws Exception
     */
    public static Map<String, String> login()throws Exception{

        // 登录数据
        Map<String,String> data = new HashMap<>();
        data.put("name","your_account");
        data.put("password","your_password");
        data.put("remember","false");
        data.put("ticket","");
        data.put("ck","");
        Connection.Response login = Jsoup.connect(login_url)
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
        System.out.println(login.parse());
        System.out.println(login.cookies());
        return login.cookies();
    }


    /**
     * 获取账号管理页面
     * @param cookies login登陆成功后返回的cookies
     * @throws Exception
     */
    public static void getSettingPage(Map<String, String> cookies) throws Exception{
        Document document = Jsoup.connect(setting_url)
                .header("Upgrade-Insecure-Requests","1")
                .cookies(cookies)
                .get();
        System.out.println(document);
    }
}
