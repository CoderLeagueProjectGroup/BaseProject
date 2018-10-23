package com.coderleague.config.cache;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 缓存方法注册接口
 * Created by DELL on 2018/10/23.
 */
public interface InvocationRegistry {

    void registerInvocation(Object invokeBean, Method invokedMethod, Object[] invocationArguments, Set<String> cacheNames);
}
