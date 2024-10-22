package com.anle.identity.service.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String POINTCUT = "execution(* com.anle.identity.service.service.*.*(..))";

    @Pointcut(POINTCUT)
    public void servicesLogging() {
    }

    @Around("servicesLogging()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        try {
            logger.info("Executing {}.{}() with arguments: {}",
                    className, methodName, Arrays.stream(joinPoint.getArgs()));

            Object result = joinPoint.proceed();

            logger.info("{}.{}() executed successfully, returned: {}",
                    className, methodName, result);
            return result;

        } catch (Exception e) {
            logger.error("Exception in {}.{}() with message: {}",
                    className, methodName, e.getMessage(), e);
            throw e;  // Re-throw the exception to avoid changing behavior
        }
    }
}
