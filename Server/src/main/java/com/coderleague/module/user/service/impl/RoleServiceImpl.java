package com.coderleague.module.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coderleague.common.entity.Constant;
import com.coderleague.module.user.entity.Module;
import com.coderleague.module.user.entity.Role;
import com.coderleague.module.user.mapper.RoleMapper;
import com.coderleague.module.user.service.IModuleService;
import com.coderleague.module.user.service.IRoleService;
import com.coderleague.module.user.vo.MenuItem;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IModuleService moduleService;

    /**
     * 根据角色获取
     *
     * @param roleId
     * @return
     */
    @Cacheable(value = Constant.MENU_CACHE)
    @Override
    public List<MenuItem> getMenu(Integer roleId) {
        List<Module> moduleList = moduleService.getMenuListByRoleId(roleId);
        if (CollectionUtils.isEmpty(moduleList)) {
            return Lists.newArrayList();
        }
        //获取一级菜单
        List<MenuItem> menuItemList = moduleList.stream()
                .filter(m -> m.getParentId() == 0)
                .sorted(new ModuleComparator())
                .map(m -> new MenuItem(m.getId(), m.getName(), m.getIcon(), m.getActionUrl(), Lists.newArrayList()))
                .collect(Collectors.toList());
        //获取一级菜单的子菜单
        Map<Integer, List<Module>> menuMap = moduleList.stream()
                .collect(Collectors.groupingBy(Module::getParentId));
        menuItemList.forEach(m -> getChildrenMenuList(m,menuMap));
        return menuItemList;
    }

    /**
     * 菜单排序
     */
    private static class ModuleComparator implements Comparator<Module>{

        @Override
        public int compare(Module o1, Module o2) {
            if(o1.getOrderNum()==o2.getOrderNum()){
                return (int)(o1.getModifyTime().toEpochSecond(ZoneOffset.of("+8"))
                        -o2.getModifyTime().toEpochSecond(ZoneOffset.of("+8")));
            }else{
                return o1.getOrderNum()-o2.getOrderNum();
            }
        }
    }
    /**
     * 递归获取菜单的子菜单
     *
     * @param menuItem
     * @param menuMap
     */
    private void getChildrenMenuList(MenuItem menuItem, Map<Integer, List<Module>> menuMap) {
        if (menuItem == null) {
            return;
        }
        List<Module> moduleList =menuMap.get(menuItem.getId());
        if(CollectionUtils.isEmpty(moduleList))
            return;
        //按照排序序号和修改日期排序
        moduleList.sort(new ModuleComparator());
        List<MenuItem> children = moduleList.stream()
                .map(module -> new MenuItem(module.getId(), module.getName(), module.getIcon(), module.getActionUrl(), Lists.newArrayList()))
                .collect(Collectors.toList());
        menuItem.getChildren().addAll(children);
        if(CollectionUtils.isEmpty(menuItem.getChildren())) return;
        menuItem.getChildren().forEach(m -> getChildrenMenuList(m, menuMap));
    }
}
