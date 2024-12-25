package com.lyxy.studentdocument.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyxy.studentdocument.entity.CheckInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyxy.studentdocument.utils.DTO.Condition;
import org.springframework.http.ResponseEntity;

import java.awt.print.Book;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 */
public interface CheckInfoService extends IService<CheckInfo> {

    ResponseEntity<CheckInfo> getCurrentCheckInfo(Integer userId);

    List<CheckInfo> getDataAnalysis(Integer userId);

    Page<CheckInfo> selectPage(Condition condition);

    boolean CheckIsExist(Integer userId, String checkYear);
}
