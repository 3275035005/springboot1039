package com.lyxy.studentdocument.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.entity.CheckInfo;
import com.lyxy.studentdocument.entity.HealthDocument;
import com.lyxy.studentdocument.service.CheckInfoService;
import com.lyxy.studentdocument.utils.DTO.AnalysisData;
import com.lyxy.studentdocument.utils.DTO.Condition;
import com.lyxy.studentdocument.utils.DTO.HealthDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 */
@Slf4j
@Api(tags = "体检表信息接口")
@Controller
@RequestMapping(value = "api/checkInfo")
public class CheckInfoController {

    @Resource
    private CheckInfoService checkInfoService;

    @GetMapping("/getCurrentCheckInfo/{userId}")
    ResponseEntity<CheckInfo> getCurrentCheckInfo(@PathVariable("userId") Integer userId) {
        return checkInfoService.getCurrentCheckInfo(userId);
    }

    @GetMapping("/getBim")
    ResponseEntity judgeIsHealth(Double height, Double weight) {
        String suggestion;
        if (height == null || weight == null) {
            throw new MyException(ExceptionEnums.NO_WEIGHT_HEIGHT);
        }
        Double result = weight / ((height / 100) * (height / 100));
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.UP);
        result = Double.valueOf(nf.format(result));
        if (result < 19) {
            suggestion = "体重偏低";
        } else if (result < 25) {
            suggestion = "健康体重";
        } else if (result < 30) {
            suggestion = "超重";
        } else if (result < 39) {
            suggestion = "严重超重";
        } else {
            suggestion = "极度超重";
        }
        return ResponseEntity.ok(new HealthDTO().setBim(result).setSuggestion(suggestion));
    }

    @GetMapping("/getDataAnalysis/{userId}")
    ResponseEntity getDataAnalysis(@PathVariable("userId") Integer userId) {
        List<CheckInfo> analysis = checkInfoService.getDataAnalysis(userId);
        if (analysis == null) {
            throw new MyException(ExceptionEnums.NO_CHECK_INFO);
        } else {
            List<String> label = new ArrayList<>();
            List<Double> height = new ArrayList<>();
            List<Double> weight = new ArrayList<>();
            analysis.forEach(v -> {
                label.add(v.getCheckYear());
                height.add(v.getHeight());
                weight.add(v.getWeight());
            });
            return ResponseEntity.ok(new AnalysisData().setLabel(label).setHeight(height).setWeight(weight));
        }
    }

    @ApiOperation(value = "基础接口: 分页返回数据")
    @PostMapping(value = "page")
    public ResponseEntity<Page<CheckInfo>> page(@RequestBody Condition condition) {
        //log.info();
        return ResponseEntity.ok(checkInfoService.selectPage(condition));
    }

    /**
     * 判断体检表是否存在
     *
     * @param userId    用户id
     * @param checkYear 检查年份
     * @return 是否存在
     */
    @GetMapping("/judgeCheckIsExist")
    ResponseEntity judgeCheckIsExist(Integer userId, String checkYear) {
        boolean aBoolean = checkInfoService.CheckIsExist(userId, checkYear);
        return ResponseEntity.ok(aBoolean);
    }

    @ApiOperation(value = "基础接口: 新增操作")
    @PostMapping(value = "add")
    @RequiresPermissions("checkInfo:add")
    public ResponseEntity<CheckInfo> save(@RequestBody CheckInfo checkInfo) {
        checkInfo.setCreateDatetime(new Date());
        checkInfo.setUpdateDatetime(new Date());
        LambdaQueryWrapper<CheckInfo> checkInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        checkInfoLambdaQueryWrapper.eq(CheckInfo::getCheckYear, checkInfo.getCheckYear());
        checkInfoLambdaQueryWrapper.eq(CheckInfo::getStuNo, checkInfo.getStuNo());

        CheckInfo check = checkInfoService.getOne(checkInfoLambdaQueryWrapper);
        if (null == check && checkInfo.getCheckYear() != null) {
            boolean save = checkInfoService.save(checkInfo);
            if (save) {
                return ResponseEntity.ok(checkInfo);
            }
        }
        throw new MyException(ExceptionEnums.ADD_ERROR);
    }

    @ApiOperation(value = "基础接口: 返回指定ID的数据")
    @GetMapping(value = "get/{id}")
    public ResponseEntity<CheckInfo> get(@PathVariable("id") Integer id) {
        CheckInfo checkInfo = checkInfoService.getById(id);
        if (checkInfo != null) {
            return ResponseEntity.ok(checkInfo);
        }
        throw new MyException(ExceptionEnums.GET_ITEM);
    }

    @ApiOperation(value = "基础接口: 修改数据")
    @PostMapping(value = "update")
    @RequiresPermissions("checkInfo:update")
    public ResponseEntity<CheckInfo> update(@RequestBody CheckInfo checkInfo) {
        checkInfo.setCreateDatetime(new Date());
        checkInfo.setUpdateDatetime(new Date());
        boolean b = checkInfoService.updateById(checkInfo);
        if (b) {
            return ResponseEntity.ok(checkInfo);
        }
        throw new MyException(ExceptionEnums.UPDATE_ERROR);
    }

    @ApiOperation(value = "基础接口: 删除指定ID的数据")
    @GetMapping(value = "delete/{id}")
    @RequiresPermissions("checkInfo:delete")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) {
        boolean b = checkInfoService.removeById(id);
        if (b) {
            return ResponseEntity.ok("删除成功");
        }
        throw new MyException(ExceptionEnums.DELETE_ERROR);
    }
}

