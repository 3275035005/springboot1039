package com.lyxy.studentdocument.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyxy.studentdocument.utils.DTO.Condition;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface UserService extends IService<User> {

    User login(User user);

    User selectByKey(Integer userId);

    List<User> selectUserByRoleId(Integer roleId);

    boolean saveByuserName(User user);

    Page<User> selectPage(Condition condition);

    User updateById(Integer id);

    ResponseEntity<User> update(User user);
}
