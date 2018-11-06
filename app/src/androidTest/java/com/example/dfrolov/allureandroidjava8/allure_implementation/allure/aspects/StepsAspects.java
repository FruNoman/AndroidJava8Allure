package com.example.dfrolov.allureandroidjava8.allure_implementation.allure.aspects;



import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.AllureLifecycle;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;

import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.Status;
import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.StepResult;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.AspectUtils.getParameters;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.AspectUtils.getParametersMap;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.NamingUtils.processNameTemplate;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.ResultsUtils.getStatus;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.ResultsUtils.getStatusDetails;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.ResultsUtils.getStatusNot;


/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 24.10.13
 * @author sskorol (Sergey Korol)
 */
@Aspect
public class StepsAspects {

    private static AllureLifecycle lifecycle;

    public String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }


    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    @Around("@annotation(com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step) && execution(* *(..))")
    public Object step(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Step step = methodSignature.getMethod().getAnnotation(Step.class);

        final String uuid = UUID.randomUUID().toString();
        final String name = Optional.of(step.value())
                .filter(StringUtils::isNoneEmpty)
                .map(value -> processNameTemplate(value, getParametersMap(methodSignature, joinPoint.getArgs())))
                .orElse(methodSignature.getName());

        final StepResult result = new StepResult()
                .withName(name)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        getLifecycle().startStep(uuid, result);
        try {
            final Object proceed = joinPoint.proceed();
            getLifecycle().updateStep(uuid, s -> s.withStatus(Status.PASSED));
            return proceed;
        } catch (Throwable e) {
            getLifecycle().updateStep(uuid, s -> s
                    .withStatus(getStatusNot(e))
                    .withStatusDetails(getStatusDetails(e).orElse(null)));
            throw e;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }


    @Pointcut("call(* android.graphics..*.*(..))")
    public void androidGraphics() {
    }

    @Pointcut("call(* android.app..*.*(..))")
    public void androidApp() {
    }

    @Pointcut("call(* android.support..*.*(..))")
    public void androidSupport() {
    }

    @Pointcut("call(* org.junit.Assert.*(..))")
    public void assertSupport() {
    }

    @Pointcut("execution(* com.example.dfrolov.allureandroidjava8.allure_implementation..*.*(..))")
    public void allureSupport() {
    }

    @Pointcut("execution(* com.example.dfrolov.allureandroidjava8..*.*(..))")
    public void testsSupport() {
    }

    @Pointcut("@annotation(org.junit.Before)")
    public void beforeAnnotation() {
    }

    @Pointcut("@annotation(org.junit.After)")
    public void afterAnnotation() {
    }
    @Pointcut("@annotation(org.junit.Test)")
    public void testAnnotation() {
    }




    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    @Around("call(* android..*.*(..)) && !androidGraphics() && !androidSupport() && !androidApp()")
    public Object everyStepAndroid(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = methodSignature.getDeclaringType().getSimpleName()+" "+methodSignature.getMethod().getName();

        final StepResult result = new StepResult()
                .withName(name)
                .withStatus(Status.PASSED)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        getLifecycle().startStep(uuid, result);
        try {
            final Object proceed = joinPoint.proceed();
            return proceed;
        } catch (Throwable e) {
            throw e;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }

    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    @Around("assertSupport()")
    public Object everyStep(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = methodSignature.getDeclaringType().getSimpleName()+" "+methodSignature.getMethod().getName();

        final StepResult result = new StepResult()
                .withName(name)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        final Object proceed;
        try {
            proceed = joinPoint.proceed();
            getLifecycle().startStep(uuid, result.withStatus(Status.PASSED));
        } catch (Throwable e) {
            getLifecycle().startStep(uuid, result.withStatus(Status.FAILED));
            throw e;
        }

        finally {
            getLifecycle().stopStep(uuid);
        }
        return proceed;
    }

    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    @Around("!allureSupport() && testsSupport() && !beforeAnnotation() && !afterAnnotation()")
    public Object everyStepTest(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = methodSignature.getDeclaringType().getSimpleName()+" "+methodSignature.getMethod().getName();

        final StepResult result = new StepResult()
                .withName(name)
                .withStatus(Status.PASSED)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        getLifecycle().startStep(uuid, result);
        try {
            final Object proceed = joinPoint.proceed();
            return proceed;
        } catch (Throwable e) {
            throw e;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }

    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    @Around("beforeAnnotation()")
    public Object beforeAnnotation(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = "Before test method "+methodSignature.getMethod().getName();

        final StepResult result = new StepResult()
                .withName(name)
                .withStatus(Status.PASSED)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        getLifecycle().startStep(uuid, result);
        try {
            final Object proceed = joinPoint.proceed();
            return proceed;
        } catch (Throwable e) {
            throw e;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }

    @SuppressWarnings("PMD.UnnecessaryLocalBeforeReturn")
    @Around("afterAnnotation()")
    public Object afterAnnotation(final ProceedingJoinPoint joinPoint) throws Throwable {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final String uuid = UUID.randomUUID().toString();
        final String name = "After test method "+methodSignature.getMethod().getName();

        final StepResult result = new StepResult()
                .withName(name)
                .withStatus(Status.PASSED)
                .withParameters(getParameters(methodSignature, joinPoint.getArgs()));
        getLifecycle().startStep(uuid, result);
        try {
            final Object proceed = joinPoint.proceed();
            return proceed;
        } catch (Throwable e) {
            throw e;
        } finally {
            getLifecycle().stopStep(uuid);
        }
    }



    /**
     * For tests only.
     *
     * @param allure allure lifecycle to set.
     */
    public static void setLifecycle(final AllureLifecycle allure) {
        lifecycle = allure;
    }

    public static AllureLifecycle getLifecycle() {
        if (Objects.isNull(lifecycle)) {
            lifecycle = Allure.getLifecycle();
        }
        return lifecycle;
    }
}
