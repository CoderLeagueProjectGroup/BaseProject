package com.coderleague.module.user.service;

import com.coderleague.module.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
public interface IUserService extends IService<User> {


    /**
     * 加密
     * @param password 密码
     * @param salt 盐
     * @return
     */
    String encrypt(String password, String salt);

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    User getUser(Integer userId);

    /**
     * 验证登陆
     * @param userId
     * @param token
     * @param timestamp
     * @return
     */
    boolean checkLogin(String userId,String token,String timestamp);
}
