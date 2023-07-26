package com.go.gauss.pipeline;

import com.go.gauss.pipeline.context.PipelineContext;
import com.go.gauss.pipeline.handler.ContextHandler;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

import javax.annotation.Resource;

/**
 * 管道执行器
 */
@Component
public class InstancePipelineExecutor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 引用 PipelineRouteConfig 中的 pipelineRouteMap
     */
    @Resource
    private Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? super PipelineContext>>>
        pipelineRouteMap;

    /**
     * 管道线程池
     */
    @Resource
    private ThreadPoolTaskExecutor pipelineThreadPool;

    /**
     * 同步处理输入的上下文数据<br/>
     * 如果处理时上下文数据流通到最后一个处理器且最后一个处理器返回 true，则返回 true，否则返回 false
     *
     * @param context 输入的上下文数据
     * @return 处理过程中管道是否畅通，畅通返回 true，不畅通返回 false
     */
    public boolean acceptSync(PipelineContext context) {
        // 获取数据处理管道
        List<? extends ContextHandler<? super PipelineContext>> pipeline = pipelineRouteMap.get(context.getClass());

        // 没有异常的写法
        pipeline.forEach(contextHandler -> contextHandler.handle(context));

        if (CollectionUtils.isEmpty(pipeline)) {
            logger.error("管道为空");
            return false;
        }

        // 管道是否畅通
        boolean lastSuccess = false;

        for (ContextHandler<? super PipelineContext> handler : pipeline) {
            try {
                // 当前处理器处理数据，并返回是否继续向下处理
                lastSuccess = handler.handle(context);
            } catch (Throwable ex) {
                lastSuccess = false;
                logger.error("[{}] 处理异常，handler={}", context.getName(), handler.getClass().getSimpleName(), ex);
            }

            // 不再向下处理
            if (!lastSuccess) {
                break;
            }
        }

        return lastSuccess;
    }

    /**
     * 异步处理输入的上下文数据
     *
     * @param context 上下文数据
     * @param callback 处理完成的回调
     */
    public void acceptAsync(PipelineContext context, BiConsumer<PipelineContext, Boolean> callback) {
        pipelineThreadPool.execute(() -> {
            boolean success = acceptSync(context);

            if (callback != null) {
                callback.accept(context, success);
            }
        });
    }
}
