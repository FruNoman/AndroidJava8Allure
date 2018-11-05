package com.example.dfrolov.allureandroidjava8;

import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.AllureLifecycle;
import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.Status;
import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.StepResult;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Optional;
import java.util.UUID;

import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.AspectUtils.getParameters;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.AspectUtils.getParametersMap;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.NamingUtils.processNameTemplate;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.ResultsUtils.getStatusDetails;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.ResultsUtils.getStatusNot;

@Aspect
public class SomeAspect {

    private static AllureLifecycle lifecycle;

    @Around("@annotation(org.junit.Before)")
    public Object beforeTest(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = "Before test";

        lifecycle = Allure.getLifecycle();

        final StepResult result = new StepResult()
                .withName(name)
                .withStatus(Status.PASSED)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        lifecycle.startStep(uuid, result);
        final Object proceed = joinPoint.proceed();
        lifecycle.stopStep(uuid);

        return proceed;
    }

    @Around("@annotation(org.junit.After)")
    public Object afterTest(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = "After test";

        lifecycle = Allure.getLifecycle();

        final StepResult result = new StepResult()
                .withName(name)
                .withStatus(Status.PASSED)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        lifecycle.startStep(uuid, result);
        final Object proceed = joinPoint.proceed();
        lifecycle.stopStep(uuid);

        return proceed;
    }

    @Around("@annotation(org.junit.Test)")
    public Object test(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = "Test";

        lifecycle = Allure.getLifecycle();

        final StepResult result = new StepResult()
                .withName(name)
                .withStatus(Status.PASSED)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        lifecycle.startStep(uuid, result);
        final Object proceed = joinPoint.proceed();
        lifecycle.stopStep(uuid);

        return proceed;
    }


    @Pointcut("execution(* com.example.dfrolov.allureandroidjava8.allure_implementation..*.*(..))")
    public void removeAllure() {}

    @Around("execution(* com.example.dfrolov.allureandroidjava8..*.*(..)) && !removeAllure()")
    public Object anotherStep(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println(" BEFORE METHOD "+methodSignature.getDeclaringType().getSimpleName()+" "+methodSignature.getMethod().getName());
        final Object proceed = joinPoint.proceed();
        System.out.println(" AFTER METHOD "+methodSignature.getDeclaringType().getSimpleName()+" "+methodSignature.getMethod().getName());
        return proceed;
    }
}
