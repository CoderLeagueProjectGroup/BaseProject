package com.coderleague.module.user.controller;


import com.coderleague.common.entity.Result;
import com.coderleague.common.exception.ParamException;
import com.coderleague.module.user.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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


    @ApiOperation(value="一个测试API",notes = "第一个测试api")
    @GetMapping("/test")
    public User test(){
        throw new ParamException("test");
    }

    @PostMapping("/login")
    public Result login(@Valid User user, BindingResult bindingResult){
        Result result = new Result();
        if(bindingResult.hasErrors()){
            for(FieldError fieldError:bindingResult.getFieldErrors()){
                result.setCode(401);
                result.setMsg(fieldError.getDefaultMessage());
                return result;
            }
        }
        return result;
    }
}
