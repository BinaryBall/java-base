package com.jamal;

/**
 * 双哈希
 */
public class HashDouble {

    private int size;

    private Student[] array;
    private Student noStudent;

    public HashDouble(int size) {
        this.size = size;
        array = new Student[size];
        noStudent = new Student(-1, "");
    }

    /**
     * 哈希函数
     *
     * @param key
     * @return
     */
    private int hash(int key) {
        return (key % size);
    }

    /**
     * 根据关键字生成探测步长
     * @param key
     * @return
     */
    private int stepHash(int key) {
        return 7 - (key % 7);
    }

    /**
     * 双哈希插入
     *
     * @param student
     */
    public void insert(Student student) {
        int key = student.getKey();
        int hashVal = hash(key);
        // 获取步长
        int stepSize = stepHash(key);
        while (array[hashVal] != null && array[hashVal].getKey() != -1) {
            hashVal +=stepSize;
            // 如果超过数组大小，则从第一个开始找
            hashVal %= size;
        }
        array[hashVal] = student;
    }

    /**
     * 双哈希删除
     *
     * @param key
     * @return
     */
    public Student delete(int key) {
        int hashVal = hash(key);
        int stepSize = stepHash(key);
        while (array[hashVal] != null) {
            if (array[hashVal].getKey() == key) {
                Student temp = array[hashVal];
                array[hashVal] = noStudent;
                return temp;
            }
            hashVal +=stepSize;
            hashVal %= size;
        }
        return null;
    }

    /**
     * 双哈希查找
     *
     * @param key
     * @return
     */
    public Student find(int key) {
        int hashVal = hash(key);
        int stepSize = stepHash(key);
        while (array[hashVal] != null) {
            if (array[hashVal].getKey() == key) {
                return array[hashVal];
            }
            hashVal +=stepSize;
            hashVal %= size;
        }

        return null;
    }

    public void disPlayTable() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                System.out.println("数组下标： " + i + " ，学号：　" + array[i].getNum() + "　，姓名：　" + array[i].getName());
            } else {
                System.out.println("数组下标： " + i + " ，该位置没有元素");
            }
        }
    }
}

