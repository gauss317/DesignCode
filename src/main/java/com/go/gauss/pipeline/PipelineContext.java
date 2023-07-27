package com.go.gauss.pipeline;

/**
 * 传递到管道的上下文
 * 后定义的context上下文需要继承此父类
 */
public class PipelineContext {
    /**
     * 获取数据名称
     */
    public String getName() {
        return this.getClass().getSimpleName();
    }
}