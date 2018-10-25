package com.coderleague.module.user.controller;


import com.coderleague.common.entity.Result;
import com.coderleague.common.exception.ParamException;
import com.coderleague.module.user.entity.User;
import com.coderleague.module.user.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Autowired
    private IUserService userService;

    @ApiOperation(value="登陆接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @PostMapping("/login")
    public Result login(@RequestBody @Valid User user, BindingResult bindingResult){
        Result result = new Result();
        if(bindingResult.hasErrors()){
            for(FieldError fieldError:bindingResult.getFieldErrors()){
                result.setCode(401);
                result.setMsg(fieldError.getDefaultMessage());
                return result;
            }
        }
        result=userService.login(user);
        return result;
    }
}
