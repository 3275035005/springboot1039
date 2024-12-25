package com.lyxy.studentdocument.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.entity.Suggestion;
import com.lyxy.studentdocument.service.SuggestionService;
import com.lyxy.studentdocument.utils.DTO.InfoDTO;
import com.lyxy.studentdocument.utils.DTO.InfoReadDTO;
import com.lyxy.studentdocument.utils.Vo.SuggestionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Api(tags = "医师建议相关接口")
@Controller
@RequestMapping(value = "api/suggestion")
public class SuggestionController {

    @Resource
    private SuggestionService suggestionService;

    @ApiOperation(value = "基础接口: 新增操作")
    @PostMapping(value = "add")
    @RequiresPermissions("suggestion:add")
    public ResponseEntity<Suggestion> save(@RequestBody Suggestion suggestion) {
        try {
            suggestion.setCreateDatetime(new Date());
            suggestion.setUpdateDatetime(new Date());
            if (!suggestionService.save(suggestion)) {
                throw new MyException(ExceptionEnums.ADD_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException(ExceptionEnums.ADD_ERROR);
        }
        return ResponseEntity.ok(suggestion);
    }

    @PostMapping("/getInfoPage")
    public ResponseEntity<Page<SuggestionVo>> getPage(@RequestBody InfoDTO infoDTO) {
        Page<SuggestionVo> page = suggestionService.selectPage(infoDTO);
        return ResponseEntity.ok(page);
    }

    @PostMapping("/deleteByIds")
    @RequiresPermissions("suggestion:delete")
    public ResponseEntity deleteByIds(@RequestBody Integer[] ids) {
        List<Integer> collect = Arrays.stream(ids).collect(Collectors.toList());
        if (suggestionService.removeByIds(collect)) {
            return ResponseEntity.ok("操作成功");
        } else {
            throw new MyException(ExceptionEnums.DELETE_ERROR);
        }
    }

    @PostMapping("/setInfoRead")
    public ResponseEntity setInfoRead(@RequestBody InfoReadDTO infoReadDTO) {
        boolean b = suggestionService.markToRead(infoReadDTO);
        if (b) {
            return ResponseEntity.ok("操作成功");
        }
        throw new MyException(ExceptionEnums.UPDATE_ERROR);
    }
}


