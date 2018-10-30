package com.coderleague.module.user.controller;


import com.coderleague.common.entity.Constant;
import com.coderleague.common.entity.Result;
import com.coderleague.common.util.LoginUtil;
import com.coderleague.module.user.service.IRoleService;
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
    public Result<List> getMenu(){
        return new Result<>(Constant.SUCCESS_CODE,null,
                roleService.getMenu(LoginUtil.getUser().getRoleId()));
    }
}
