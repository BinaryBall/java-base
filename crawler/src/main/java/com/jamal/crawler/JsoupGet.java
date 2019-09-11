package com.jamal.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * jsoup get 方式网页采集，以 CSDN 博客为例
 */
public class JsoupGet {
    public static void main(String[] args) throws Exception {
        /**
         *最后面的1表示页码，当我们需要采集多页时，我们需要这个地方是动态的
         */
//        String CSDN__HOME_URL = "https://blog.csdn.net/z694644032/article/list/1?";
        String CSDN__HOME_URL = "https://blog.csdn.net/z694644032/article/list/";
        for (int i=1;i<3;i++){
            // 我们需要拼接url，带上页码
            String url = CSDN__HOME_URL+i;
            List<String> links = paserListPage(url);
            for (String link:links){
                paserDetailPage(link);
            }
        }
    }

    /**
     * CSDN__HOME_URL是文章列表页，获取详情页的链接
     * @throws Exception
     */
    public static List<String> paserListPage(String link) throws Exception{
        Document doc = Jsoup.connect(link).get();
        // 根据css选择器
        Elements elements = doc.select(".article-list div h4 a");
//        Elements elements = doc.select("#mainBox > main > div.article-list > div > h4 > a");
        // 存放链接
        List<String> links = new ArrayList<>(30);
        for (Element element:elements){
            // 获取绝对路径
            links.add(element.absUrl("href"));
        }

        return links;
    }

    /**
     * 处理详情页，获取如下几个字段
     * 标题 title
     * 时间 date
     * 作者 author
     * 内容 content
     * @param detailUrl
     */
    public static void paserDetailPage(String detailUrl) throws Exception{
        Document document = Jsoup.connect(detailUrl).get();
        // 文章标题
        String title = document.select("div.article-title-box > h1.title-article").first().ownText();
        // 文章发布时间
        String date = document.select("span.time").first().ownText();
        // 文章作者
        String author = document.select("a.follow-nickName").first().ownText();
        // 文章正文内容
        String content = document.select("div#content_views").first().text();

        System.out.println("采集文章，标题："+title+" ,发布时间："+date+" ,作者："+author);

//        System.out.println("采集文章，标题："+title+" ,发布时间："+date+" ,作者："+author+" ,正文内容："+content);

    }
}
