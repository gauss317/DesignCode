package com.go.gauss.service.pipeline.handler;

import com.go.gauss.service.pipeline.InstanceBuildContext;
import com.go.gauss.pipeline.ContextHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理器 - 保存模型实例到相关DB表：
 */
@Component
public class ModelInstanceSaver implements ContextHandler<InstanceBuildContext> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean handle(InstanceBuildContext context) {
        logger.info("--保存模型实例到相关DB表--");

        // 假装保存模型实例

        return true;
    }
}
