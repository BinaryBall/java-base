package com.jamal.arrayStack;

/**
 * 基于数组的顺序栈
 */
public class ArrayStack {

    // 栈最大容量
    private int maxSzie;
    // 存放内容
    private String[] array;
    // 栈顶元素
    private int top;

    public ArrayStack(int size){
        this.maxSzie = size;
        this.array = new String[this.maxSzie];
        this.top = 0;
    }

    /**
     * 入栈操作
     *
     * @param data 数据
     * @return 0：入栈失败 1：入栈成功
     */
    public int push(String data) {
        if (top == maxSzie) return 0;
        array[top] = data;
        top++;
        return 1;
    }

    /**
     * 出栈操作
     *
     * @return
     */
    public String pop() {
        if (top == 0) return null;
        return array[--top];
    }

    /**
     * 获取栈顶元素
     *
     * @return
     */
    public String peek() {
        return array[top - 1];
    }
}
