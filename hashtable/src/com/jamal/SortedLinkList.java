package com.jamal;


public class SortedLinkList {
    private Link first;
    public SortedLinkList(){
        first = null;
    }
    /**
     *链表插入
     * @param link
     */
    public void insert(Link link){
        int key = link.getKey();
        Link previous = null;
        Link current = first;
        while (current!=null && key >current.getKey()){
            previous = current;
            current = current.next;
        }
        if (previous == null)
            first = link;
        else
            previous.next = link;
        link.next = current;
    }

    /**
     * 链表删除
     * @param key
     */
    public void delete(int key){
        Link previous = null;
        Link current = first;
        while (current !=null && key !=current.getKey()){
            previous = current;
            current = current.next;
        }
        if (previous == null)
            first = first.next;
        else
            previous.next = current.next;
    }

    /**
     * 链表查找
     * @param key
     * @return
     */
    public Link find(int key){
        Link current = first;
        while (current !=null && current.getKey() <=key){
            if (current.getKey() == key){
                return current;
            }
            current = current.next;
        }
        return null;
    }
    public void displayList(){
        System.out.print("List (first-->last): ");
        Link current = first;
        while (current !=null){
            current.displayLink();
            current = current.next;
        }
        System.out.println(" ");
    }
}
