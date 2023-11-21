/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.go.gauss.builder;


import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class ObjectBuilder extends JsonBuilder {
    private final JSONObject jsonObject;

    ObjectBuilder() {
        this.jsonObject = new JSONObject();
    }

    public ObjectBuilder with(String key, String value) {
        jsonObject.put(key, value);
        return this;
    }

    public ObjectBuilder with(String key, Number value) {
        jsonObject.put(key, value);
        return this;
    }

    public ObjectBuilder with(String key, boolean value) {
        jsonObject.put(key, value);
        return this;
    }

    public ObjectBuilder with(String key, Character character) {
        jsonObject.put(key, character);
        return this;
    }

    public ObjectBuilder with(String key, List<Object> list) {
        jsonObject.put(key, list);
        return this;
    }

    @Override
    public String build() {
        return jsonObject.toString();
    }

    public JSONObject toJsonObject() {
        return jsonObject;
    }
}