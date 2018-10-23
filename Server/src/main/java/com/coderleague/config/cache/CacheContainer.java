package com.coderleague.config.cache;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by DELL on 2018/10/23.
 */
public class CacheContainer {

    private static final String DEFAULT_CACHE_NAME="default";

    private static final Map<String,CacheItemConfig> CACHE_CONFIG_HOLDER=new ConcurrentHashMap<String,CacheItemConfig>(){
        {
            put(DEFAULT_CACHE_NAME,new CacheItemConfig(){
                @Override
                public String getName() {
                    return DEFAULT_CACHE_NAME;
                }

                @Override
                public long getExpireTimeSecond() {
                    return 30;
                }

                @Override
                public long getPreloadTimeSecond() {
                    return 25;
                }
            });
        }
    };

    public static void init(List<CacheItemConfig> cacheItemConfigs){
        if(CollectionUtils.isEmpty(cacheItemConfigs)){
            return;
        }
        cacheItemConfigs.forEach(cacheItemConfig -> CACHE_CONFIG_HOLDER.put(cacheItemConfig.getName(),cacheItemConfig));
    }

    public static CacheItemConfig getCacheItemConfigByCacheName(String cacheName){
        if(CACHE_CONFIG_HOLDER.containsKey(cacheName)){
            return CACHE_CONFIG_HOLDER.get(cacheName);
        }
        return CACHE_CONFIG_HOLDER.get(DEFAULT_CACHE_NAME);
    }

    public static List<CacheItemConfig> getCacheItemConfigs(){
        return CACHE_CONFIG_HOLDER
                .values()
                .stream()
                .filter(cacheItemConfig -> !DEFAULT_CACHE_NAME.equals(cacheItemConfig.getName()))
                .collect(Collectors.toList());
    }


}
