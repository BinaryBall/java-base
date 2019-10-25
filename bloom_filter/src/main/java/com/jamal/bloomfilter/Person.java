package com.jamal.bloomfilter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bloom_filter
 * 2019/10/21 10:55
 *
 * @author 曾小辉
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String name;

    private String age;
}
