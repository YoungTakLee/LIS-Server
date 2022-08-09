package com.green.controller;

import com.green.dto.BannerDto;
import com.green.dto.ResponseDto;
import com.green.enums.ResponseStatus;
import com.green.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/banners")
public class BannerController {
    private final BannerService bannerService;

    @GetMapping("/{banner_idx}")
    public ResponseEntity<?> getBanners(@PathVariable(value = "banner_idx") Long bannerIdx) {
        return new ResponseEntity<>(bannerService.getBanner(bannerIdx), HttpStatus.OK);
    }


    @DeleteMapping("/{banner_idx}")
    public ResponseEntity<?> deleteBanner(@PathVariable(value = "banner_idx") Long bannerIdx) {
        return new ResponseEntity<>(bannerService.deleteBanner(bannerIdx), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> getBanners(@RequestParam(value = "per_page", defaultValue = "10") int perPage
            , @RequestParam(value = "cur_page", defaultValue = "0") int currenPage
            , @RequestParam(value = "banner_start_date", required = false) String startDate
            , @RequestParam(value = "banner_end_date", required = false) String endDate
            , @RequestParam(value = "client_type", defaultValue = "cms") String clientType) {
        ResponseDto responseDto = new ResponseDto();

        if (!clientType.equalsIgnoreCase("cms") && !clientType.equalsIgnoreCase("lis")) {
            responseDto.setStatus(com.green.enums.ResponseStatus.PARAMETER_ERROR);
        } else {
            try {
                responseDto = bannerService.getBanners(perPage, currenPage, startDate, endDate, clientType);
            } catch (Exception e) {
                log.error("Error occur from getBanner : {}", e.getMessage());
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody BannerDto banner) {
        ResponseDto<BannerDto> responseDto;
        try {
            responseDto = bannerService.insertBanner(banner);
        } catch (Exception e) {
            log.error("Error occur from insertBanner: {}", e.getMessage());
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{banner_idx}")
    public ResponseEntity<?> modify(@PathVariable(value = "banner_idx") Long bannerIdx, @RequestBody BannerDto banner) {
        ResponseDto<BannerDto> responseDto;
        try {
            banner.setBannerIdx(bannerIdx);
            responseDto = bannerService.modifyBanner(banner);
        } catch (Exception e) {
            log.error("Error occur from modifyBanner: {}", e.getMessage());
            responseDto = new ResponseDto<>(ResponseStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
