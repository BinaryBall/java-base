package com.jamal.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * jsoup get 方式网页采集，以 CSDN 博客为例 https://jsoup.org/cookbook/input/parse-document-from-string
 * https://www.jianshu.com/p/d04bfd6b6146
 */
public class JsoupPost {
    public static void main(String[] args) throws Exception {
        Map<String, String> cookies = login();
//        people(cookies);
    }

    public static Map<String, String> login()throws Exception{
        // 登录url
        String login_url = "https://accounts.douban.com/passport/login";

        // 先访问一遍，获取基础的cookie
        Connection.Response res = Jsoup.connect(login_url).execute();
        Map<String, String> cookies = res.cookies();
        Map<String, String> headers = res.headers();
        // 登录数据
        Map<String,String> data = new HashMap<>();
        data.put("username","188704");
        data.put("password","");
        Connection.Response login = Jsoup.connect(login_url)
                .followRedirects(false)
                .postDataCharset("UTF-8")
                .cookies(cookies)
                .headers(headers)
                .data(data)
                .execute();
        login.charset("UTF-8");
//        res = Jsoup.connect("https://www.douban.com/people/150968577/").execute();
        System.out.println(login.parse());
//        System.out.println(res.headers());
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
        String people_url = "https://www.douban.com/people/150968577/";
        Document document = Jsoup.connect(people_url)
                .header("Upgrade-Insecure-Requests","1")
                .cookies(cookies)
//                .header("cookie","bid=9SX3kiRWZ5A; _ga=GA1.2.1558638732.1564457063; __yadk_uid=3g3QDIM1CJJ4M5lVy4RiAjHVQDiqThVi; gr_user_id=0b6883be-8d32-4b59-b70f-5b6b489493d4; _vwo_uuid_v2=D8DEEC86C3F2EA3AC79CF1D54E093840D|bf6359dbed370335dd481c5f3754d484; viewed=\"26702824_26766807_25723064\"; push_noty_num=0; push_doumail_num=0; __utmv=30149280.15096; douban-fav-remind=1; ll=\"118160\"; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1568192673%2C%22https%3A%2F%2Fwww.google.com%2F%22%5D; _pk_ses.100001.8cb4=*; __utma=30149280.1558638732.1564457063.1567663268.1568192674.4; __utmc=30149280; __utmz=30149280.1568192674.4.4.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); ap_v=0,6.0; douban-profile-remind=1; __utmt=1; __utmb=30149280.11.10.1568192674; _pk_id.100001.8cb4=f2f185d40d2b1283.1566786181.4.1568194237.1567663359.; dbcl2=\"150968577:ol6snOdXGoE")
                .get();
        System.out.println(document);
    }
}
