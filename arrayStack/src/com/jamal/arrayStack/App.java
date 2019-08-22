package com.jamal.arrayStack;

/**
 * @author xiaoxiang
 * @title: App
 * @projectName arrayStack
 * @description: TODO
 * @date 2019/8/2215:11
 */
public class App {

    public static void main(String[] args) {
//        arrayStackTest();
        linkStackTest();
    }

    public static void arrayStackTest(){
        ArrayStack arrayStack = new ArrayStack(10);
        for (int i = 0;i<20;i++) {
            System.out.println(arrayStack.push("张三"+i));
        }
        for (int i = 0;i<20;i++) {
            System.out.println(arrayStack.pop());
        }
    }

    public static void linkStackTest(){
        LinkStack linkStack = new LinkStack();
        for (int i = 0;i<20;i++) {
            System.out.println(linkStack.push("张三"+i));
        }
        for (int i = 0;i<20;i++) {
            System.out.println(linkStack.pop());
        }
    }

}
