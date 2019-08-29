package com.jamal;

/**
 * @author xiaoxiang
 * @title: DoubleEndsQueue
 * @projectName queue
 * @description: TODO
 * @date 2019/8/2914:27
 */
public class DoubleEndsQueue {

    private static class Node {
        String item;
        Node next;
        Node prev;

        Node(Node prev, String element, Node next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
    transient Node first;

    /**
     * Pointer to last node.
     * Invariant: (first == null && last == null) ||
     *            (last.next == null && last.item != null)
     */
    transient Node last;


    public void enqueueFirst(String e) {
        final Node f = first;
        final Node newNode = new Node(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
    }

    public void enqueueLast(String e) {
        final Node l = last;
        final Node newNode = new Node(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
    }

    public String dequeueFirst() {
        if (first == null) return null;
        final Node f = first;
        String element = f.item;
        Node next = f.next;
        f.item = null;
        f.next = null; // help GC
        first = next;
        if (next == null)
            last = null;
        else
            next.prev = null;
        return element;
    }

    public String dequeueLast() {
        final Node l = last;
        if (last == null) return null;
        String element = l.item;
        Node prev = l.prev;
        l.item = null;
        l.prev = null;
        last = prev;
        if (prev == null)
            first = null;
        else
            prev.next = null;
        return element;
    }
    // 输出队列全部内容
    public void displayAll() {
        while (first !=null){
            System.out.print(first.item+" ");
            first = first.next;
        }
        System.out.println("===============");
    }
}
