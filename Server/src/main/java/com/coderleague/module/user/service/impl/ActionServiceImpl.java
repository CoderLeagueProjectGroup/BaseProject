package com.coderleague.module.user.service.impl;

import com.coderleague.module.user.entity.Action;
import com.coderleague.module.user.mapper.ActionMapper;
import com.coderleague.module.user.service.IActionService;
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
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements IActionService {

}
