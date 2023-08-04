/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2023-2023. All rights reserved.
 */

package com.go.gauss.config.loader;

import com.netflix.config.util.ConfigurationUtils;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

/**
 * Properties文件加载器。支持加载{@code .properties}配置文件。
 *
 * @since 2023-01-18
 */
@Slf4j
public class PropertiesLoader implements ConfigurationLoader {
    @Override
    public boolean accept(Resource resource) {
        return Optional.ofNullable(resource)
            .map(Resource::getFilename)
            .map(name -> name.toLowerCase(Locale.ROOT))
            .map(name -> name.endsWith(".properties"))
            .orElse(false);
    }

    @Override
    public Map<String, Object> load(Resource resource) {
        log.debug("Loading properties resource:[{}]", resource.getFilename());

        Map<String, Object> configMap = new HashMap<>();

        try (InputStream inputStream = resource.getInputStream()) {
            Properties props = ConfigurationUtils.loadPropertiesFromInputStream(inputStream);
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                configMap.put(key, value);
            }
        } catch (IOException e) {
            log.error("Fail to loading properties file[{}].", resource.getFilename(), e);
        }

        return configMap;
    }
}
