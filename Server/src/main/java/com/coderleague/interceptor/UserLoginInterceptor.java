package com.coderleague.interceptor;

import com.alibaba.fastjson.JSON;
import com.coderleague.common.entity.Constant;
import com.coderleague.common.entity.Result;
import com.coderleague.common.util.LoginUtil;
import com.coderleague.module.user.service.IUserService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证登陆
 * Created by DELL on 2018/10/23.
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> map = getLoginParams(request);
        Result result=null;
        if (StringUtils.isEmpty(map.get(Constant.USER_ID_PARAM))
                || StringUtils.isEmpty(map.get(Constant.TOKEN_PARAM))
                || StringUtils.isEmpty(map.get(Constant.TIMESTAMP_PARAM))) {
            result=new Result(Constant.NO_AUTH_CODE,"登陆验证失败",null);
            writeResult(response,result);
            return false;
        }

        result = userService.checkLogin(Integer.valueOf(map.get(Constant.USER_ID_PARAM)),
                map.get(Constant.TOKEN_PARAM), map.get(Constant.TIMESTAMP_PARAM));
        if (result.getCode() == Constant.NO_AUTH_CODE) {
            writeResult(response,result);
            return false;
        }
        //存储当前登陆用户信息
        LoginUtil.setUser(userService.getUser(Integer.valueOf(map.get(Constant.USER_ID_PARAM))));
        return super.preHandle(request, response, handler);
    }

    private void writeResult(HttpServletResponse response, Result result) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        //移除当前登陆用户信息
        LoginUtil.removeUser();
    }

    /**
     * 获取登陆参数
     *
     * @param request
     * @return
     */
    private Map<String, String> getLoginParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        //先从header里面取，取不到再从请求参数中取
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization)) {
            map.put(Constant.USER_ID_PARAM, request.getParameter(Constant.USER_ID_PARAM));
            map.put(Constant.TOKEN_PARAM, request.getParameter(Constant.TOKEN_PARAM));
            map.put(Constant.TIMESTAMP_PARAM, request.getParameter(Constant.TIMESTAMP_PARAM));
        } else {
            String[] arr = authorization.split("&");
            for (int i = 0; i < arr.length; i++) {
                if (!StringUtils.isEmpty(arr[i])) {
                    String[] strs = arr[i].split("=");
                    if (strs.length > 1) {
                        map.put(strs[0], strs[1]);
                    }
                }
            }
        }
        return map;
    }
}
