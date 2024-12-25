package com.lyxy.studentdocument.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDatetime;

    /**
     * 出生
     */
    private Date birth;

    /**
     * 大学
     */
    private String college;

    /**
     * 年级
     */
    private String grade;

    /**
     * 主要
     */
    private String major;

    /**
     * 名字
     */
    private String name;

    private String password;

    /**
     * 角色id
     */
    private Integer roleId;

    private String sex;

    private String stuNo;

    private String username;

    /**
     * 文化水平
     */
    private String cultureLevel;

    /**
     * 国家
     */
    private String nation;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 占领
     */
    private String occupation;

    /**
     * 目前地址
     */
    private String presentAddress;

    /**
     * 工作地方
     */
    private String workPlace;

    /**
     * 照片
     */
    private String photo;

    /**
     * 真正年龄
     */
    private Integer realAge;

    /**
     * 老师id
     */
    private Integer teacherId;


}
