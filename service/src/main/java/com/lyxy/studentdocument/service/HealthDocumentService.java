package com.lyxy.studentdocument.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.HealthDocument;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyxy.studentdocument.utils.DTO.Condition;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface HealthDocumentService extends IService<HealthDocument> {

    Page<HealthDocument> selectPage(Condition condition);

    HealthDocument updateHealthDocument(HealthDocument healthDocument);
}
