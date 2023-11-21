/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.go.gauss.builder;

public abstract class JsonBuilder {
    public abstract String build();

    public static ObjectBuilder forObject() {
        return new ObjectBuilder();
    }

    public static ArrayBuilder forArray() {
        return new ArrayBuilder();
    }

    @Override
    public String toString() {
        return build();
    }
}
