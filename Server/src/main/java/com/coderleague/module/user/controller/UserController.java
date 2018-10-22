package com.coderleague.module.user.controller;


import com.coderleague.common.exception.ParamException;
import com.coderleague.module.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@RestController
@RequestMapping("/user/user")
public class UserController {


    @ApiOperation(value="一个测试API",notes = "第一个测试api")
    @GetMapping("/test")
    public User test(){
        throw new ParamException("test");
    }
}
