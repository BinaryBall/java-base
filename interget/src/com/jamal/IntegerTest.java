package com.jamal;

import java.util.HashMap;

/**
 * interget
 * 2019/9/28 13:01
 *
 * @author 曾小辉
 **/
public class IntegerTest {
    public static void main(String[] args) {

        Integer x = 3000;
        Integer y = 3000;
        System.out.println("x===y：" + (x == y));

        Integer xx = new Integer(3000);
        Integer yy = new Integer(3000);
        System.out.println("xx==yy：" + (xx == yy));

        int m = x;

        System.out.println(m);

        String nm = "-XX:AutoBoxCacheMax";
        Integer i = Integer.getInteger(nm);  //定义一个Integer型变量

        System.out.println(i);

        System.out.println(System.getProperties());

        Integer mm = 1000;
        int mmm = mm;

    }
}
