package com.green.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
public class CenterDto {

    @Autowired
    public CenterDto(int seq, String title,String password) {
        this.seq = seq;
        this.title = title;
        this.password = password;
    }

    private int seq;
    private String title;
    private String password;
}
