package com.lyxy.studentdocument.service;

import com.lyxy.studentdocument.entity.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyxy.studentdocument.utils.Vo.ResourceVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface ResourcesService extends IService<Resources> {
    List<ResourceVO> getResourceTreeByRoleId(Integer roleId, Integer typeId);

    List<ResourceVO> getResourceTreeByUserId(Integer userId,Integer typeId);

}
