package org.linlinjava.litemall.admin.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

/**
 * @author jijiecong
 * @version 1.0
 * @date 2023/5/28 15:18
 * @description TODO
 */
public class SpringBeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        SpringBeanUtils.applicationContext = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 解析${}表达式
     * @param expression
     * @return
     */
    public static String resolvePlaceholders(String expression){
        Environment environment = getApplicationContext().getEnvironment();
        return environment.resolvePlaceholders(expression);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
