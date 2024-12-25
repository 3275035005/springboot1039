package com.lyxy.studentdocument.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.HealthDocument;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyxy.studentdocument.utils.DTO.Clause;
import com.lyxy.studentdocument.utils.DTO.Condition;

/**
 * <p>
 *  Mapper 接口
 * </p>
 */
public interface HealthDocumentMapper extends BaseMapper<HealthDocument> {
    IPage<HealthDocument> selectPageBy(Page<HealthDocument> page,
                                       String content,
                                       String author,
                                       String book,
                                       int isPublished);
}
