package com.coderleague.config;

import com.coderleague.common.entity.Constant;
import com.coderleague.config.cache.CacheItemConfig;
import com.coderleague.config.cache.CustomizedRedisCacheManager;
import com.google.common.collect.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by DELL on 2018/10/23.
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, RedisTemplate<Object, Object> redisTemplate) {
        CacheItemConfig productCacheItemConfig=new CacheItemConfig();
        productCacheItemConfig.setName(Constant.USER_CACHE_NAME);
        productCacheItemConfig.setExpireTimeSecond(60*30);
        productCacheItemConfig.setPreloadTimeSecond(60*5);
        List<CacheItemConfig> cacheItemConfigs= Lists.newArrayList(productCacheItemConfig);
        CustomizedRedisCacheManager cacheManager = new CustomizedRedisCacheManager(connectionFactory,redisTemplate,cacheItemConfigs);
        return cacheManager;
    }
}
