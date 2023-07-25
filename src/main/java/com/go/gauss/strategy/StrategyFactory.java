package com.go.gauss.strategy;

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Collection;
import java.util.Map;

/**
 * Spring 容器在启动的时候，会去扫描工厂指定的类型（Class<S>）的 Bean，并将其注册到工厂中（加入到 strategyMap）
 * @param <T>
 * @param <S>
 */
public abstract class StrategyFactory<T, S extends Strategy<T>> implements InitializingBean, ApplicationContextAware {
    private Map<T, S> strategyMap;

    private ApplicationContext appContext;

    /**
     * 根据策略type获得对应的策略的 Bean
     *
     * @param type 策略 type
     * @return 策略的 Bean
     */
    public S getStrategy(T type) {
        return strategyMap.get(type);
    }

    /**
     * 获取策略的类型（交给子类去实现）
     *
     * @return 策略的类型
     */
    protected abstract Class<S> getStrategyType();

    @Override
    public void afterPropertiesSet() {
        // 获取 Spring 容器中，所有 S 类型的 Bean
        Collection<S> strategies = appContext.getBeansOfType(getStrategyType()).values();

        strategyMap = Maps.newHashMapWithExpectedSize(strategies.size());

        // 将所有 S 类型的 Bean 放入到 strategyMap 中
        for (final S strategy : strategies) {
            T id = strategy.getType();
            strategyMap.put(id, strategy);
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
