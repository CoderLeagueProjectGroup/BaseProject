package com.coderleague.module.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coderleague.module.user.entity.User;
import com.coderleague.module.user.mapper.UserMapper;
import com.coderleague.module.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 加密
     * @param password 密码
     * @param salt 盐
     * @return
     */
    @Override
    public String encrypt(String password, String salt) {
        return DigestUtils.sha1Hex(password+salt);
    }


    @Cacheable(value = "UserCache")
    @Override
    public User getUser(Integer userId) {
        return getById(userId);
    }

    @Override
    public boolean checkLogin(String userId, String token, String timestamp) {
        return false;
    }
}
