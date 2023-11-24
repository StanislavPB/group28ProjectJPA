package org.group28projectjpa.configuration;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggingConfiguration {
    @Pointcut("execution(public * org.group28projectjpa.controllers.*.*(..))")
    public void controllerLog() {
    }

    @Pointcut("execution(public * org.group28projectjpa.core.services.*.*(..))")
    public void serviceLog() {
    }

    @Before("controllerLog()")
    public void doBeforeControllerLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("NEW REQUEST: \n IP: {}\n URL: {}\n HTTP_METHOD; {}\n CONTROLLER_METHOD: {}.{}",
                request.getRemoteAddr(),
                request.getRequestURL().toString(),
                request.getMethod(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @Before("serviceLog()")
    public void doBeforeServiceLog(JoinPoint joinPoint) {
        log.info(" RUN SERVICE: /n SERVICE_METHOD: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }


}
