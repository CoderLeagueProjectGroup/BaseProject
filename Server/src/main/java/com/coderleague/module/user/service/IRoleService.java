package com.coderleague.module.user.service;

import com.coderleague.module.user.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.coderleague.module.user.vo.MenuItem;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
public interface IRoleService extends IService<Role> {


    /**
     * 根据角色获取
     * @param roleId
     * @return
     */
    List<MenuItem> getMenu(Integer roleId);
}
