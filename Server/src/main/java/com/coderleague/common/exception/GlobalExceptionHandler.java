package com.coderleague.common.exception;

import com.coderleague.common.entity.Result;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * Created by DELL on 2018/10/22.
 */
@Component
public class GlobalExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @Nullable Object o, Exception e) {
        ModelAndView mv=new ModelAndView(new MappingJackson2JsonView());
        Result result;
        if(e instanceof ParamException){
            mv.setStatus(HttpStatus.BAD_REQUEST);
            ParamException paramException = (ParamException) e;
            result=new Result(400,paramException.getMessage(),null);
        }else{
            mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            result=new Result(500,"网络发生错误",null);
        }
        mv.addAllObjects(BeanMap.create(result));
        return mv;
    }
}
