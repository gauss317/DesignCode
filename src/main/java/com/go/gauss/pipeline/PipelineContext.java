package com.go.gauss.pipeline;

import lombok.Getter;
import lombok.Setter;

/**
 * 传递到管道的上下文
 */
@Getter
@Setter
public class PipelineContext {
    /**
     * 获取数据名称
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }
}