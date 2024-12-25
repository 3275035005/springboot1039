package com.lyxy.studentdocument.dao;

import com.lyxy.studentdocument.entity.CheckInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface CheckInfoMapper extends BaseMapper<CheckInfo> {
    List<CheckInfo> getDataAnalysis(@Param("userId") Integer userId);

    CheckInfo getCurrentCheckInfo(@Param("userId") Integer userId);
}
