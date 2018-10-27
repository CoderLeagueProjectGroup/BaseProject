package com.coderleague.module.user.service.impl;

import com.coderleague.module.user.entity.Module;
import com.coderleague.module.user.mapper.ModuleMapper;
import com.coderleague.module.user.service.IModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {

    /**
     * 根据角色id获取功能集合
     * @param roleId
     * @return
     */
    public List<Module> getListByRoleId(Integer roleId){
        return baseMapper.getListByRoleId(roleId,null);
    }

    /**
     * 根据角色id获取菜单
     * @param roleId
     * @return
     */
    @Override
    public List<Module> getMenuListByRoleId(Integer roleId) {
        return baseMapper.getListByRoleId(roleId,true);
    }
}
