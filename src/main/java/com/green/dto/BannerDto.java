package com.green.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class BannerDto {
    @JsonProperty("banner_idx")
    private Long bannerIdx;
    private String title;
    @JsonProperty("banner_start_date")
    private String bannerStartDate;
    @JsonProperty("banner_end_date")
    private String bannerEndDate;
    @JsonProperty("file_count")
    private String fileCnt;
    @JsonProperty("file_info")
    private List<BannerFileDto> fileInfo;
}
