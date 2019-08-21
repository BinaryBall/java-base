package com.jamal;

public class App {
    public static void main(String[] args) {
//        LinearProbingHash hash = new LinearProbingHash(10);
//        Student student = new Student(1,"张三");
//        Student student1 = new Student(2,"王强");
//        Student student2 = new Student(5,"张伟");
//        Student student3 = new Student(11,"宝强");
//        hash.insert(student);
//        hash.insert(student1);
//        hash.insert(student2);
//        hash.insert(student3);
//        hash.disPlayTable();
//
//        Student find = hash.find(5);
//        System.out.println("查找结果，学号：　"+find.getNum()+"　，姓名：　"+find.getName());
//        hash.delete(11);
//        hash.disPlayTable();
//        HashDouble hash = new HashDouble(10);
//        Student student = new Student(1,"张三");
//        Student student1 = new Student(2,"王强");
//        Student student2 = new Student(5,"张伟");
//        Student student3 = new Student(11,"宝强");
//        hash.insert(student);
//        hash.insert(student1);
//        hash.insert(student2);
//        hash.insert(student3);
//        hash.disPlayTable();
//
//        Student find = hash.find(5);
//        System.out.println("查找结果，学号：　"+find.getNum()+"　，姓名：　"+find.getName());
//        hash.delete(11);
//        hash.disPlayTable();
        LinkHashTable linkHashTable = new LinkHashTable(10);
        linkHashTable.insert(1);
        linkHashTable.insert(2);
        linkHashTable.insert(6);
        linkHashTable.insert(8);
        linkHashTable.insert(16);
        linkHashTable.insert(31);
        linkHashTable.insert(54);
        linkHashTable.insert(81);
        linkHashTable.insert(88);
        linkHashTable.insert(90);
        linkHashTable.displayTable();
        linkHashTable.delete(6);
        linkHashTable.displayTable();
    }


}
