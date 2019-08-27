package com.jamal;

/**
 * 在多线程的情况下是安全的，不能实现懒加载，static 修饰的是classLoad主动加载的，只要我们调用了SingletonObject1类，instance就会加载到内存中
 * 如何很长时间内不使用，则占用了内存
 */
public class SingletonObject1 {
    private static final SingletonObject1 instance = new SingletonObject1();

    private SingletonObject1(){

    }

    public static SingletonObject1 getInstance(){
        return instance;
    }
}
