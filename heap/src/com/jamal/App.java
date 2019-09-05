package com.jamal;

/**
 * @author xiaoxiang
 * @title: App
 * @projectName heap
 * @description: TODO
 * @date 2019/9/514:55
 */
public class App {
    public static void main(String[] args) {
//        heap();
        sort();
    }

    public static void heap(){
        Heap heap = new Heap(10);
        for (int i=1;i<11;i++){
            heap.insert(i);
        }
        for (int i=1;i<11;i++){
            System.out.println("   ");
            heap.removeMax();
            heap.displayer();
        }

    }

    public static void sort(){
        int[] a = {2,4,6,8,5,1,9,13,56,34,67,23};
        HeadSort sort = new HeadSort();
        sort.sort(a,11);
        for (int i=0;i<a.length;i++){
            System.out.print(a[i]+" ");
        }
    }
}
