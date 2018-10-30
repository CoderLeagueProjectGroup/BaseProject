package com.coderleague.common.entity;

import com.coderleague.module.user.entity.User;
import com.coderleague.module.user.vo.MenuItem;
import org.apache.tomcat.util.bcel.Const;

import java.time.Duration;
import java.util.List;

/**
 * Created by DELL on 2018/10/30.
 */
public enum CacheType {
    USER_CACHE(Constant.USER_CACHE_NAME, Duration.ofMinutes(30),User.class),
    MENU_CACHE(Constant.MENU_CACHE, Duration.ofMinutes(60), List.class);

    CacheType(String cacheName, Duration ttl, Class<?> clz) {
        this.cacheName = cacheName;
        this.ttl = ttl;
        this.clz = clz;
    }

    /**
     * 缓存名称
     */
    private String cacheName;
    /**
     * 存活时间
     */
    private Duration ttl;
    /**
     * 缓存对象
     */
    private Class<?> clz;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public Duration getTtl() {
        return ttl;
    }

    public void setTtl(Duration ttl) {
        this.ttl = ttl;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
