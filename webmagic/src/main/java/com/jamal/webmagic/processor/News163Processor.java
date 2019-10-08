package com.jamal.webmagic.processor;

import com.jamal.webmagic.downloader.WebClientDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * webmagic
 * 2019/10/8 15:48
 *
 * @author
 **/
public class News163Processor implements PageProcessor {


    // https://news.163.com/19/1008/14/EQVM1OA30001899N.html

    // 详情页链接正则表达式
    public static final String URL_POST = "https://news\\.163\\.com/\\d+/\\d+\\/\\d+/\\S+.html";

    private Site site = Site.me();

    @Override
    public void process(Page page) {
        if (!page.getUrl().regex(URL_POST).match()) {
            page.addTargetRequests(page.getHtml().xpath("//div[@class='ndi_main']").links().regex(URL_POST).all());
        } else {
            page.putField("title", page.getHtml().xpath("//div[@id='epContentLeft']/h1/text()").toString());
            page.putField("source", page.getHtml().xpath("//div[@class='post_time_source']/text()").toString());
            page.putField("time", page.getHtml().xpath("//a[@id='ne_article_source']/text()").toString());
            page.putField("content",page.getHtml().xpath("//div[@class='post_text']/tidyText()").toString());
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    public static void main(String[] args) {
        Spider.create(new News163Processor()).addPipeline(new JsonFilePipeline("data/webmagic")).setDownloader(new WebClientDownloader("https://news\\.163\\.com/\\d+/\\d+\\/\\d+/\\S+.html")).addUrl("https://news.163.com/").thread(5)
                .start();
        System.out.println("https://news.163.com/19/1008/14/EQVM1OA30001899N.html".matches("https://news\\.163\\.com/\\d+/\\d+\\/\\d+/\\S+.html"));
    }
}
