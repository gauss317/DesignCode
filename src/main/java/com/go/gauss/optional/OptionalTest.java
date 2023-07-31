package com.go.gauss.optional;

import com.huawei.agconnect.server.commons.rest.config.RestRequestConfig;

import java.util.Optional;

/**
 * 功能描述
 *
 * @since 2023-07-31
 */
public class OptionalTest {
    public static void main(String[] args) {
        String testStr = null;
        // testStr = "ss";
        // 使用Optional.ofNullable代替null的判断
        System.out.println(Optional.ofNullable(testStr).orElse("string"));
    }
}
