package com.springboot.ecombackend1main.aspect;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class LogTimeAspect {

    @Around(value = "execution(* com.springboot.ecombackend1main.controller..*(..))")
    public Object logTime(final ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder kpi = new StringBuilder("KPI: ");
        kpi.append("[").append(joinPoint.getKind()).append("]\tfor:")
                .append(joinPoint.getSignature()).append("\twithArgs: (")
                        .append(StringUtils.join(joinPoint.getArgs(), ",")).append(")\ttook: ");
        Object result = joinPoint.proceed();
        log.info(kpi.append(System.currentTimeMillis() - startTime).append(" ms.").toString());
        return result;
    }
}
