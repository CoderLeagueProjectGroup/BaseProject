package com.coderleague.module.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ModuleAction对象", description="")
public class ModuleAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Integer id;

    @ApiModelProperty(value = "模块id")
    @TableField("module_id")
    private Integer moduleId;

    @ApiModelProperty(value = "操作id")
    @TableField("action_id")
    private Integer actionId;

    @ApiModelProperty(value = "是否主操作，0否，1是")
    @TableField("main")
    private Integer main;


}
