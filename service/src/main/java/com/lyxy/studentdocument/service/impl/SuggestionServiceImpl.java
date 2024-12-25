package com.lyxy.studentdocument.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.Suggestion;
import com.lyxy.studentdocument.dao.SuggestionMapper;
import com.lyxy.studentdocument.service.SuggestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyxy.studentdocument.utils.DTO.InfoDTO;
import com.lyxy.studentdocument.utils.DTO.InfoReadDTO;
import com.lyxy.studentdocument.utils.Vo.SuggestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Slf4j
@Service
public class SuggestionServiceImpl extends ServiceImpl<SuggestionMapper, Suggestion> implements SuggestionService {
    @Resource
    private SuggestionMapper suggestionMapper;

    @Resource
    private UserServiceImpl userService;

    @Override
    public Page<SuggestionVo> selectPage(InfoDTO infoDTO) {
        Page<Suggestion> suggestionPage = new Page<>(infoDTO.getPage(), infoDTO.getRows());
        LambdaQueryWrapper<Suggestion> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        String content = infoDTO.getContent();
        Integer isRead = infoDTO.getIsRead();
        Integer doctorId = infoDTO.getDoctorId();
        Integer userId = infoDTO.getUserId();
        String office = infoDTO.getOffice();
        if (office == null) {
            userLambdaQueryWrapper.eq(null != isRead, Suggestion::getIsRead, isRead)
                    .eq(null != doctorId, Suggestion::getDoctorId, doctorId)
                    .eq(null != userId, Suggestion::getUserId, userId)
                    .eq(null != office, Suggestion::getOffice, office)
                    .like(null != content, Suggestion::getContent, content);
        } else {
            userLambdaQueryWrapper.eq(null != isRead, Suggestion::getIsRead, isRead)
                    .eq(null != doctorId, Suggestion::getDoctorId, doctorId)
                    .eq(null != userId, Suggestion::getUserId, userId)
                    .eq(!"".equals(office), Suggestion::getOffice, office)
                    .like(null != content, Suggestion::getContent, content);
        }
        userLambdaQueryWrapper.orderByDesc(Suggestion::getUpdateDatetime);
        //log.info("简介{},是否阅读{}", content, isRead);
        Page<Suggestion> page = suggestionMapper.selectPage(suggestionPage, userLambdaQueryWrapper);
        List<Suggestion> records = page.getRecords();

        Page<SuggestionVo> suggestionVoPage = new Page<>();
        BeanUtils.copyProperties(page,suggestionVoPage);

        List<SuggestionVo> suggestionVos = copyList(records);

        suggestionVoPage.setRecords(suggestionVos);

        System.out.println("这就是返回界面的数据==="+suggestionVos);
        return suggestionVoPage;
    }

    private List<SuggestionVo> copyList(List<Suggestion> records) {
        List<SuggestionVo> suggestionVos = new ArrayList<>();
        for (Suggestion record : records) {
            suggestionVos.add(copy(record));
        }
        return suggestionVos;
    }

    private SuggestionVo copy(Suggestion record) {
        SuggestionVo suggestionVo = new SuggestionVo();
        BeanUtils.copyProperties(record,suggestionVo);
        String name = userService.selectByKey(record.getUserId()).getName();
        String doctorName = userService.selectByKey(record.getDoctorId()).getName();
        suggestionVo.setUsername(name);
        suggestionVo.setDoctorName(doctorName);
        return suggestionVo;
    }

    @Override
    public boolean markToRead(InfoReadDTO infoReadDTO) {
        Integer isRead = infoReadDTO.getIsRead();
        List<Integer> collect = Arrays.stream(infoReadDTO.getIds()).collect(Collectors.toList());

        List<Suggestion> suggestions = suggestionMapper.selectBatchIds(collect);
        for (Suggestion suggestion : suggestions) {
            suggestion.setIsRead(isRead);
            suggestionMapper.updateById(suggestion);
        }

        return true;
    }
}
