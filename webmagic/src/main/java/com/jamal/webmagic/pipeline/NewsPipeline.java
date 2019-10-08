package com.jamal.webmagic.pipeline;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * webmagic
 * 2019/10/8 14:02
 * 新闻数据 存入 mongodb
 *
 * @author
 **/
public class NewsPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {

        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }

    }
}
