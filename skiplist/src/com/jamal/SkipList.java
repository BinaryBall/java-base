package com.jamal;

import java.util.Random;

/**
 * @author xiaoxiang
 * @title: SkipList
 * @projectName skiplist
 * @description: TODO
 * @date 2019/8/1510:27
 */
public class SkipList {

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        for (int i = 1; i < 10; i++) {
            skipList.insert(i);
        }
        skipList.dumpSkipList();

    }

    private static final int MAX_LEVEL = 16;

    private int levelCount = 1;

    private Node head = new Node();  // 带头链表

    private Random r = new Random();


    /**
     * 在跳表中查询
     *
     * @param value
     * @return
     */
    public Node find(int value) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            return p.forwards[0];
        } else {
            return null;
        }
    }

    /**
     * 插入数据
     *
     * @param value
     */
    public void insert(int value) {
        int level = randomLevel();
        Node newNode = new Node();
        newNode.data = value;
        newNode.maxLevel = level;
        Node update[] = new Node[level];
        for (int i = 0; i < level; ++i) {
            update[i] = head;
        }

        // record every level largest value which smaller than insert value in update[]
        Node p = head;
        for (int i = level - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;// use update save node in search path
        }

        // in search path node next node become new node forwords(next)
        for (int i = 0; i < level; ++i) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        // update node hight
        if (levelCount < level) levelCount = level;
    }

    /**
     * 删除数据
     *
     * @param value
     */
    public void delete(int value) {
        Node[] update = new Node[levelCount];
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.forwards[i] != null && p.forwards[i].data < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        if (p.forwards[0] != null && p.forwards[0].data == value) {
            for (int i = levelCount - 1; i >= 0; --i) {
                if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
                }
            }
        }
    }

    // 随机 level 次，如果是奇数层数 +1，防止伪随机
    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; ++i) {
            if (r.nextInt() % 2 == 1) {
                level++;
            }
        }

        return level;
    }

    public void dumpSkipList(){
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
//            while (p.forwards[i] != null ) {
//                System.out.print(p.forwards[i].data);
//            }
            System.out.print(p.forwards[i]);
            System.out.println("-------------------");
        }
    }
    public void printAll() {
        Node p = head;
        while (p.forwards[0] != null) {
            System.out.println(p.forwards[0] + " ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    public class Node {
        private int data = -1;
        private Node forwards[] = new Node[MAX_LEVEL];
        private int maxLevel = 0;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{ data: ");
            builder.append(data);
            builder.append("; levels: ");
            builder.append(maxLevel);
            builder.append("; forwards: ");
            builder.append(forwards);
            builder.append(" }");

            return builder.toString();
        }
    }

}
