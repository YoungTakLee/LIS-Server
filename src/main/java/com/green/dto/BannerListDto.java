package com.green.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BannerListDto {
    private List<BannerDto> bannerDtoList = new ArrayList<>();
    private Long totalCount;
    private int totalPage;
    private int currentPage;
}
