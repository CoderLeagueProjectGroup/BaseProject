package com.coderleague.common.util;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by DELL on 2018/10/23.
 */
public class ThreadTaskHelper {

    private static ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()*2);

    private static final ConcurrentHashMap<String,String> RUNNING_REFRESH_CACHE =
            new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String,String> getRunningRefreshCache(){
        return RUNNING_REFRESH_CACHE;
    }

    public static void putRefreshCacheTask(String cacheKey){
        RUNNING_REFRESH_CACHE.putIfAbsent(cacheKey,cacheKey);
    }

    public static void removeRefreshCacheTask(String cacheKey){
        RUNNING_REFRESH_CACHE.remove(cacheKey);
    }

    public static boolean hasRunningRefreshCacheTask(String cacheKey){
        return RUNNING_REFRESH_CACHE.containsKey(cacheKey);
    }

    public static void run(Runnable runnable){
        executorService.execute(runnable);
    }
}
