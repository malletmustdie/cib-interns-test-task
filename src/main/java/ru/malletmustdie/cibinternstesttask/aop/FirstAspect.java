package ru.malletmustdie.cibinternstesttask.aop;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class FirstAspect {

    @Before("execution(* ru.malletmustdie.cibinternstesttask.service.*.*(..))")
    public void beforeLogService(JoinPoint joinPoint) {
        beforeLog(joinPoint);
    }

    @AfterReturning(pointcut = "execution(* ru.malletmustdie.cibinternstesttask.service.*.*(..))",
                    returning = "returningObject")
    public void afterLogService(Object returningObject) {
        afterLog(returningObject);
    }

    private void beforeLog(JoinPoint joinPoint) {
        log.info("Выполнился метод:" + joinPoint.getTarget().getClass().getSimpleName() + " "
                         + joinPoint.getSignature().getName());
        String args = Arrays.stream(joinPoint.getArgs()).filter(Objects::nonNull)
                            .map(Object::toString)
                            .collect(Collectors.joining(","));
        log.info("Входящие параметры: " + joinPoint + ", args=[" + args + "]");
    }

    private void afterLog(Object returningObject) {
        log.trace("Возвращаемое значение: " + returningObject);
    }

}
