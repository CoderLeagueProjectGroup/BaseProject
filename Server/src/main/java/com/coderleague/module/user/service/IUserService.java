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
     * @param user
     * @return
     */
    String encrypt(User user);
}
