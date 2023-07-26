package com.go.gauss.pipeline.configuration;

import com.go.gauss.pipeline.handler.ContextHandler;
import com.go.gauss.pipeline.context.PipelineContext;
import com.go.gauss.pipeline.handler.InputDataPreChecker;
import com.go.gauss.pipeline.context.InstanceBuildContext;
import com.go.gauss.pipeline.handler.ModelInstanceCreator;
import com.go.gauss.pipeline.handler.ModelInstanceSaver;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管道路由的配置
 */
@Configuration
public class PipelineRouteConfig implements ApplicationContextAware {

    /**
     * 数据类型->管道中处理器类型列表 的路由
     */
    private static final Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>>
        PIPELINE_ROUTE_MAP = new HashMap<>(4);

    /*
     * 在这里配置各种上下文类型对应的处理管道：键为上下文类型，值为处理器类型的列表
     */
    static {
        PIPELINE_ROUTE_MAP.put(InstanceBuildContext.class,
            Arrays.asList(InputDataPreChecker.class, ModelInstanceCreator.class, ModelInstanceSaver.class));

        // 将来其他 Context 的管道配置
        // PIPELINE_ROUTE_MAP.put(xxxContext.class, Arrays.asList(xxx.class, xxx.class, xxx.class));
    }

    /**
     * 在 Spring 启动时，根据路由表生成对应的管道映射关系
     * 将map的class转化为对象的handle bean
     */
    @Bean("pipelineRouteMap")
    public Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> getHandlerPipelineMap() {
        return PIPELINE_ROUTE_MAP.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, this::toPipeline));
    }

    /**
     * 根据给定的管道中 ContextHandler 的类型的列表，构建管道
     */
    private List<? extends ContextHandler<? extends PipelineContext>> toPipeline(
        Map.Entry<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> entry) {
        return entry.getValue().stream().map(appContext::getBean).collect(Collectors.toList());
    }

    private ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
