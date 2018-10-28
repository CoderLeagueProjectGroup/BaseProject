package com.coderleague.module.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coderleague.common.entity.Constant;
import com.coderleague.common.entity.Result;
import com.coderleague.common.util.LoginUtil;
import com.coderleague.module.user.entity.User;
import com.coderleague.module.user.mapper.UserMapper;
import com.coderleague.module.user.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Value("${login.expire:36000}")
    private long loginExpireSeconds;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 加密
     *
     * @param password 密码
     * @param salt     盐
     * @return
     */
    @Override
    public String encrypt(String password, String salt) {
        return DigestUtils.sha1Hex(password + salt);
    }


    @Cacheable(value = Constant.USER_CACHE_NAME)
    @Override
    public User getUser(Integer userId) {
        return getById(userId);
    }

    /**
     * 验证登陆
     *
     * @param userId
     * @param token
     * @param timestamp
     * @return
     */
    @Override
    public Result checkLogin(int userId, String token, String timestamp) {
        Result result = new Result();
        result.setCode(200);
        String loginToken = (String) redisTemplate.opsForValue().get(Constant.LOGIN_CACHE_NAME + ":" + userId);
        if (StringUtils.isBlank(loginToken)) {
            result.setCode(401);
            result.setMsg("登陆验证失败");
            return result;
        }

        long now = System.currentTimeMillis();
        long mills = Long.valueOf(timestamp);
        //如果时间戳与当前时间误差大于5分钟
        if (Math.abs(now - mills) > 60 * 1000 * 5) {
            result.setCode(401);
            result.setMsg("时间戳过期");
            return result;
        }

        //校验token
        if (!token.equals(DigestUtils.md5Hex(loginToken + timestamp))) {
            result.setCode(401);
            result.setMsg("登陆验证失败");
            return result;
        }
        return result;
    }

    /**
     * 登陆
     *
     * @param user
     * @return
     */
    @Override
    public Result<String> login(User user) {
        Result result = new Result();
        User u = getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (u == null) {
            result.setCode(401);
            result.setMsg("用户名或密码错误");
            return result;
        }

        String encryptedPassword = encrypt(user.getPassword(), u.getSalt());
        if (!u.getPassword().equals(encryptedPassword)) {
            result.setCode(401);
            result.setMsg("用户名或密码错误");
            return result;
        }
        //验证通过，发放token
        result.setCode(200);
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(Constant.LOGIN_CACHE_NAME + ":" + u.getId(), token, loginExpireSeconds, TimeUnit.SECONDS);
        Map<String,Object> data=new HashMap<>(2);
        data.put("id",u.getId());
        data.put("token",token);
        result.setData(data);
        return result;
    }

    /**
     * 获取用户名
     * @return
     */
    @Override
    public Result<String> getUsername() {
        return new Result<>(200,null, LoginUtil.getUser().getUsername());
    }

    public static void main(String[] args) {
        long mills =System.currentTimeMillis();
        System.out.println(mills);
        System.out.println(DigestUtils.md5Hex("be3317b8b07347e9b013c02ac2c3d328"+mills));
    }
}
