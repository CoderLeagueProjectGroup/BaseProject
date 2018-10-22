package com.coderleague.module.user.service.impl;

import com.coderleague.module.user.entity.Role;
import com.coderleague.module.user.mapper.RoleMapper;
import com.coderleague.module.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qcb
 * @since 2018-10-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
