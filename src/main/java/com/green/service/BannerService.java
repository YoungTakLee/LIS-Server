package com.green.service;

import com.green.dto.BannerDto;
import com.green.dto.ResponseDto;
import com.green.enums.BannerType;

public interface BannerService {
    ResponseDto<BannerDto> insertBanner(BannerDto banner);

    ResponseDto<BannerDto> getBanner(Long bannerIdx);

    ResponseDto getBanners(int perPage, int currenPage, BannerType bannerType, String startDate, String endDate, String clientType);

    ResponseDto<BannerDto> deleteBanner(Long bannerIdx);

    ResponseDto<BannerDto> modifyBanner(BannerDto banner);

}
