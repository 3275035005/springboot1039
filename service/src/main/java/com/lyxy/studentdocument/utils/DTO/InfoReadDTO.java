package com.lyxy.studentdocument.utils.DTO;

import lombok.Data;

@Data
public class InfoReadDTO {

    /**
     * 状态
     */
    Integer isRead;
    /**
     * id数组
     */
    Integer[] ids;

}
