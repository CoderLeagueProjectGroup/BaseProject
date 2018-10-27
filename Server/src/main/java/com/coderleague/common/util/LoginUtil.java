package com.coderleague.common.util;

import com.coderleague.module.user.entity.User;

/**
 * Created by DELL on 2018/10/27.
 */
public class LoginUtil {

    public static final ThreadLocal<User> USER_HOLDER=new ThreadLocal<>();

    public static void setUser(User user){
        USER_HOLDER.set(user);
    }

    public static User getUser(){
        return USER_HOLDER.get();
    }

    public static void removeUser(){
        USER_HOLDER.remove();
    }
}
