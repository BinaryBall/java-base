package com.jamal.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Jsoup 页面解析demo
 */
public class JsoupDemo {
    public static void main(String[] args) throws Exception {

        String url = "https://blog.csdn.net/z694644032";
        Document doc = Jsoup.connect(url).get();
        // 获取标题 //*[@id="mainBox"]/main/div[2]/div[1]/h4/a
        System.out.println(doc.title());
        // 根据class选择
        Elements elements = doc.select(".article-list div h4 a");
        for (Element element:elements){
            // element <a href="https://blog.csdn.net/z694644032/article/details/100726266" target="_blank"> <span class="article-type type-1 float-none">原</span> 实现 Java 本地缓存，该从这几点开始 </a>
            // 获取a标签下所有的text，包括字标签的text
            String text = element.text();
            // 只获取自己的text，不包括子标签的text
            String ownText = element.ownText();
            // 获取觉得路劲
            String href = element.absUrl("href");
            // 获取标签的属性
            Attributes attributes = element.attributes();
            // 根据属性获取属性值
            String target = element.attr("target");
            System.out.println("a 标签的target："+target);
            String data = element.data();
            System.out.println("a 标签的data："+data);
            String className = element.className();
            System.out.println("a 标签的className ："+className);
            String baseUri = element.baseUri();
            System.out.println(attributes);
            System.out.println("a 标签的ownText："+ownText);
            System.out.println("a 标签的text："+text);
            System.out.println("a 标签的href："+href);
//            System.out.println("a 标签的uri："+baseUri);
        }
    }


}
