package com.dekapx.apps.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class EventMonitoringAspect {
    @Around("@annotation(org.springframework.context.event.EventListener)")
    public Object monitor(ProceedingJoinPoint pjp) throws Throwable {
        String eventName = pjp.getArgs()[0].getClass().getSimpleName();
        try {
            Object result = pjp.proceed();
            log.info("EventMonitoringAspect - " + eventName + " processed successfully");
            return result;
        } catch (Exception e) {
            log.error("EventMonitoringAspect - " + eventName + " failed", e);
            throw e;
        }
    }
}
