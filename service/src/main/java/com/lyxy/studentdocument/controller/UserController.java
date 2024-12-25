package com.lyxy.studentdocument.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.entity.User;
import com.lyxy.studentdocument.service.UserService;
import com.lyxy.studentdocument.utils.DTO.Condition;
import com.lyxy.studentdocument.utils.MD5Utils;
import com.lyxy.studentdocument.utils.UserThreadLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Slf4j
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("api/user")
public class UserController {

    @Resource
    UserService userService;

    @ApiOperation(value = "用户登录接口")
    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User loginUser = userService.login(user);
        //log.info("用户{}",user);
        UserThreadLocal.put(loginUser);
        return ResponseEntity.ok(loginUser);
    }

    @GetMapping("login")
    @ResponseBody
    public ResponseEntity login() {
        return ResponseEntity.status(301).body("登录过期或者未登录,请退出重新登录");
    }

    @ApiOperation(value = "基础接口: 返回所有数据")
    @GetMapping(value = "all")
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(userService.list());
    }

    @ApiOperation(value = "用户注销接口")
    @GetMapping("/loginOut")
    public ResponseEntity<String> loginOut() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        UserThreadLocal.remove();
        return ResponseEntity.ok("退出登录");

    }

    @ApiOperation(value = "基础接口: 返回指定ID的数据")
    @GetMapping(value = "get/{id}")
    public ResponseEntity<User> get(@PathVariable("id") Integer id) {
        User user = userService.selectByKey(id);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "基础接口: 添加用户")
    @PostMapping("add")
    public ResponseEntity<User> save(@RequestBody User user) {
        boolean b = userService.saveByuserName(user);
        //user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        if (b) {
            return ResponseEntity.ok(user);
        }
        throw new MyException(ExceptionEnums.ADD_ERROR);
    }

    @ApiOperation(value = "基础接口: 修改数据")
    @PostMapping(value = "update")
    public ResponseEntity<User> update(@RequestBody User user) {
        user.setUpdateDatetime(new Date());
        user.setCreateDatetime(new Date());
        if (!userService.updateById(user.getId()).getPassword().equals(user.getPassword())) {
            user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        }
        return userService.update(user);
    }

    @ApiOperation(value = "获取所有相应角色用户")
    @GetMapping("/getAllStudent/{roleId}")
    public ResponseEntity<List<User>> getAllStudent(@PathVariable("roleId") Integer roleId) {
        List<User> users = userService.selectUserByRoleId(roleId);
        return ResponseEntity.ok(users);
    }


    @ApiOperation(value = "基础接口: 分页返回数据")
    @PostMapping(value = "page")
    public ResponseEntity<Page<User>> page(@RequestBody Condition condition) {
        //log.info();
        return ResponseEntity.ok(userService.selectPage(condition));
    }

    @ApiOperation(value = "基础接口: 删除指定ID的数据")
    @GetMapping(value = "delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        boolean b = userService.removeById(id);
        if (b) {
            return ResponseEntity.ok("删除成功");
        }
        throw new MyException(ExceptionEnums.DELETE_ERROR);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUserName(@RequestBody User user) {
        ResponseEntity<User> update = this.update(user);
        return update;
    }
}

