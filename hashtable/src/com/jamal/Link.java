package com.jamal;

/**
 * @author xiaoxiang
 * @title: Link
 * @projectName hashtable
 * @description: TODO
 * @date 2019/8/2114:16
 */
public class Link {
    private int iData;

    public Link next;

    public int getKey() {
        return iData;
    }

    public Link(int iData) {
        this.iData = iData;
    }

    public void displayLink(){
        System.out.print(iData+" ");
    }

}
