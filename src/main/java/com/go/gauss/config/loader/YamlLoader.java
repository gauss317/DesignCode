

package com.go.gauss.config.loader;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Yaml配置文件加载器。
 * <p>
 * 能够将Yaml格式的配置文件转换为{@code Map<String, Object>}格式的对象。
 * Key为平铺后的格式。例如：
 * </p>
 * 
 * <pre class="code">
 *   environments:
 *     dev:
 *       url: https://dev.bar.com
 *       name: Developer Setup
 *     prod:
 *       url: https://foo.bar.com
 *       name: My Cool App
 * </pre>
 *
 * 会被转换为：
 *
 * <pre class="code">
 *   environments.dev.url=https://dev.bar.com
 *   environments.dev.name=Developer Setup
 *   environments.prod.url=https://foo.bar.com
 *   environments.prod.name=My Cool App
 * </pre>
 *
 * @implNote 基于Spring的YamlProcessor实现。
 * @since 2023-01-18
 */
@Slf4j
public final class YamlLoader extends YamlProcessor implements ConfigurationLoader {

    private Yaml yaml;

    public YamlLoader() {
        this.yaml = createYaml();
    }

    @Override
    protected Yaml createYaml() {
        if (Objects.isNull(this.yaml)) {
            this.yaml = super.createYaml();
        }
        return this.yaml;
    }

    @Override
    public boolean accept(Resource resource) {
        return Optional.ofNullable(resource)
            .map(Resource::getFilename)
            .map(name -> name.toLowerCase(Locale.ROOT))
            .map(name -> name.endsWith(".yaml") || name.endsWith(".yml"))
            .orElse(false);
    }

    @Override
    public Map<String, Object> load(Resource resource) {
        log.debug("Loading yaml resource:[{}]", resource.getFilename());
        setResources(resource);

        final Map<String, Object> result = new LinkedHashMap<>();
        process((properties, map) -> result.putAll(getFlattenedMap(map)));
        return result;
    }
}
