package com.example.mdcutilsdemo.config;

import com.example.mdcutilsdemo.filter.TraceIdFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class WebConfiguration {
    @Bean
    @ConditionalOnMissingBean({TraceIdFilter.class})
    public FilterRegistrationBean<TraceIdFilter> traceIdFilter() {
        FilterRegistrationBean<TraceIdFilter> registrationBean = new FilterRegistrationBean<>();
        try {
            // 初始化过滤器
            TraceIdFilter traceIdFilter = new TraceIdFilter();
            registrationBean.setFilter(traceIdFilter);

            log.info("TraceIdFilter has been successfully initialized.");
        } catch (IllegalArgumentException e) {
            // 处理非法参数异常
            log.error("Failed to initialize TraceIdFilter due to invalid configuration: {}", e.getMessage());
            throw new IllegalArgumentException("Invalid configuration for TraceIdFilter", e);
        } catch (Exception e) {
            // 处理其他异常
            log.error("An unexpected error occurred while initializing TraceIdFilter: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize TraceIdFilter", e);
        }

        // 动态设置 URL 模式
        registrationBean.addUrlPatterns("/*");
        // 设置过滤器顺序
        registrationBean.setOrder(1);

        // 添加日志记录
        log.info("TraceIdFilter has been registered with URL patterns:{},and order:{}"
                , String.join(", ", "/*")
                , 1);

        return registrationBean;
    }
}
