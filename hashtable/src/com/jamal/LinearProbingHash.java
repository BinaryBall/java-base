package com.jamal;

/**
 * 线性探测哈希表
 */
public class LinearProbingHash {

    private int size;

    private Student[] array;
    private Student noStudent;

    public LinearProbingHash(int size) {
        this.size = size;
        array = new Student[size];
        noStudent = new Student(-1,"");
    }

    /**
     * 哈希函数
     * @param key
     * @return
     */
    private int hash(int key) {
        return (key % size);
    }

    /**
     * 插入
     * @param student
     */
    public void insert(Student student){
        int key = student.getKey();
        int hashVal = hash(key);
        while (array[hashVal] !=null && array[hashVal].getKey() !=-1){
            ++hashVal;
            // 如果超过数组大小，则从第一个开始找
            hashVal %=size;
        }
        array[hashVal] = student;
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public Student delete(int key){
        int hashVal = hash(key);
        while (array[hashVal] !=null){
            if (array[hashVal].getKey() == key){
                Student temp = array[hashVal];
                array[hashVal]= noStudent;
                return temp;
            }
            ++hashVal;
            hashVal %=size;
        }
        return null;
    }

    /**
     * 查找
     * @param key
     * @return
     */
    public Student find(int key){
        int hashVal = hash(key);
        while (array[hashVal] !=null){
            if (array[hashVal].getKey() == key){
                return array[hashVal];
            }
            ++hashVal;
            hashVal %=size;
        }

        return null;
    }

    public void disPlayTable(){
        for (int i=0;i<array.length;i++){
            if (array[i] !=null){
                System.out.println("数组下标： "+i+" ，学号：　"+array[i].getNum()+"　，姓名：　"+array[i].getName());
            }else {
                System.out.println("数组下标： "+i+" ，该位置没有元素");
            }
        }
    }
}

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

