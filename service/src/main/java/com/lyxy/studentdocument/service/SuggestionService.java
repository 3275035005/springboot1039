package com.lyxy.studentdocument.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.Suggestion;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyxy.studentdocument.utils.DTO.InfoDTO;
import com.lyxy.studentdocument.utils.DTO.InfoReadDTO;
import com.lyxy.studentdocument.utils.Vo.SuggestionVo;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface SuggestionService extends IService<Suggestion> {

    Page<SuggestionVo> selectPage(InfoDTO infoDTO);

    boolean markToRead(InfoReadDTO infoReadDTO);
}
