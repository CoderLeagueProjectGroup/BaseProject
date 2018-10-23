package com.coderleague.config.cache;

import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 标记了缓存注解的方法类信息
 * 用于主动刷新缓存时调用原始方法加载数据
 * Created by DELL on 2018/10/23.
 */
@Getter
public class CacheInvocation {

    private Object key;
    private final Object targetBean;
    private final Method targetMethod;
    private Object[] arguments;

    public CacheInvocation(Object key, Object targetBean, Method targetMethod, Object[] arguments) {
        this.key = key;
        this.targetBean = targetBean;
        this.targetMethod = targetMethod;
        if (arguments != null && arguments.length > 0) {
            this.arguments = Arrays.copyOf(arguments, arguments.length);
        }
    }

}
