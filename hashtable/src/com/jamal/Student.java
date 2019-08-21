package com.jamal;

/**
 * 学生类
 */
class Student {

    private int num;

    private String name;

    public Student(int num, String name) {
        this.name = name;
        this.num = num;
    }

    public Student() {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return this.num;
    }
}
