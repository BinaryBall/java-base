package com.jamal.webmagic.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * webmagic
 * 2019/10/8 14:56
 * 新闻类
 *
 * @author
 **/

@Data
@ToString
public class NewsEntity implements Serializable {


    // 新闻标题
    private String title;
    // 新闻来源
    private String source;
    // 新闻发布时间
    private String time;
    // 新闻内容
    private String content;
}
