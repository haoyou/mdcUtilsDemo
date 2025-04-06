package com.example.mdcutilsdemo.filter;

import com.example.mdcutilsdemo.utils.MdcUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Slf4j
public class TraceIdFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("TraceFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            // 确保类型安全
            if (!(servletRequest instanceof HttpServletRequest)) {
                log.warn("The provided ServletRequest is not an instance of HttpServletRequest.");
                return;
            }

            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

            // 获取并生成 Trace ID
            String gateWayTraceId = httpServletRequest.getHeader(MdcUtils.TRACE_ID_KEY);
            MdcUtils.generatorTraceId(gateWayTraceId);

            // 继续过滤链
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            // 记录完整异常信息
            log.error("TraceFilter doFilter error.", e);
        } finally {
            // 清除 Trace ID
            MdcUtils.removeTraceId();
        }

    }

    @Override
    public void destroy() {
        log.info("TraceFilter destroy");
    }
}
