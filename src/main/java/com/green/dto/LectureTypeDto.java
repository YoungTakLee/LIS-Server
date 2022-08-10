package com.green.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class LectureTypeDto {

    @Autowired
    public LectureTypeDto(int seq, String title) {
        this.seq = seq;
        this.title = title;
    }
    private int seq;
    private String title;
}
