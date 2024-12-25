package com.lyxy.studentdocument.utils.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * 建议签证官
 *
 * @author 小章肝java
 * @date 2022/10/15 10:20
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SuggestionVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDatetime;

    private Integer checkInfoId;

    private String content;

    private Integer doctorId;

    private Integer isRead;

    private String office;

    private Integer userId;

    private String username;

    private String doctorName;
}
