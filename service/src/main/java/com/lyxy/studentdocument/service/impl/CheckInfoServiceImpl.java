package com.lyxy.studentdocument.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import com.lyxy.studentdocument.entity.CheckInfo;
import com.lyxy.studentdocument.dao.CheckInfoMapper;
import com.lyxy.studentdocument.entity.HealthDocument;
import com.lyxy.studentdocument.service.CheckInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyxy.studentdocument.utils.DTO.Clause;
import com.lyxy.studentdocument.utils.DTO.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Slf4j
@Service
public class CheckInfoServiceImpl extends ServiceImpl<CheckInfoMapper, CheckInfo> implements CheckInfoService {

    @Resource
    private CheckInfoMapper checkInfoMapper;

    @Override
    public ResponseEntity<CheckInfo> getCurrentCheckInfo(Integer userId) {
        CheckInfo checkInfo = checkInfoMapper.getCurrentCheckInfo(userId);
        //System.out.println("======================="+checkInfo);
        if (checkInfo == null) {
            throw new MyException(ExceptionEnums.NO_CHECK_INFO);
        } else {
            return ResponseEntity.ok(checkInfo);
        }
    }

    @Override
    public List<CheckInfo> getDataAnalysis(Integer userId) {
        List<CheckInfo> dataAnalysis = checkInfoMapper.getDataAnalysis(userId);
        return dataAnalysis;
    }

    @Override
    public Page<CheckInfo> selectPage(Condition condition) {
        Page<CheckInfo> wpage = new Page<>(condition.getPageNum(), condition.getPageSize());
        LambdaQueryWrapper<CheckInfo> checkInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //条件分页查询 使用queryWrapper
        //.like()
        for (Clause clause : condition.getClauses()) {
            if ("userId".equals(clause.getColumn())){
                checkInfoLambdaQueryWrapper.eq(clause.getValue() != null, CheckInfo::getUserId, clause.getValue());
            }else if ("college".equals(clause.getColumn())){
                checkInfoLambdaQueryWrapper.like(clause.getValue() != null, CheckInfo::getCollege, clause.getValue());
            } else if ("major".equals(clause.getColumn())){
                checkInfoLambdaQueryWrapper.like(clause.getValue() != null, CheckInfo::getMajor, clause.getValue());
            } else if ("name".equals(clause.getColumn())){
                checkInfoLambdaQueryWrapper.like(clause.getValue() != null, CheckInfo::getName, clause.getValue());
            }else if ("checkYear".equals(clause.getColumn())){
                checkInfoLambdaQueryWrapper.like(clause.getValue() != null, CheckInfo::getCheckYear, clause.getValue());
            }
        }
        checkInfoLambdaQueryWrapper.orderByDesc(CheckInfo::getUpdateDatetime);
        Page<CheckInfo> page = checkInfoMapper.selectPage(wpage, checkInfoLambdaQueryWrapper);
        log.info("分页数据=》》》》》》{}", page.getRecords());
        return page;
    }

    @Override
    public boolean CheckIsExist(Integer userId, String checkYear) {
        LambdaQueryWrapper<CheckInfo> checkInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        checkInfoLambdaQueryWrapper.eq(CheckInfo::getUserId, userId)
                .eq(CheckInfo::getCheckYear, checkYear);
        CheckInfo checkInfo = checkInfoMapper.selectOne(checkInfoLambdaQueryWrapper);
        //log.info("当前学生检查信息{}",checkInfo);
        //检查信息不存在时， 则可以添加
        if (checkInfo == null) {
            return true;
        }
        return false;
    }
}
