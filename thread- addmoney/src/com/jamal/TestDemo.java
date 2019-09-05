package com.jamal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaoxiang
 * @title: BlockingQueueDemo
 * @projectName thread- addmoney
 * @description: TODO
 * @date 2019/9/416:07
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws Exception {
        List<Double> list = new ArrayList<>(10);
        int money = 0;
        for (int n=0;n<10;n++){
            BlockingQueue queue = new ArrayBlockingQueue(1);
            Account account = new Account();
            ExecutorService service = Executors.newFixedThreadPool(150);
            for(int i = 1; i <= 150; i++) {
                service.execute(new MyAccount(account));
//                service.execute(new BlockingQueueProducer(queue,i));
//                service.execute(new BlockingQueueConsumer(queue,account));
            }

            service.shutdown();

            while(!service.isTerminated()) {}
            list.add(account.getBalance());
//            System.out.println("账户余额: " + account.getBalance());
        }
        list.forEach((e)->{
            System.out.println(e);
        });

    }
}

class BlockingQueueProducer implements Runnable{

    protected BlockingQueue queue = null;

    protected int element;

    public BlockingQueueProducer(BlockingQueue queue,int element) {
        this.queue = queue;
        this.element = element;
    }

    public void run() {
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BlockingQueueConsumer implements Runnable{

    protected BlockingQueue queue = null;

    private volatile Account account;

    public BlockingQueueConsumer(BlockingQueue queue,Account account) {
        this.queue = queue;
        this.account = account;
    }

    public void run() {
        try {

            System.out.println("消费者："+queue.take()+"，存款");
            Thread.sleep(10);
            account.deposit(1.00);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 添加synchronized
class Account{
    private volatile double balance;

    /**
     * 存款
     * @param money 存入金额
     */
    public void deposit(double money) {

        balance = balance+ money;

    }

    /**
     * 获得账户余额
     */
    public double getBalance() {
        return balance;
    }
}

class MyAccount extends Thread{
    private Account account;
    public MyAccount(Account account){
        this.account = account;
    }
    public void run() {
        try {

            Thread.sleep(1000);
            account.deposit(1.00);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}