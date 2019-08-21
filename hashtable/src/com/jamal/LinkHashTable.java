package com.jamal;

/**
 * @author xiaoxiang
 * @title: LinkHashTable
 * @projectName hashtable
 * @description: TODO
 * @date 2019/8/2115:16
 */
public class LinkHashTable {

    private SortedLinkList[] array;

    private int arraySize;

    public LinkHashTable(int size) {
        this.arraySize = size;
        this.array = new SortedLinkList[arraySize];
        for (int i = 0; i < arraySize; i++) {
            array[i] = new SortedLinkList();
        }
    }

    public void displayTable() {
        for (int i = 0; i < arraySize; i++) {
            System.out.print("数组下标：" + i + " . ");
            array[i].displayList();
        }
    }

    public int hash(int key) {
        return key % arraySize;
    }

    /**
     * 链表法插入
     *
     * @param data
     */
    public void insert(int data) {
        Link link = new Link(data);
        int key = link.getKey();
        int hashVal = hash(key);
        array[hashVal].insert(link);
    }

    /**
     * 链表法-删除
     *
     * @param key
     */
    public void delete(int key) {
        int hashVal = hash(key);
        array[hashVal].delete(key);
    }

    /**
     * 链表法-查找
     *
     * @param key
     * @return
     */
    public Link find(int key) {
        int hashVal = hash(key);
        return array[hashVal].find(key);
    }
}
