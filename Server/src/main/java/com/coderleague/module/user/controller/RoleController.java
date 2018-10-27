package com.coderleague.module.user.controller;


import com.coderleague.common.util.LoginUtil;
import com.coderleague.module.user.service.IRoleService;
import com.coderleague.module.user.vo.MenuItem;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@RestController
@RequestMapping("/user/role")
public class RoleController {


    @Autowired
    private IRoleService roleService;

    @ApiOperation(value="获取当前账号菜单的接口")
    @GetMapping(value = "menu")
    public List<MenuItem> getMenu(){
        return roleService.getMenu(LoginUtil.getUser().getRoleId());
    }
}
