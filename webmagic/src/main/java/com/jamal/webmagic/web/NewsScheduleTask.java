package com.jamal.webmagic.web;

import com.jamal.webmagic.processor.HuPuProcessor;
import com.jamal.webmagic.pipeline.NewsPipeline;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

/**
 * webmagic
 * 2019/10/8 15:04
 * 新闻定时任务
 *
 * @author
 **/
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class NewsScheduleTask {
    @Async
    @Scheduled(fixedDelay = 1000 * 60 * 5)  //间隔 5 分钟
    public void first() {
        try {
            Spider.create(new HuPuProcessor()).addPipeline(new NewsPipeline()).addUrl("https://voice.hupu.com/nba/1").thread(5).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
