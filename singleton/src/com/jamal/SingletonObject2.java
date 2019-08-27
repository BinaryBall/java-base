package com.jamal;

/**
 * 实现了懒加载，但是在多线程的情况下不安全，有可能会产生多份实例，
 */
public class SingletonObject2 {
    private static SingletonObject2 instance;

    private SingletonObject2(){

    }

    public static SingletonObject2 getInstance(){
        /**
         * 两个线程同时执行到这里，都判断为空，线程1放弃了CPU执行权，线程2获取CPU执行权，new了一个实例，
         * 线程1在获取CPU执行权，再次new了一个实例
         */

        if (instance == null)
            instance = new SingletonObject2();
        return instance;
    }
}
