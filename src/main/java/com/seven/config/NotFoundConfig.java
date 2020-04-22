package com.seven.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @Description: 404捕获异常必须配置下列属性 才生效
 * @Author chendongdong
 * @Date 2020/4/22 16:27
 * @Version V1.0
 **/
@Configuration
public class NotFoundConfig {
    @Bean
    @ConditionalOnMissingBean
    @Primary
    public WebMvcProperties mvcProperties(WebMvcProperties properties) {
        if (properties==null){
            properties=new WebMvcProperties();
        }
        properties.setThrowExceptionIfNoHandlerFound(true);
        return properties;
    }

    @Bean
    @ConditionalOnMissingBean
    @Primary
    public ResourceProperties resourceProperties(ResourceProperties properties){
        if (properties==null){
            properties=new ResourceProperties();
        }
        properties.setAddMappings(false);
        return properties;
    }
}
