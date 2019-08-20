package com.jamal;

import java.util.Random;

/**
 * 双向链表实现的跳表
 */
public class DoubleLinkSkipList {

    // 头节点
    private final static byte HEAD_NODE = (byte) -1;
    // 数据节点
    private final static byte DATA_NODE = (byte) 0;
    // 尾节点
    private final static byte TAIL_NODE = (byte) -1;


    private static class Node{
        private Integer value;
        private Node up,down,left,right;
        private byte bit;

        public Node(Integer value,byte bit){
            this.value = value;
            this.bit = bit;

        }
        public Node(Integer value){
            this(value,DATA_NODE);
        }

    }

    // 始终保持最高层的head
    private Node head;
    // 始终保持最高层的tail
    private Node tail;
    // 元素的个数
    private int size;
    //　层高
    private int level = 1;
    // 随机函数
    private Random random;
    // 最高层高
    private static final int MAX_LEVEL = 16;

    public DoubleLinkSkipList(){
        head = new Node(null,HEAD_NODE);
        tail = new Node(null,TAIL_NODE);
        head.right = tail;
        tail.left = head;
        this.random = new Random(System.currentTimeMillis());
    }


    public boolean isEmpty(){
        return (size() == 0);
    }

    public int size(){
        return size;
    }

    /**
     * 查找出最后一个小于该元素或者等于元素的节点
     * @param element　待查找的数
     * @return
     */
    private Node find(Integer element){
        Node current = head;
        for (;;){
            while (current.right.bit !=TAIL_NODE && current.right.value <= element){
                current = current.right;
            }

            if (current.down !=null){// 找到最底层节点
                current = current.down;
            }else{
                break;
            }
        }
        return current;// current.value <= element <current.right.value
    }

    /**
     * 判断是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(Integer element){
        Node node = this.find(element);
        return (node.value==element);
    }

    /**
     * 获取某个元素
     * @param element　
     * @return
     */
    public Integer get(Integer element){
        Node node = this.find(element);
        return (node.value==element)?node.value:null;
    }

    /**
     * 添加元素
     * @param element
     */
    public void add(Integer element){
        //查找出前一个节点
        Node node = this.find(element);
        // 新建节点
        Node newNode = new Node(element);
        // 新节点与前后两节点建立关系
        newNode.left = node;
        newNode.right = node.right;
        node.right.left = newNode;
        node.right = newNode;

        int currentLevel = 1;
        // 建立索引层，随机建立层高
        while (random.nextDouble() < 0.5d){

            // 索引大于当前索引层
            if (currentLevel >= level){
                level++;
                // 最顶层索引的头指针
                Node topHead = new Node(null,HEAD_NODE);
                // 最顶层索引的尾指针
                Node topTail = new Node(null,TAIL_NODE);

                topHead.right = topTail;
                topHead.down = head;
                head.up = topHead;
                topTail.left = topHead;
                topTail.down = tail;
                tail.up = topTail;

                head = topHead;
                tail = topTail;


            }
            // 一直往左边找到索引层
            while (node !=null && node.up == null){
                node = node.left;
            }
            node = node.up;
            // 新建索引节点，与当前左边节点建立关系
            Node indexNode = new Node(element);
            indexNode.left = node;
            indexNode.right = node.right;
            indexNode.down = newNode;

            node.right.left = indexNode;
            node.right = indexNode;

            newNode.up = indexNode;

            // 将索引节点作为新的节点，继续往上建立索引（如果有索引的话）
            newNode = indexNode;

            currentLevel++;
            // 当前层高大于最高层高时，跳出循环
            if (currentLevel > MAX_LEVEL) break;
        }
        size ++;
    }

    /**
     * 删除跳表
     * @param element　待删除的元素
     */
    public void delete(Integer element){
        Node node = this.find(element);
        // 没有找到该元素,直接返回
        if (node.value != element) return;
        // 删除元素，将元素的左右链表建立关系
        node.left.right = node.right;
        node.right.left = node.left;
        // 判断是否有索引层，
        while (node.up !=null){
            node.up.left.right = node.up.right;
            node.up.right.left = node.up.left;
            node = node.up;
        }

    }
    /**
     * 打印跳表
     */
    public void dumpSkipList(){
        Node temp = head;
        int i = level;
        while (temp !=null){
            System.out.printf("Total {%d};level {%d}", level ,i--);
            Node node = temp.right;
            while (node.bit==DATA_NODE){
                System.out.printf("--->%d",node.value);
                node = node.right;
            }
            System.out.printf("\n");
            temp = temp.down;
        }
    }

    public static void main(String[] args) {
        DoubleLinkSkipList skipList = new DoubleLinkSkipList();
        for (int i = 1; i < 10; i++) {
            skipList.add(i);
        }
        System.out.println(skipList.size());
        System.out.println(skipList.contains(3));
        System.out.println(skipList.contains(15));
        System.out.println(skipList.get(5));
        System.out.println(skipList.get(15));
        System.out.println("删除前");
        skipList.dumpSkipList();

        skipList.delete(3);
        System.out.println("删除后");
        skipList.dumpSkipList();

    }
}
