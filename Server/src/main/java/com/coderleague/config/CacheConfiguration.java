package com.coderleague.config;

import com.coderleague.common.entity.CacheType;
import com.coderleague.common.entity.Constant;
import com.coderleague.config.cache.CacheItemConfig;
import com.coderleague.config.cache.CustomizedRedisCacheManager;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.redis.cache.RedisCacheConfiguration.registerDefaultConverters;

/**
 * Created by DELL on 2018/10/23.
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                .withInitialCacheConfigurations(getCacheConfig())
                .transactionAware()
                .build();
    }


    /**
     * 对每一个缓存单独配置TTL,value序列化器使用Jackson2JsonRedisSerializer
     */
    private Map<String,RedisCacheConfiguration> getCacheConfig(){
        Map<String,RedisCacheConfiguration> cacheConfigurations = new HashMap<>(16);
        for (CacheType cacheType : CacheType.values()) {
            Jackson2JsonRedisSerializer jsonRedisSerializer
                    = new Jackson2JsonRedisSerializer<>(cacheType.getClz());
            //解决Java8中LocalDateTime的序列化问题
            jsonRedisSerializer.setObjectMapper(new ObjectMapper()
                                                .registerModule(new JavaTimeModule()));
            RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(cacheType.getTtl())
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer));
            cacheConfigurations.put(cacheType.getCacheName(),config);
        }
        return cacheConfigurations;
    }


    /**
     * retemplate相关配置
     *
     * @param factory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate template = new RedisTemplate();
        // 配置连接工厂
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);

        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();

        return template;
    }
}
