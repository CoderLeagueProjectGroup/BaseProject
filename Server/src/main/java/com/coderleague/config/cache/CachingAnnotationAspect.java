package com.coderleague.config.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by DELL on 2018/10/23.
 */
@Aspect
@Component
public class CachingAnnotationAspect {

    @Autowired
    private InvocationRegistry invocationRegistry;

    private <T extends Annotation> List<T> getMethodAnnotations(AnnotatedElement ae,Class<T> annotaionType){
        List<T> anns=new ArrayList<T>(2);
        //look for raw annotation
        T ann=ae.getAnnotation(annotaionType);
        if(ann!=null){
            anns.add(ann);
        }
        //look for meta-annotations
        for(Annotation metaAnn:ae.getAnnotations()){
            ann=metaAnn.annotationType().getAnnotation(annotaionType);
            if(ann!=null){
                anns.add(ann);
            }
        }
        return anns.isEmpty()?null:anns;
    }

    private Method getSpecificMethod(ProceedingJoinPoint pjp){
        MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
        Method method=methodSignature.getMethod();
        // The method may be on an interface, but we need attributes from the
        // target class. If the target class is null, the method will be
        // unchanged.
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
        if (targetClass == null && pjp.getTarget() != null) {
            targetClass = pjp.getTarget().getClass();
        }
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the
        // original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }

    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void pointcut(){}

    @Around("pointcut()")
    public Object registerInvocation(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method=getSpecificMethod(joinPoint);
        List<Cacheable> annotations=getMethodAnnotations(method,Cacheable.class);

        Set<String> cacheSet = new HashSet<>();
        for(Cacheable cacheable:annotations){
            cacheSet.addAll(Arrays.asList(cacheable.value()));
        }
        invocationRegistry.registerInvocation(joinPoint.getTarget(),method,joinPoint.getArgs(),cacheSet);
        return joinPoint.proceed();
    }
}
