package com.go.gauss.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 功能描述
 *
 * @since 2023-08-02
 */
@Component
public class StringLifecycle implements InitializingBean, ApplicationContextAware {
    private final String code = "code";

    /**
     * constructor has runned:null
     * setApplicationContext has runned:false
     * postConstruct has runned:false
     * afterPropertiesSet has runned:false
     */
    public StringLifecycle() {
        System.out.println("constructor has runned:" + code);
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct has runned:" + code);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet has runned:" + code);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext has runned:" + code);
    }
}
