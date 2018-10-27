package com.coderleague.module.user.mapper;

import com.coderleague.module.user.entity.Module;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
public interface ModuleMapper extends BaseMapper<Module> {

    /**
     * 根据角色id获取功能集合
     * @param roleId
     * @param isMenu
     * @return
     */
    List<Module> getListByRoleId(@Param("roleId") Integer roleId,@Param("isMenu") Boolean isMenu);
}
