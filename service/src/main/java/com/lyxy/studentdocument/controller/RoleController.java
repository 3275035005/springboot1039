package com.lyxy.studentdocument.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.entity.Role;
import com.lyxy.studentdocument.service.RoleService;
import com.lyxy.studentdocument.utils.DTO.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Slf4j
@Api(tags = "角色相关接口")
@Controller
@RequestMapping(value = "api/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseEntity<List<Role>> list() {
        List<Role> roles = roleService.selectAll();
        if (!CollectionUtils.isEmpty(roles)) {
            return ResponseEntity.ok(roles.stream().filter(v ->  v.getRoleName().contains("医生")
                    || v.getRoleName().contains("辅导员")
                    || v.getRoleName().contains("用户")).collect(Collectors.toList()));
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping(value = "get/{id}")
    public ResponseEntity<Role> get(@PathVariable("id") String id) {
        Role role = roleService.getById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        }
        throw new MyException(ExceptionEnums.GET_ITEM);

    }

    @ApiOperation(value = "基础接口: 分页返回数据")
    @PostMapping(value = "page")
    public ResponseEntity<Page<Role>> page(@RequestBody Condition condition) {
        Page page = roleService.selectPage(condition);
        if (page != null) {
            return ResponseEntity.ok(page);
        }
        throw new MyException(ExceptionEnums.GET_LIST_ERROR);
    }

    @ApiOperation(value = "基础接口: 修改数据")
    @PostMapping(value = "update")
    @RequiresPermissions("role:update")
    public ResponseEntity<Role> update(@RequestBody Role role) {
        role.setUpdateDatetime(new Date());
        role.setCreateDatetime(new Date());
        boolean b = roleService.updateById(role);
        if (b) {
            return ResponseEntity.ok(role);
        }
        throw new MyException(ExceptionEnums.UPDATE_ERROR);
    }

    @ApiOperation(value = "基础接口: 删除指定ID的数据")
    @GetMapping(value = "delete/{id}")
    @RequiresPermissions("role:delete")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        boolean b = roleService.removeById(id);
        if (b) {
            return ResponseEntity.ok("删除成功");
        }
        throw new MyException(ExceptionEnums.DELETE_ERROR);
    }

    @ApiOperation(value = "基础接口: 新增操作")
    @PostMapping(value = "add")
    @RequiresPermissions("role:add")
    public ResponseEntity<Role> save(@RequestBody Role role) {
        role.setUpdateDatetime(new Date());
        role.setCreateDatetime(new Date());

        boolean save = roleService.save(role);
        if (save) {
            return ResponseEntity.ok(role);
        }
        throw new MyException(ExceptionEnums.ADD_ERROR);
    }

    @ApiOperation(value = "基础接口: 返回所有数据")
    @GetMapping(value = "all")
    public ResponseEntity<List<Role>> all() {
        List<Role> list = roleService.list();
        if (list != null) {
            return ResponseEntity.ok(list);
        }
        throw new MyException(ExceptionEnums.GET_LIST_ERROR);
    }

}

