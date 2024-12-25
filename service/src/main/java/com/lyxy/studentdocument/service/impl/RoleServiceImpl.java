package com.lyxy.studentdocument.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.Role;
import com.lyxy.studentdocument.dao.RoleMapper;
import com.lyxy.studentdocument.entity.User;
import com.lyxy.studentdocument.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyxy.studentdocument.utils.DTO.Condition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectAll() {
        List<Role> roles = roleMapper.selectList(null);
        return roles;
    }

    @Override
    public Page selectPage(Condition condition) {
        Page<Role> rolePage = new Page<>(condition.getPageNum(), condition.getPageSize());
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String column = condition.getClauses().get(0).getColumn();
        String value = (String) condition.getClauses().get(0).getValue();
        //根据角色备注remark是否存在 分页查询
        roleLambdaQueryWrapper.like(column != null, Role::getRemark, value)
                .orderByDesc(Role::getUpdateDatetime);
        Page<Role> page = roleMapper.selectPage(rolePage, roleLambdaQueryWrapper);
        return page;
    }
}
