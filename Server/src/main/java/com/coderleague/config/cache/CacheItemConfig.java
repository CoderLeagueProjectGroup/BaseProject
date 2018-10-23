package com.coderleague.config.cache;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by DELL on 2018/10/23.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CacheItemConfig implements Serializable {

    private static final long serialVersionUID = -371507697619851900L;

    /**
     * 缓存容器名称
     */
    private String name;

    /**
     * 缓存失效时间
     */
    private long expireTimeSecond;

    /**
     * 当缓存存活时间达到此值时，主动刷新缓存
     */
    private long preloadTimeSecond;
}
