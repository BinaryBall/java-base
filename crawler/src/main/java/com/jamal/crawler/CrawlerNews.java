package com.jamal.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 网页采集遇到数据 Ajax 异步加载，我该怎么办？
 * 采集网易新闻
 * <p>
 * 网易新闻采用的是 ajax 的方式加载的
 * <p>
 * 新闻连接js ：
 * https://temp.163.com/special/00804KVA/cm_yaowen.js?callback=data_callback
 * https://temp.163.com/special/00804KVA/cm_yaowen_03.js?callback=data_callback
 * https://temp.163.com/special/00804KVA/cm_yaowen_04.js?callback=data_callback
 * <p>
 * <p>
 * <p>
 * <p>
 * crawler
 * 2019/9/29 10:41
 *
 * @author 曾小辉
 **/
public class CrawlerNews {

    public static void main(String[] args) throws Exception {
//        String url = "https://news.163.com/";
//        new CrawlerNews().jsoupMethod(url);

        String url1 = "https://temp.163.com/special/00804KVA/cm_yaowen.js?callback=data_callback";
        new CrawlerNews().httpclientMethod(url1);
    }

    /**
     * 使用反向解析法 解决数据异步加载的问题
     *
     * @param url
     */
    public void httpclientMethod(String url) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String body = EntityUtils.toString(entity,"GBK");
            // 先替换掉最前面的 data_callback(
            body = body.replace("data_callback(","");
            // 过滤掉最后面一个 ）右括号
            body = body.substring(0,body.lastIndexOf(")"));
            // 将 body 转换成 JSONArray
            JSONArray jsonArray = JSON.parseArray(body);
            for (int i = 0 ;i< jsonArray.size();i++){
                JSONObject data = jsonArray.getJSONObject(i);
                System.out.println("文章标题：" + data.getString("title") + " ,文章链接：" + data.getString("docurl"));
            }
        } else {
            System.out.println("处理失败！！！返回状态码：" + response.getStatusLine().getStatusCode());
        }

    }

    /**
     * jsoup + WebClient 解决数据异步加载问题
     *
     * @param url
     * @throws Exception
     */
    public void jsoupMethod(String url) throws Exception {

        /**
         * WebClient 的基本设置
         */
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //支持JavaScript
        webClient.getOptions().setCssEnabled(false);//禁用css支持
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setTimeout(3 * 1000);
        webClient.getOptions().setUseInsecureSSL(true);
        HtmlPage page = webClient.getPage(url);

        webClient.waitForBackgroundJavaScript(1000 * 6);
        String html = page.asXml();

        Document document = Jsoup.parse(html);

        Elements elements = document.select(".ndi_main .news_title h3 a");
        for (Element element : elements) {
            String article_url = element.attr("href");
            String title = element.ownText();
            if (article_url.contains("https://news.163.com/")) {
                System.out.println("文章标题：" + title + " ,文章链接：" + article_url);
            }
        }
    }
}
