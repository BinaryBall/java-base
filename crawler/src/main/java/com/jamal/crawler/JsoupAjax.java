package com.jamal.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * jsoup+ htmlunit 异步数据
 */
public class JsoupAjax {

//    static String url = "https://www.ip.cn/";
    static String url = "https://item.jd.com/12240763.html";

    public static void main(String[] args) {
        getIp();
    }
    public static void getIp(){
        try {
            WebClient webClient = new WebClient(BrowserVersion.CHROME);
            //支持JavaScript
            webClient.getOptions().setJavaScriptEnabled(true);//启用JS解释器，默认为true
            webClient.getOptions().setCssEnabled(false);//禁用css支持
            webClient.getOptions().setActiveXNative(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(30*1000);
            webClient.getOptions().setUseInsecureSSL(true);

            HtmlPage page = webClient.getPage(url);
            //设置一个运行JavaScript的时间
            webClient.waitForBackgroundJavaScript(1000*60*2);
            String html = page.asXml();
            Document document = Jsoup.parse(html);
            System.out.println("--------------------------------");
//            System.out.println(document);//#jd-price *[@id="jd-price"]/text()
            Elements elements = document.select("#jd-price");
            for (Element element:elements){
                System.out.println(element);
            }
//            String ip = document.select("#result > div > p:nth-child(1) > code").text();
//            System.out.println("价格："+ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
