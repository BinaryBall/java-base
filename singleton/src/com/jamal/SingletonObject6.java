package com.jamal;

/**
 * Holder模式，能在不加任何锁的情况下完美的解决单例问题
 */
public class SingletonObject6 {


    private SingletonObject6(){

    }

    /**
     * static能保证只被实例化一次，并且严格保证实例化顺序，static 是一个主动加载的，只有调用getInstance才会去调用
     */
    private static class InstanceHolder{
        private  final static SingletonObject6 instance = new SingletonObject6();

    }
    public static SingletonObject6 getInstance(){

        return InstanceHolder.instance;
    }
}
