

package com.go.gauss.config.loader;

import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * 配置加载器。
 * <p>
 * 实现类需要完成将{@link Resource}资源文件转换为{@code Map<String, Object>}的能力。
 * </p>
 *
 * @since 2023-01-18
 */
public interface ConfigurationLoader {

    /**
     * 判断是否能够处理该资源文件
     * 
     * @param resource 待处理的资源文件
     * @return 判断结果
     */
    boolean accept(Resource resource);

    /**
     * 加载{@link Resource}资源文件，转换为{@code Map<String, Object>}格式的对象
     * 
     * @param resource 待加载的资源文件
     * @return Map格式的加载结果
     */
    Map<String, Object> load(Resource resource);
}
