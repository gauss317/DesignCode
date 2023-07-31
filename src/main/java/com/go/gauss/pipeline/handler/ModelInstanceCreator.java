package com.go.gauss.pipeline.handler;

import com.go.gauss.service.pipeline.InstanceBuildContext;
import com.go.gauss.pipeline.ContextHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理器 - 根据输入创建模型实例：
 */
@Component
public class ModelInstanceCreator implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--根据输入数据创建模型实例--");

        // 假装创建模型实例

        return true;
    }
}
