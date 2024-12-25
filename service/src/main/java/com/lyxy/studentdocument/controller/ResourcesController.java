package com.lyxy.studentdocument.controller;


import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;

import com.lyxy.studentdocument.entity.Resources;
import com.lyxy.studentdocument.entity.User;
import com.lyxy.studentdocument.service.ResourcesService;
import com.lyxy.studentdocument.service.RoleResourceBindService;
import com.lyxy.studentdocument.shiro.ShiroService;
import com.lyxy.studentdocument.utils.Vo.ResourceVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 */
@Slf4j
@Api(tags = "资源相关接口")
@Controller
@RequestMapping(value = "api/resources")
public class ResourcesController {

    @Resource
    private ResourcesService resourcesService;

    @Resource
    private RoleResourceBindService roleResourceBindService;

    @Resource
    private ShiroService shiroService;

    /**
     * 被用户id菜单
     *
     * @param userId 用户id
     * @param typeId id类型  required=false 表示可以为null
     * @return {@link ResponseEntity}<{@link List}<{@link ResourceVO}>>
     */
    @GetMapping("/getMenuByUserId")
    ResponseEntity< List<ResourceVO> > getMenuByUserId(@RequestParam("userId") Integer userId,@RequestParam(value = "typeId", required = false) Integer typeId){
        List<ResourceVO> resourceVOS;
        try {
            resourceVOS = resourcesService.getResourceTreeByUserId(userId,typeId);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new MyException(ExceptionEnums.RESOURCE_FOUNT_FAIL);
        }
        if(resourceVOS == null){
            throw new MyException(ExceptionEnums.RESOURCE_FOUNT_FAIL);
        }
        return ResponseEntity.ok(resourceVOS);
    }

    @ApiOperation(value = "基础接口: 新增操作")
    @PostMapping(value = "add")
    @RequiresPermissions("resource:add")
    public ResponseEntity<Resources> save(@RequestBody Resources resource){
        resource.setCreateDatetime(new Date());
        resource.setUpdateDatetime(new Date());
        boolean save = resourcesService.save(resource);
        if (save){
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            List<Integer> list = new ArrayList<>();
            list.add(user.getRoleId());
            roleResourceBindService.addBindInfo(resource.getId(),list);
            shiroService.updatePermission();
            return ResponseEntity.ok(resource);
        }
        throw new MyException(ExceptionEnums.ADD_ERROR);
    }

    @ApiOperation(value = "基础接口: 删除操作")
    @GetMapping(value = "delete/{id}")
    @RequiresPermissions("resource:delete")
    public ResponseEntity<String> delete(@PathVariable("id")Integer id){
        boolean b = resourcesService.removeById(id);
        if (b) {
            shiroService.updatePermission();
            return ResponseEntity.ok("删除成功");
        }
        throw new MyException(ExceptionEnums.DELETE_ERROR);
    }

    @ApiOperation(value = "基础接口: 修改数据")
    @PostMapping(value = "update")
    @RequiresPermissions("resource:update")
    public ResponseEntity<Resources> update(@RequestBody Resources resource){
        resource.setCreateDatetime(new Date());
        resource.setUpdateDatetime(new Date());
        boolean b = resourcesService.updateById(resource);
        if (b){
            shiroService.updatePermission();
            return ResponseEntity.ok(resource);
        }
        throw new MyException(ExceptionEnums.UPDATE_ERROR);
    }
}

