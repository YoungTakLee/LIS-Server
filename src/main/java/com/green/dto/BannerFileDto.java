package com.green.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class BannerFileDto {
    @JsonProperty("banner_file_idx")
    private String bannerFileIdx;
    @JsonProperty("banner_idx")
    private String bannerIdx;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("original_file_name")
    private String originalFileName;
    @JsonProperty("saved_file_name")
    private String savedFileName;
    @JsonProperty("file_seq")
    private String fileSeq;
    @JsonProperty("reg_date")
    private String regDate;
}
