package com.coderleague.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by DELL on 2018/10/22.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="结果对象", description="")
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    @ApiModelProperty(value = "状态码")
    private int code;

    @ApiModelProperty(value = "信息描述")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;
}
