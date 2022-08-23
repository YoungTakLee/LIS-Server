package com.green.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Aspect
public class RequestLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingAspect.class);
    //private final CmsLogDao mCmsLogDao;

   // RequestLoggingAspect(CmsLogDao cmsLogDao) {
    //    this.mCmsLogDao = cmsLogDao;
   // }

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)", entry.getKey(), entry.getValue()/*Joiner.on(",").join(entry.getValue())*/))
                .collect(Collectors.joining(", "));
    }

    @Pointcut("within(com.green.controller.*)")
    public void onRequest() {
    }

    @Around("com.green.config.RequestLoggingAspect.onRequest()")
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = // 5
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, String[]> paramMap = request.getParameterMap();
        String params = "";
        if (paramMap.isEmpty() == false) {
            params = " [" + paramMapToString(paramMap) + "]";
        }
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed(pjp.getArgs());
        } finally {
            long end = System.currentTimeMillis();
            if (!request.getRequestURI().contains("/equip") && !request.getRequestURI().contains("/power")
                    && !request.getRequestURI().contains("/files/dcs/stream/video/")) {
                logger.debug("Request: {} {}{} < {} ({}ms)", request.getMethod(), request.getRequestURI(), params,
                        request.getRemoteHost(), end - start);
            }
            if (!(request.getMethod().equalsIgnoreCase("GET"))) {
                Map<String, Object> cmsLogParamMap = new HashMap<>();
                cmsLogParamMap.put("user", request.getHeader("userInfo"));
                cmsLogParamMap.put("req_ip", request.getHeader(request.getRemoteAddr()));
                cmsLogParamMap.put("referer", request.getHeader("referer"));
                cmsLogParamMap.put("req_http_method", request.getMethod());
                cmsLogParamMap.put("req_uri", request.getRequestURI());
                cmsLogParamMap.put("params", params);
                //mCmsLogDao.insertCmsLog(cmsLogParamMap);
            }
        }
    }
}