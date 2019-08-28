package com.jamal;

/**
 * 基于链表的队列
 */
public class LinkQueue {

    // 指向队首
    private Node head;
    // 指向队尾
    private Node tail;

    /**
     * 入队操作
     * @param data
     * @return
     */
    public int enqueue(String data){
        Node node = new Node(data,null);
        // 判断队列中是否有元素
        if (tail == null) {
            tail = node;
            head = node;
        }else {
            tail.next = node;
            tail = node;
        }
        return 1;
    }

    /**
     * 出队操作
     * @return
     */
    public String dequeue(){
        if (head==null) return null;
        String data = head.data;
        head = head.next;
        // 取出元素后，头指针为空，说明队列中没有元素，tail也需要制为空
        if (head == null){
            tail = null;
        }
        return data;
    }

    class Node{
        private String data;
        private Node next;

        public Node(String data,Node node){
            this.data = data;
            next = node;
        }
    }


}
