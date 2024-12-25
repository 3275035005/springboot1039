package com.lyxy.studentdocument.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.entity.HealthDocument;
import com.lyxy.studentdocument.service.HealthDocumentService;
import com.lyxy.studentdocument.utils.DTO.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 */
//@Slf4j
@Api(tags = "健康文档相关接口")
@RestController
@RequestMapping("api/healthDocument")
public class HealthDocumentController {

    @Resource
    private HealthDocumentService healthDocumentService;

    @ApiOperation(value = "基础接口: 分页返回数据")
    @PostMapping(value = "page")
    public ResponseEntity<Page<HealthDocument>> page(@RequestBody Condition condition) {
        //log.info("传过来的数据======={}",condition);
        return ResponseEntity.ok(healthDocumentService.selectPage(condition));
    }

    @ApiOperation(value = "基础接口: 返回指定ID的数据")
    @GetMapping(value = "get/{id}")
    public ResponseEntity<HealthDocument> get(@PathVariable("id") Integer id) {
        HealthDocument byId = healthDocumentService.getById(id);
        if (byId != null) {
            return ResponseEntity.ok(byId);
        }
        throw new MyException(ExceptionEnums.GET_ITEM);
    }

    @ApiOperation(value = "增加阅读数量")
    @GetMapping(value = "view/{id}")
    public ResponseEntity<HealthDocument> view(@PathVariable("id") Integer id) {
        HealthDocument byId = healthDocumentService.getById(id);
        Integer visitNum = Integer.parseInt(byId.getVisitNum()) + 1;
        byId.setVisitNum(visitNum.toString());

        this.update(byId);
        if (byId != null) {
            return ResponseEntity.ok(byId);
        }
        throw new MyException(ExceptionEnums.GET_ITEM);
    }

    @ApiOperation(value = "基础接口: 修改数据")
    @PostMapping(value = "update")
    @RequiresPermissions("healthDocument:update")
    public ResponseEntity<HealthDocument> update(@RequestBody HealthDocument healthDocument) {
        HealthDocument document = healthDocumentService.updateHealthDocument(healthDocument);
        if (document != null) {
            return ResponseEntity.ok(healthDocument);
        }
        throw new MyException(ExceptionEnums.UPDATE_ERROR);
    }

    @ApiOperation(value = "基础接口: 删除指定ID的数据")
    @GetMapping(value = "delete/{id}")
    @RequiresPermissions("healthDocument:delete")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        boolean b = healthDocumentService.removeById(id);
        if (b) {
            return ResponseEntity.ok("删除成功");
        }
        throw new MyException(ExceptionEnums.DELETE_ERROR);
    }

    @ApiOperation(value = "基础接口: 新增操作")
    @PostMapping(value = "add")
    @RequiresPermissions(value = {"healthDocument:add"}, logical = Logical.AND)
    public ResponseEntity<HealthDocument> save(@RequestBody HealthDocument healthDocument) {
        healthDocument.setUpdateDatetime(new Date());
        healthDocument.setCreateDatetime(new Date());
        healthDocument.setPublishData(new Date());

        if (null == healthDocument.getVisitNum()) {
            healthDocument.setVisitNum("0");
        }

        boolean save = healthDocumentService.save(healthDocument);
        if (save) {
            return ResponseEntity.ok(healthDocument);
        }
        throw new MyException(ExceptionEnums.ADD_ERROR);
    }


}

