package com.example.dfrolov.allureandroidjava8.allure_implementation.allure.aspects;



import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Allure;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.AllureLifecycle;
import com.example.dfrolov.allureandroidjava8.allure_implementation.allure.Attachment;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.AspectUtils.getParametersMap;
import static com.example.dfrolov.allureandroidjava8.allure_implementation.allure.util.NamingUtils.processNameTemplate;


/**
 *
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 24.10.13
 */
@Aspect
public class AttachmentsAspects {

    private static AllureLifecycle lifecycle;

    public static AllureLifecycle getLifecycle() {
        if (Objects.isNull(lifecycle)) {
            lifecycle = Allure.getLifecycle();
        }
        return lifecycle;
    }

    /**
     * Sets lifecycle for aspects. Usually used in tests.
     *
     * @param lifecycle allure lifecycle to set.
     */
    public static void setLifecycle(final AllureLifecycle lifecycle) {
        AttachmentsAspects.lifecycle = lifecycle;
    }

    /**
     */
    @Pointcut("@annotation(io.qameta.allure.Attachment)")
    public void withAttachmentAnnotation() {
        //pointcut body, should be empty
    }

    /**
     * Pointcut for any methods.
     */
    @Pointcut("execution(* *(..))")
    public void anyMethod() {
        //pointcut body, should be empty
    }

    /**
     * If returned data is not a byte array, then use toString() method, and get bytes from it.
     *
     * @param joinPoint the join point to process.
     * @param result    the returned value.
     */
    @AfterReturning(pointcut = "anyMethod() && withAttachmentAnnotation()", returning = "result")
    public void attachment(final JoinPoint joinPoint, final Object result) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Attachment attachment = methodSignature.getMethod()
                .getAnnotation(Attachment.class);
        final byte[] bytes = (result instanceof byte[]) ? (byte[]) result : Objects.toString(result)
                .getBytes(StandardCharsets.UTF_8);

        final String name = attachment.value().isEmpty()
                ? methodSignature.getName()
                : processNameTemplate(attachment.value(), getParametersMap(methodSignature, joinPoint.getArgs()));
        getLifecycle().addAttachment(name, attachment.type(), attachment.fileExtension(), bytes);
    }
}
