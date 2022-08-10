package com.green.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class LectureDetailDto {
    private Integer seq;
    private Integer lectureTypeSeq;
    private String title;
    private String method;
    private String instructor;
    private Integer recruitmentCount;
    private String roomNo;
    private String weekDay;
    private String lectureTime;
    private String desc;
}
