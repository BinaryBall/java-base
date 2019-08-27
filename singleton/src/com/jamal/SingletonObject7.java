package com.jamal;

/**
 * 枚举模式 实现单例
 */
public class SingletonObject7 {


    private SingletonObject7(){

    }

    /**
     * 枚举类型是线程安全的，并且只会装载一次
     */
    private enum Singleton{
        INSTANCE;

        private final SingletonObject7 instance;

        Singleton(){
            instance = new SingletonObject7();
        }

        private SingletonObject7 getInstance(){
            return instance;
        }
    }

    public static SingletonObject7 getInstance(){

        return Singleton.INSTANCE.getInstance();
    }
}
