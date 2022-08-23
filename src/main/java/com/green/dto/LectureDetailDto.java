package com.green.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String materialCosts;
}
