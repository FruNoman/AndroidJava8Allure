package com.example.dfrolov.allureandroidjava8;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class SomeAspect {




    public SomeAspect() {
    }


    @Around("execution(* com.example.dfrolov.allureandroidjava8..*.*(..))")
    public Object step(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        System.out.println(" BEFORE METHOD "+methodSignature.getDeclaringType().getSimpleName()+"   "+methodSignature.getMethod().getName());
        Object proceed = joinPoint.proceed();
        System.out.println(" AFTER METHOD "+methodSignature.getDeclaringType().getSimpleName()+"   "+methodSignature.getMethod().getName());
        return proceed;
    }
}
