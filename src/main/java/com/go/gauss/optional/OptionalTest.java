package com.go.gauss.optional;

import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * 功能描述
 *
 * @since 2023-07-31
 */
public class OptionalTest {
    public static void main(String[] args) {
        String testStr = null;
        // 使用Optional.ofNullable代替null的判断
        System.out.println(Optional.ofNullable(testStr).orElse("orElse"));

        Demo demo = new Demo();
        System.out.println(Optional.ofNullable(demo.getId()).orElse("demo"));

        demo.setId("id");
        if (demo == null || demo.getId() == null) {
            System.out.println("exception");
        }

        // 三目运算符可使用Optional判断
        String s = Optional.of(demo).map(Demo::getChildren).map(Children::getName).orElse("1");
        System.out.println(s);
    }
}

@Data
class Demo implements Serializable {
    private String id;

    private String ip;

    private int port;

    private Children children;
}

@Data
class Children {
    private String name;
}