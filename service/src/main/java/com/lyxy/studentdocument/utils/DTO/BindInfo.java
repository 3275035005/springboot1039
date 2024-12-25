package com.lyxy.studentdocument.utils.DTO;

import lombok.Data;

import java.util.List;


@Data
public class BindInfo {

    private Integer resourceId;

    private List<Integer> roleList;

}
