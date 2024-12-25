package com.lyxy.studentdocument.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyxy.studentdocument.entity.RoleResourceBind;
import com.lyxy.studentdocument.dao.RoleResourceBindMapper;
import com.lyxy.studentdocument.service.RoleResourceBindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class RoleResourceBindServiceImpl extends ServiceImpl<RoleResourceBindMapper, RoleResourceBind> implements RoleResourceBindService {
    @Resource
    private RoleResourceBindMapper roleResourceBindMapper;

    @Override
    public List<Integer> getRoleIds(Integer resourceId) {
        LambdaQueryWrapper<RoleResourceBind> roleResourceBindLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleResourceBindLambdaQueryWrapper.eq(RoleResourceBind::getResourceId, resourceId);
        List<RoleResourceBind> list = roleResourceBindMapper.selectList(roleResourceBindLambdaQueryWrapper);
        List<Integer> collect = list.stream().map(RoleResourceBind::getRoleId).collect(Collectors.toList());
        return collect;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addBindInfo(Integer resourceId, List<Integer> roleList) {
        return roleResourceBindMapper.addBindInfo(resourceId,roleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteBindInfo(Integer resourceId) {
        return roleResourceBindMapper.deleteBindInfo(resourceId);
    }
}
