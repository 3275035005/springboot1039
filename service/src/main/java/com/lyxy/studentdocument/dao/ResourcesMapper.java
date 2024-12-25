package com.lyxy.studentdocument.dao;

import com.lyxy.studentdocument.entity.Resources;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface ResourcesMapper extends BaseMapper<Resources> {

    List<Resources> findRoleResource(@Param("roleId") Integer roleId, @Param("typeId")Integer typeId);

    /**
     *  获取所有url 和 权限
     *  @return 对应所有资源
     */
    List<Resources> listUrlAndPermission();
}
