package com.coderleague.module.user.service.impl;

import com.coderleague.module.user.entity.Department;
import com.coderleague.module.user.mapper.DepartmentMapper;
import com.coderleague.module.user.service.IDepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
