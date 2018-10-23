package com.coderleague.config.cache;

/**
 * 主动刷新缓存接口
 * Created by DELL on 2018/10/23.
 */
public interface CacheSupport {

    /**
     * 刷新容器中所有的值
     * @param cacheName
     */
    void refreshCache(String cacheName);

    /**
     * 按容器以及指定键更新缓存
     * @param cacheName
     * @param cacheKey
     */
    void refreshCacheByKey(String cacheName,String cacheKey);
}
