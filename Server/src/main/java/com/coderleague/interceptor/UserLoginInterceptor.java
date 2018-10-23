package com.coderleague.interceptor;

import com.coderleague.common.entity.Constant;
import com.sun.deploy.net.HttpUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证登陆
 * Created by DELL on 2018/10/23.
 */
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,String> map = getLoginParams(request);
        if(StringUtils.isEmpty(map.get(Constant.USER_ID_PARAM))
                ||StringUtils.isEmpty(map.get(Constant.TOKEN_PARAM))
                ||StringUtils.isEmpty(map.get(Constant.TIMESTAMP_PARAM))){
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * 获取登陆参数
     * @param request
     * @return
     */
    private Map<String, String> getLoginParams(HttpServletRequest request) {
        Map<String,String> map = new HashMap<>();
        //先从header里面取，取不到再从请求参数中取
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization)) {
            map.put(Constant.USER_ID_PARAM,request.getParameter(Constant.USER_ID_PARAM));
            map.put(Constant.TOKEN_PARAM,request.getParameter(Constant.TOKEN_PARAM));
            map.put(Constant.TIMESTAMP_PARAM,request.getParameter(Constant.TIMESTAMP_PARAM));
        } else {
            String[] arr=StringUtils.split(authorization,"&");
            for(int i=0;i<arr.length;i++){
                if(!StringUtils.isEmpty(arr[i])){
                    String[] strs=StringUtils.split(arr[i],"=");
                    if(strs.length>0){
                        map.put(strs[0],strs[1]);
                    }
                }
            }
        }
        return map;
    }
}
