package com.jamal.arrayStack;

/**
 * 基于链表的链式栈
 */
public class LinkStack {

    // 始终指向栈的第一个元素
    private Node top = null;


    /**
     * 压栈
     *
     * @param data
     * @return
     */
    public int push(String data) {
        Node node = new Node(data);
        if (top == null) {
            top = node;
        } else {
            node.next = top;
            top = node;
        }
        return 1;
    }


    /**
     * 出栈
     *
     * @return
     */
    public String pop() {
        if (top == null) return null;
        String data = top.getData();
        top = top.next;
        return data;
    }

    /**
     * 节点信息
     */
    private static class Node {
        private String data;
        private Node next;

        public Node(String data) {
            this.data = data;
            this.next = null;
        }

        public String getData() {
            return this.data;
        }
    }
}
