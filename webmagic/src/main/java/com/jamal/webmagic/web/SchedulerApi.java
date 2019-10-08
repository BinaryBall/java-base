package com.jamal.webmagic.web;

import com.jamal.webmagic.processor.HuPuProcessor;
import com.jamal.webmagic.pipeline.NewsPipeline;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;

/**
 * webmagic
 * 2019/10/8 14:37
 * 调度api
 *
 * @author
 **/
@RestController
@RequestMapping(path = "/crawler")
public class SchedulerApi {

    @GetMapping(path = "/hupu")
    public String hupuNews() {
        String result = "启动成功";
        try {
            Spider.create(new HuPuProcessor()).addPipeline(new NewsPipeline()).addUrl("https://voice.hupu.com/nba/1").thread(5).start();
        } catch (Exception e) {
            result = "启动失败";
        }
        return result;
    }
}
