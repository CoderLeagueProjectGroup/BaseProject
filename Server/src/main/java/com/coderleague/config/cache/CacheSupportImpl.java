package com.coderleague.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.MethodInvoker;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by DELL on 2018/10/23.
 */
@Slf4j
@Component
public class CacheSupportImpl implements CacheSupport, InvocationRegistry {

    /**
     * 记录容器与所有执行方法的信息
     */
    private Map<String, Set<CacheInvocation>> cacheToInvocationsMap;

    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void initialize() {
        cacheToInvocationsMap = new ConcurrentHashMap<>(cacheManager.getCacheNames().size());
        for (String cacheName : cacheManager.getCacheNames()) {
            cacheToInvocationsMap.put(cacheName, new CopyOnWriteArraySet<>());
        }
    }

    private void refreshCache(CacheInvocation invocation, String cacheName) {
        boolean invocationSuccess;
        Object computed = null;
        try {
            computed = invoke(invocation);
            invocationSuccess = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            invocationSuccess = false;
        }
        if (invocationSuccess && cacheToInvocationsMap.get(cacheName) != null) {
            cacheManager.getCache(cacheName).put(invocation.getKey(), computed);
        }
    }

    private Object invoke(CacheInvocation invocation) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        MethodInvoker invoker = new MethodInvoker();
        invoker.setTargetObject(invocation.getTargetBean());
        invoker.setArguments(invocation.getArguments());
        invoker.setTargetMethod(invocation.getTargetMethod().getName());
        invoker.prepare();
        return invoker.invoke();
    }

    @Override
    public void refreshCache(String cacheName) {
        refreshCacheByKey(cacheName, null);
    }

    @Override
    public void refreshCacheByKey(String cacheName, String cacheKey) {
        if (cacheToInvocationsMap.get(cacheName) != null) {
            for (CacheInvocation invocation : cacheToInvocationsMap.get(cacheName)) {
                if (!StringUtils.isBlank(cacheKey)
                        && invocation.getKey().toString().equals(cacheKey)) {
                    refreshCache(invocation, cacheName);
                }
            }
        }
    }

    @Override
    public void registerInvocation(Object invokeBean, Method invokedMethod,
                                   Object[] invocationArguments, Set<String> cacheNames) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : invocationArguments) {
            sb.append(obj.toString());
        }
        Object key = sb.toString();

        CacheInvocation invocation = new CacheInvocation(key, invokeBean, invokedMethod, invocationArguments);
        for (String cacheName : cacheNames) {
            String[] cacheParams = cacheName.split("#");
            String realCacheName = cacheParams[0];
            if (!cacheToInvocationsMap.containsKey(realCacheName)) {
                initialize();
            }
            cacheToInvocationsMap.get(realCacheName).add(invocation);
        }
    }
}
