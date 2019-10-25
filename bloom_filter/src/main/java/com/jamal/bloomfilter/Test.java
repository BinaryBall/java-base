package com.jamal.bloomfilter;

import java.util.concurrent.TimeUnit;

/**
 * bloom_filter
 * 2019/10/21 10:56
 *
 * @author 曾小辉
 **/
public class Test {
//
//    private long count = 0;
//
//    private void add10K() {
//        int idx = 0;
//        while (idx++ < 100000) {
//            count += 1;
//        }
//    }
//
//    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0;i<30;i++) {
//            Test test = new Test();
//            Thread th1 = new Thread(() -> {
//                test.add10K();
//            });
//            Thread th2 = new Thread(() -> {
//                test.add10K();
//            }); // 启动两个线程 th1.start(); th2.start();
//            th1.start();
//            th2.start();
//            th1.join();
//            th2.join();
//            System.out.println(test.count);
//        }
//    }
public static void main(String[] args) throws InterruptedException{
    Thread thread1 = new Thread(){
        @Override
        public void run(){
            System.out.println("start");
            boolean flag = true;
            while(flag){
                ;
            }
            System.out.print("end");
        }
    };
    thread1.setName("thread1");
    thread1.start();
    //当前线程休眠1秒
    TimeUnit.SECONDS.sleep(1);
    //关闭线程thread1
//    thread1.stop();
    thread1.interrupt();
    //输出线程thread1的状态
    String s = thread1.getState() + "1------" + thread1.isInterrupted();
    System.out.println(s);
    //当前线程休眠一秒
    TimeUnit.SECONDS.sleep(1);
    System.out.println(thread1.getState()+"2------"+thread1.isInterrupted());
}
}

