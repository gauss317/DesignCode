/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.go.gauss.builder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ArrayBuilder extends JsonBuilder {
    private final JSONArray array;

    ArrayBuilder() {
        this.array = new JSONArray();
    }

    public ArrayBuilder add(boolean value) {
        array.add(value);
        return this;
    }

    public ArrayBuilder add(String value) {
        array.add(value);
        return this;
    }

    public ArrayBuilder add(Character character) {
        array.add(character);
        return this;
    }

    public ArrayBuilder add(Number number) {
        array.add(number);
        return this;
    }

    public ArrayBuilder add(JSONObject jsonObject) {
        array.add(jsonObject);
        return this;
    }

    @Override
    public String build() {
        return array.toString();
    }

    public JSONArray toJsonArray() {
        return array;
    }
}