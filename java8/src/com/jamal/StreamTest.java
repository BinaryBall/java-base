package com.jamal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * java8
 * 2019/10/17 16:41
 *
 * @author 曾小辉
 **/
public class Stream {

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person(20, "张三"));
        personList.add(new Person(30, "张为为"));
        personList.add(new Person(25, "张无畏"));
        personList.add(new Person(29, "张斯"));
        personList.add(new Person(21, "张逼"));
        personList.add(new Person(20, "里斯"));
        personList.add(new Person(28, "科尔"));
        personList.add(new Person(25, "莫雷"));
        personList.add(new Person(40, "校花"));

//        personList.stream().forEach((person)-> System.out.println(person.getName()));
//        personList.stream().filter((person) -> {
//                        System.out.println("filter:"+person.getName());
//                        return person.getAge()>22;
//                    })
//                .map((person)->{
//                    System.out.println("map:"+person.getName());
//                    return person;
//                })
//                .sorted(Comparator.comparing(Person::getAge))
//                .forEach(person -> System.out.println("姓名："+person.getName()+" 年龄："+person.getAge()));
//        java.util.stream.Stream<Person> s = personList.stream();
//        s.forEach(System.out::println);
//        s.forEach(System.out::println);

//        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
//        numbers.stream()
//                .filter(i -> i%2==0)
//                .distinct()
//                .forEach(System.out::println);

        List<Integer> list =  personList.stream()
                .map(Person::getName)
                .map(String::length)
                .collect(toList());
        System.out.println(list);
    }
}
