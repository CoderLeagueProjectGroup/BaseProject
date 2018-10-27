package com.coderleague.config;

import com.coderleague.interceptor.UserLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by DELL on 2018/10/24.
 */
@Configuration
@Slf4j
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public HandlerInterceptor userLoginInterceptor(){
        return new UserLoginInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/user/login","/**/*.css","/error","/swagger-ui.html","/**/*.html","/**/*.js","/**/*.png","/**/*.jpg","/druid/**","/actuator/**","/swagger-resources/**");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    public ResourceBundleMessageSource getMessageSource() throws Exception {
        ResourceBundleMessageSource rbms = new ResourceBundleMessageSource();
        rbms.setDefaultEncoding("UTF-8");
        rbms.setBasenames("i18n/validation/ValidationMessage"); // 此为文件目录 ValidationMessages是文件名
        rbms.setCacheSeconds(10);
        return rbms;
    }

    @Bean
    @Override
    protected Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        try {
            validator.setValidationMessageSource(getMessageSource());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return validator;
    }
}
