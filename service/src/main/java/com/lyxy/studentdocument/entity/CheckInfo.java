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
@ApiModel(value = "CheckInfo对象", description = "")
public class CheckInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDatetime;

    @ApiModelProperty(value = "四肢")
    private String allLegs;

    @ApiModelProperty(value = "关节")
    private String arthrosis;

    private String assayCheck;

    private Date birth;

    private Integer bloodPressure;

    private String checkResult;

    private Date checkTime;

    private String chestCheck;

    private String college;

    @ApiModelProperty(value = "彩色图案及编码")
    private String colorOrCode;

    private Double correctLeftView;

    private Double correctRightView;

    private String cultureLevel;

    @ApiModelProperty(value = "耳疾")
    private String earSick;

    @ApiModelProperty(value = "平足")
    private String foot;

    @ApiModelProperty(value = "甲状腺")
    private String glandulaThyroidea;

    private String grade;

    @ApiModelProperty(value = "发育营养")
    private String growth;

    @ApiModelProperty(value = "心脏及血管")
    private String heart;

    private Double height;

    @ApiModelProperty(value = "既往医史")
    private String historyCheck;

    @ApiModelProperty(value = "缺齿")
    private String hypodontia;

    private Double leftAudition;

    private Double leftView;

    @ApiModelProperty(value = "肝")
    private String liver;

    @ApiModelProperty(value = "肺及呼吸道")
    private String lung;

    @ApiModelProperty(value = "淋巴")
    private String lymph;

    private String major;

    @ApiModelProperty(value = "神经及精神")
    private String mind;

    private String name;

    private String nation;

    private String nativePlace;

    private String occupation;

    private String otherCheck;

    @ApiModelProperty(value = "其他眼疾")
    private String otherEyesSick;

    @ApiModelProperty(value = "其他内科疾病")
    private String otherInternalSick;

    @ApiModelProperty(value = "其他五官疾病")
    private String otherSenseSick;

    @ApiModelProperty(value = "其他外科疾病")
    private String otherSurgerySick;

    @ApiModelProperty(value = "鼻窦")
    private String paranasalSinus;

    private String photo;

    private String presentAddress;

    private Integer pulse;

    private Integer realAge;

    private String remark;

    private Double rightAudition;

    private Double rightView;

    private String sealer;

    private String sex;

    private String signForAssay;

    private String signForChest;

    private String signForEar;

    private String signForEyes;

    private String signForInternal;

    private String signForMouth;

    private String signForResult;

    private String signForSurgery;

    @ApiModelProperty(value = "单色识别")
    private String singleColorJudge;

    @ApiModelProperty(value = "皮肤")
    private String skin;

    @ApiModelProperty(value = "嗅觉")
    private String smell;

    @ApiModelProperty(value = "脊柱")
    private String spineBackbone;

    @ApiModelProperty(value = "脾")
    private String spleen;

    @ApiModelProperty(value = "口吃")
    private String stammer;

    private String stuNo;

    private String suggestForCheck;

    @ApiModelProperty(value = "咽喉")
    private String throat;

    @ApiModelProperty(value = "齿槽")
    private String toothPlace;

    @ApiModelProperty(value = "龋齿")
    private String toothSick;

    private Integer userId;

    private Double waistline;

    private Double weight;

    private String workPlace;

    private String checkYear;


}
