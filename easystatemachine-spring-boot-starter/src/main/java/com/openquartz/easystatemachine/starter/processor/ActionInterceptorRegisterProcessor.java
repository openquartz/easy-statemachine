package com.openquartz.easystatemachine.starter.processor;

import com.openquartz.easystatemachine.ActionInterceptor;
import com.openquartz.easystatemachine.impl.ActionInterceptorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author svnee
 * @see com.openquartz.easystatemachine.ActionInterceptor register to factory
 */
public class ActionInterceptorRegisterProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ActionInterceptor) {
            ActionInterceptorFactory.register((ActionInterceptor) bean);
        }
        return bean;
    }
}
