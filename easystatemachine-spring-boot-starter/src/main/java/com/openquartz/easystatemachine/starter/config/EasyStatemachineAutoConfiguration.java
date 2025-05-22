package com.openquartz.easystatemachine.starter.config;

import com.openquartz.easystatemachine.starter.processor.ActionInterceptorRegisterProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

/**
 * @author svnee
 **/
public class EasyStatemachineAutoConfiguration {

    @Bean
    @Role(ROLE_INFRASTRUCTURE)
    public ActionInterceptorRegisterProcessor actionInterceptorRegisterProcessor() {
        return new ActionInterceptorRegisterProcessor();
    }

}
