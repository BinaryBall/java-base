package com.jamal.webmagic.processor;

import com.jamal.webmagic.pipeline.NewsPipeline;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * webmagic
 * 2019/10/8 10:31
 * 虎扑新闻
 *
 * @author 曾小辉
 **/
public class HuPuProcessor implements PageProcessor {

    // 详情页链接正则表达式
    public static final String URL_POST = "https://voice\\.hupu\\.com/nba/\\d+\\.html";
    // 列表页链接正则表达式，只匹配一个数字，即只会匹配到前9页
    public static final String URL_LIST = "https://voice\\.hupu\\.com/nba/\\d";

    private Site site = Site.me();

    @Override
    public void process(Page page) {

        // 匹配列表页
        if (!page.getUrl().regex(URL_POST).match()) {
            // 将详情页链接放到待采集的队列中
            page.addTargetRequests(page.getHtml().xpath("//div[@class=\"news-list\"]").links().regex(URL_POST).all());
            // 匹配列表页
//            page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
            //文章页
        } else {

            // 新闻标题
            String title = page.getHtml().xpath("//div[@class='artical-title']/h1[@class='headline']/text()").toString();
            // 来源
            String source = page.getHtml().xpath("//div[@class='artical-info']//span[@class='comeFrom']/a/text()").toString();

            // 发布时间
            String time = page.getHtml().xpath("//span[@id='pubtime_baidu']/text()").toString();

            // 发布内容
            String content = page.getHtml().xpath("//div[@class='artical-main-content']/tidyText()").toString();

            // 将结果保存到 ResultItems 中
            page.putField("title", title);
            page.putField("source", source);
            page.putField("time", time);
            page.putField("content", content);

            // 设置过滤条件，比如标题不能为空等
            if (page.getResultItems().get("title") == null) {
                //设置skip之后，这个页面的结果不会被Pipeline处理
                page.setSkip(true);
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        Spider.create(new HuPuProcessor()).addPipeline(new NewsPipeline()).setDownloader(new HttpClientDownloader()).addUrl("https://voice.hupu.com/nba/1").thread(5)
                .start();
    }
}
