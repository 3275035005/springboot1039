package com.lyxy.studentdocument.utils.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AnalysisData {

    List<String> label;

    List<Double> height;

    List<Double> weight;

}
