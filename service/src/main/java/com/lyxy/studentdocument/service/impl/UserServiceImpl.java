package com.lyxy.studentdocument.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.entity.User;
import com.lyxy.studentdocument.dao.UserMapper;
import com.lyxy.studentdocument.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyxy.studentdocument.utils.DTO.Condition;
import com.lyxy.studentdocument.utils.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        String userName = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User loginUser = (User) subject.getPrincipal();
            if (loginUser != null) {
                return loginUser;
            }
        } catch (AuthenticationException e) {
            log.info("登陆失败:"+e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public User selectByKey(Integer userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    @Override
    public List<User> selectUserByRoleId(Integer roleId) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getRoleId, roleId);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        return users;
    }

    @Override
    public boolean saveByuserName(User user) {
        if (user.getRoleId() == null) {
            user.setRoleId(2);
        }
        if (user.getUsername() == null) {
            user.setUsername(user.getStuNo());
        }
        if (user.getPassword() == null) {
            user.setPassword("123");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            throw new MyException(ExceptionEnums.ADD_ERROR);
        }
        QueryWrapper<User> username = new QueryWrapper<User>().eq("username", user.getUsername());
        User one = userMapper.selectOne(username);
        if (one != null) {
            throw new MyException(ExceptionEnums.ACCOUNT_IS_EXIT);
        }
        user.setCreateDatetime(new Date());
        user.setUpdateDatetime(new Date());
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Page<User> selectPage(Condition condition) {
        Page<User> userPage = new Page<>(condition.getPageNum(), condition.getPageSize());
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String college = (String) condition.getClauses().get(0).getValue();
        String major = (String) condition.getClauses().get(1).getValue();
        String name = (String) condition.getClauses().get(2).getValue();
        String stuNo = (String) condition.getClauses().get(3).getValue();
        if ("".equals(college) && "".equals(major) && "".equals(name) && "".equals(stuNo)) {
            userLambdaQueryWrapper.orderByDesc(User::getUpdateDatetime);
        } else {
            userLambdaQueryWrapper.like(User::getCollege, college)
                    .like(User::getMajor, major)
                    .like(User::getName, name)
                    .like(User::getStuNo, stuNo)
                    .orderByDesc(User::getUpdateDatetime);
        }

        Page<User> page = userMapper.selectPage(userPage, userLambdaQueryWrapper);
        log.info("条件分页后的用户数据{}", page.getRecords());
        return page;
    }

    @Override
    public User updateById(Integer id) {
        User user = userMapper.selectById(id);
        if (user != null){
            return user;
        }
        throw new MyException(ExceptionEnums.UPDATE_ERROR);
    }

    @Override
    public ResponseEntity<User> update(User user) {
        userMapper.updateById(user);
        return ResponseEntity.ok(user);
    }

    public static void main(String[] args) {

        System.out.println(MD5Utils.encrypt("waike", "666"));
    }
}
