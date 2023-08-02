package com.go.gauss.serialize;

import com.alibaba.fastjson.JSON;

/**
 * 功能描述
 *
 * @since 2023-08-01
 */
// 实现上面定义的序列化接口
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        // 获取上面的序列化标识
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
