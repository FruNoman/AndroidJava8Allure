package com.example.dfrolov.allureandroidjava8.allure_implementation.allure.aspects;



import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.AllureLifecycle;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Step;

import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.Status;
import com.example.dfrolov.allureandroidjava8.allure_implementation.model_pojo.StepResult;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

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
