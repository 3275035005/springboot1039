package com.lyxy.studentdocument.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.HealthDocument;
import com.lyxy.studentdocument.dao.HealthDocumentMapper;
import com.lyxy.studentdocument.service.HealthDocumentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyxy.studentdocument.utils.DTO.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Slf4j
@Service
public class HealthDocumentServiceImpl extends ServiceImpl<HealthDocumentMapper, HealthDocument> implements HealthDocumentService {

    @Resource
    private HealthDocumentMapper healthDocumentMapper;

    @Override
    public Page<HealthDocument> selectPage(Condition condition) {
        Page<HealthDocument> wpage = new Page<>(condition.getPageNum(), condition.getPageSize());
        LambdaQueryWrapper<HealthDocument> checkInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();

        String content = (String) condition.getClauses().get(3).getValue();
        String author = (String) condition.getClauses().get(1).getValue();
        String book = (String) condition.getClauses().get(2).getValue();
        int isPublished = (int) condition.getClauses().get(0).getValue();

        //Page<HealthDocument> page = (Page<HealthDocument>) healthDocumentMapper.selectPageBy(wpage, content, author, book, isPublished);

        //条件分页查询 使用queryWrapper
        checkInfoLambdaQueryWrapper.like(content != null, HealthDocument::getContent, content)
                .like(author != null, HealthDocument::getAuthor, author)
                .like(book != null, HealthDocument::getBook, book)
                .eq(HealthDocument::getIsPublished, isPublished)
                .orderByDesc(HealthDocument::getPublishData);

        //checkInfoLambdaQueryWrapper.
        Page<HealthDocument> page = healthDocumentMapper.selectPage(wpage, checkInfoLambdaQueryWrapper);
        log.info("分页数据=》》》》》》{}", page.getRecords());
        return page;
    }

    @Override
    public HealthDocument updateHealthDocument(HealthDocument healthDocument) {
        healthDocument.setPublishData(new Date());
        healthDocument.setUpdateDatetime(new Date());
        int i = healthDocumentMapper.updateById(healthDocument);
        if (i > 0) {
            HealthDocument document = healthDocumentMapper.selectById(healthDocument.getId());
            return document;
        }
        return null;
    }
}
