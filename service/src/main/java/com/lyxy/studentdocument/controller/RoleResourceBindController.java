package com.lyxy.studentdocument.controller;


import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.service.RoleResourceBindService;
import com.lyxy.studentdocument.utils.DTO.BindInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Api(tags = "角色资源绑定相关接口")
@Controller
@RequestMapping(value = "api/roleResourceBind")
public class RoleResourceBindController {

    @Resource
    private RoleResourceBindService roleResourceBindService;

    @GetMapping("/getRoles/{resourceId}")
    @ResponseBody
    @ApiOperation(value = "获取某个资源下面所有绑定的角色")
    List<Integer> getRoleListByResourceId(@PathVariable("resourceId") Integer resourceId) {
        return roleResourceBindService.getRoleIds(resourceId);
    }

    @PostMapping("/saveBind")
    @ApiOperation(value = "删除原资源下面的所有角色,然后加上现在的所有角色")
    ResponseEntity<String> saveBind(@RequestBody BindInfo bindInfo) {
        try {
            if (Objects.equals(bindInfo.getRoleList(), this.getRoleListByResourceId(bindInfo.getResourceId()))) {
                return ResponseEntity.ok("资源信息已保存，权限信息未发生更改！");
            } else {
                roleResourceBindService.deleteBindInfo(bindInfo.getResourceId());
                roleResourceBindService.addBindInfo(bindInfo.getResourceId(), bindInfo.getRoleList());
            }
        } catch (Exception e) {
            throw new MyException(ExceptionEnums.UPDATE_ERROR);
        }
        return ResponseEntity.ok("更新权限信息成功");
    }

}

