package com.coderleague.config.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by DELL on 2018/10/23.
 */
public class CustomizedRedisCacheManager extends RedisCacheManager {

    private RedisCacheWriter redisCacheWriter;
    private RedisCacheConfiguration defaultRedisCacheConfiguration;
    private RedisOperations redisOperations;

    private CustomizedRedisCacheManager(
            RedisCacheWriter redisCacheWriter
            ,RedisCacheConfiguration redisCacheConfiguration,
            Map<String, RedisCacheConfiguration> redisCacheConfigurationMap) {
        super(redisCacheWriter,redisCacheConfiguration,redisCacheConfigurationMap);
        this.redisCacheWriter=redisCacheWriter;
        this.defaultRedisCacheConfiguration=redisCacheConfiguration;
    }



    public CustomizedRedisCacheManager(
            RedisConnectionFactory connectionFactory,
            RedisOperations redisOperations,
            List<CacheItemConfig> cacheItemConfigList){
        this(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory),
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(30)),
                cacheItemConfigList.stream().collect(Collectors.toMap(CacheItemConfig::getName,cacheItemConfig -> {
                    return RedisCacheConfiguration.defaultCacheConfig()
                            .entryTtl(Duration.ofSeconds(cacheItemConfig.getExpireTimeSecond()))
                            .prefixKeysWith(cacheItemConfig.getName());
                })));
        this.redisOperations = redisOperations;
    }

    @Override
    public Cache getCache(String name) {
        Cache cache = super.getCache(name);
        if(null==cache){
            return cache;
        }
        return new CustomizedRedisCache(name,redisCacheWriter,
                defaultRedisCacheConfiguration,redisOperations);
    }
}
