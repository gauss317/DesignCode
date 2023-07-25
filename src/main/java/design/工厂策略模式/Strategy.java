/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2022-2022. All rights reserved.
 */

package design.工厂策略模式;

/**
 * 策略
 *
 * @since 2023-06-27
 */
public interface Strategy<T> {
    /**
     * 获取策略的标识
     *
     * @return the type
     */
    T getType();
}
