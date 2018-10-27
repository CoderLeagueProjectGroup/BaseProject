package com.coderleague.module.user.service;

import com.coderleague.module.user.entity.Module;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
public interface IModuleService extends IService<Module> {


    /**
     * 根据角色id获取功能集合
     * @param roleId
     * @return
     */
    List<Module> getListByRoleId(Integer roleId);

    /**
     * 根据角色id获取菜单
     * @param roleId
     * @return
     */
    List<Module> getMenuListByRoleId(Integer roleId);
}
