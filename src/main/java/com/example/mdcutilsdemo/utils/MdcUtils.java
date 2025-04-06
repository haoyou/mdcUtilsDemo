package com.example.mdcutilsdemo.utils;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

public class MdcUtils {
    public static final String TRACE_ID_KEY = "traceId";
    /**
     * 生成traceId
     * */
    public static String generatorTraceId(){
        String traceId = IdUtil.fastSimpleUUID().toLowerCase();
        MDC.put(TRACE_ID_KEY, traceId);
        return traceId;
    }
    public static String generatorTraceId(String traceId){
        if (StringUtils.isBlank(traceId)){
            return generatorTraceId();
        }
        MDC.put(TRACE_ID_KEY, traceId);
        return traceId;
    }
    /**
     * 获取traceId
     * */
    public static String getTraceId(){
        return MDC.get(TRACE_ID_KEY);
    }
    /**
     * 移除traceId
     * */
    public static void removeTraceId(){
        MDC.remove(TRACE_ID_KEY);
    }
}
