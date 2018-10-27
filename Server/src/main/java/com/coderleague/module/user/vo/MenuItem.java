package com.coderleague.module.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2018/10/27.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="菜单", description="")
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem implements Serializable {

    private static final long serialVersionUID = -968878680139764690L;

    private Integer id;
    /**
     * 名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 操作url
     */
    private String url;

    /**
     * 子菜单
     */
    private List<MenuItem> children;
}
