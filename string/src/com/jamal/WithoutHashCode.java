package com.jamal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * string
 * 2019/9/24 14:49
 * hashcode 和equlas
 *
 * @author 曾小辉
 **/
public class WithoutHashCode {
    public static void main(String[] args) {
        key k1 = new key(1);
        key k2 = new key(1);
        Map<key,String> map = new HashMap<>();
        map.put(k1,"这是一个测试");
        System.out.println(map.get(k2));
    }
}

class key {
    private Integer id;

    private Integer getId() {
        return this.id;
    }

    public key(Integer id) {
        this.id = id;
    }

//    public boolean equals(Object o) {
//        if (o == null || !(o instanceof key)) return false;
//        return this.getId().equals(((key) o).getId());
//    }
//
//    public int hashCode() {
//        return id.hashCode();
//    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        key key = (key) o;
        return Objects.equals(id, key.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}