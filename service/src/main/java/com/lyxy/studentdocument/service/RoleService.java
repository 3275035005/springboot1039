package com.lyxy.studentdocument.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyxy.studentdocument.utils.DTO.Condition;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface RoleService extends IService<Role> {
    List<Role> selectAll();

    Page selectPage(Condition condition);

}
