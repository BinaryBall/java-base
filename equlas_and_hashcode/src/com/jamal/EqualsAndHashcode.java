package com.jamal;

import java.util.*;

/**
 * equlas_and_hashcode
 * 2019/9/26 13:43
 * equals hashcode
 *
 * @author
 **/
public class EqualsAndHashcode {
    public static void main(String[] args) {
        Article article = new Article("www.baidu.com","百度一下");
        Article article1 = new Article("www.baidu.com","坑B百度");

//        System.out.println(article);
//        System.out.println(article1);
//        System.out.println(article==article1);
//        System.out.println(article.equals(article1));
//
//        System.out.println(article.getTitle());
//        System.out.println(article1.getTitle());

        Set<Article> set = new HashSet<>();
        set.add(article);
        System.out.println(set.contains(article1));

    }
}

class Article{
    // 文章路径
    String url;

    // 文章标题
    String title;

    public Article(String url ,String title){
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 重写了equals方法，只要两篇文章的url相同就是同一篇文章
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || !(o instanceof  Article)) return false;
        Article article = (Article) o;
        return article.url.equals(url);
    }

    /**
     * 重写 hashcode方法，根据url返回hash值
     * @return
     */
    @Override
    public int hashCode() {
        return url.hashCode();
    }
}