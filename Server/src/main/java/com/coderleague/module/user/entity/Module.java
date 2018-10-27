package com.coderleague.module.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@ApiModel(value="Module对象", description="")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "功能模块名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty(value = "是否菜单项，0否，1是")
    @TableField("menu_flag")
    private Integer menuFlag;

    @ApiModelProperty(value = "操作url")
    @TableField("action_url")
    private String actionUrl;

    @ApiModelProperty(value = "父 模块id，没有的话为0")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "排序序号")
    @TableField("order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人id")
    @TableField("create_id")
    private Integer createId;

    @ApiModelProperty(value = "修改时间")
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "修改人id")
    @TableField("modify_id")
    private Integer modifyId;

    @ApiModelProperty(value = "是否逻辑删除，0否(默认),1是")
    @TableField("delete_flag")
    @TableLogic
    private Integer deleteFlag;


}
