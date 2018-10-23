package com.coderleague.config.cache;

import com.coderleague.common.util.ApplicationContextUtils;
import com.coderleague.common.util.ThreadTaskHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by DELL on 2018/10/23.
 */
@Slf4j
public class CustomizedRedisCache extends RedisCache{

    private RedisOperations redisOperations;

    private static final ReentrantLock REFRESH_CACHE_LOCK = new ReentrantLock();

    public CustomizedRedisCache(String name, RedisCacheWriter cacheWriter,
                                RedisCacheConfiguration cacheConfig,
                                RedisOperations redisOperations) {
        super(name, cacheWriter, cacheConfig);
        this.redisOperations = redisOperations;
    }

    private CacheSupport getCacheSupport(){
        return ApplicationContextUtils.getBean(CacheSupport.class);
    }

    @Override
    public ValueWrapper get(Object key) {
        ValueWrapper valueWrapper = super.get(key);
        if(null!=valueWrapper){
            CacheItemConfig cacheItemConfig=CacheContainer.getCacheItemConfigByCacheName(key.toString());
            Long preloadTimeSecond = cacheItemConfig.getPreloadTimeSecond();
            if(preloadTimeSecond!=null){
                String cacheKey = createCacheKey(key);
                Long ttl = redisOperations.getExpire(cacheKey);
                if(null!=ttl&&ttl<=preloadTimeSecond){
                    log.info("key:{} ttl:{} preloadSecondTime:{}",cacheKey,ttl,preloadTimeSecond);
                    if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
                        log.info("do not need to refresh");
                    }else{
                        ThreadTaskHelper.run(() -> {
                            if(ThreadTaskHelper.hasRunningRefreshCacheTask(cacheKey)){
                                log.info("do not need to refresh");
                            }else{
                                log.info("refresh key:{}",cacheKey);
                                CustomizedRedisCache.this.getCacheSupport().refreshCacheByKey(getName(),key.toString());
                                ThreadTaskHelper.removeRefreshCacheTask(cacheKey);
                            }
                        });
                    }
                }
            }

        }
        return valueWrapper;
    }
}
