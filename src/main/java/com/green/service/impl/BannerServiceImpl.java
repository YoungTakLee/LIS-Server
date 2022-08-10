package com.green.service.impl;

import com.green.dto.BannerDto;
import com.green.dto.BannerFileDto;
import com.green.dto.BannerListDto;
import com.green.dto.ResponseDto;
import com.green.entity.BannerEntity;
import com.green.entity.BannerFileEntity;
import com.green.enums.BannerType;
import com.green.enums.ResponseStatus;
import com.green.repository.BannerFileRepository;
import com.green.repository.BannerRepository;
import com.green.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl implements BannerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private BannerRepository bannerRepository;
    private BannerFileRepository bannerFileRepository;
    //private final Path rootLocationBanner;
    @Value("${server.port:#{null}}")
    private String port;

    BannerServiceImpl(BannerRepository bannerRepository, BannerFileRepository bannerFileRepository) {
        this.bannerRepository = bannerRepository;
        this.bannerFileRepository = bannerFileRepository;
        //this.rootLocationBanner = Paths.get(properties.getLocationBanner());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseDto insertBanner(BannerDto banner) {
        ResponseDto responseDto = new ResponseDto<>();
        BannerEntity bannerEntity = BannerEntity.builder()
                .title(banner.getTitle())
                .bannerType(banner.getBannerType())
                .bannerStartDate(LocalDate.parse(banner.getBannerStartDate()))
                .bannerEndDate(LocalDate.parse(banner.getBannerEndDate()))
                .build();

        for (BannerFileDto bannerFileDto : banner.getFileInfo()) {
            BannerFileEntity bannerFileEntity = BannerFileEntity.builder()
                    .filePath("/files/banner/")
                    .originalFileName(bannerFileDto.getOriginalFileName())
                    .savedFileName(bannerFileDto.getSavedFileName())
                    .fileSeq(Integer.parseInt(bannerFileDto.getFileSeq()))
                    .build();
            bannerFileEntity.insertBannerEntity(bannerEntity);
            bannerEntity.getBannerFileEntity().add(bannerFileEntity);
        }

        bannerRepository.save(bannerEntity);

        if (bannerEntity.getId() > 0) {
            BannerDto returnBannerDto = BannerDto.builder()
                    .bannerIdx(bannerEntity.getId())
                    .title(banner.getTitle())
                    .bannerType(banner.getBannerType())
                    .bannerStartDate(banner.getBannerStartDate())
                    .bannerEndDate(banner.getBannerEndDate())
                    .fileCnt(String.valueOf(bannerEntity.getBannerFileEntity().size())).build();

            returnBannerDto.setFileInfo(
                    bannerEntity.getBannerFileEntity().stream().map(bannerFileEntity -> {
                        BannerFileDto bannerFileDto = BannerFileDto.builder()
                                .bannerFileIdx(String.valueOf(bannerFileEntity.getId()))
                                .bannerIdx(String.valueOf(bannerEntity.getId()))
                                .filePath(bannerFileEntity.getFilePath())
                                .originalFileName(bannerFileEntity.getOriginalFileName())
                                .savedFileName(bannerFileEntity.getSavedFileName())
                                .fileSeq(String.valueOf(bannerFileEntity.getFileSeq()))
                                .regDate(String.valueOf(bannerFileEntity.getRegDate()))
                                .build();
                        return bannerFileDto;
                    }).collect(Collectors.toList()));
            responseDto.setResponseBody(returnBannerDto);
            responseDto.setStatus(ResponseStatus.SUCCESS);
        } else {
            responseDto.setStatus(ResponseStatus.INSERT_FAILED);
        }

        return responseDto;
    }


    @Override
    public ResponseDto<BannerDto> getBanner(Long bannerIdx) {
        ResponseDto<BannerDto> responseDto = new ResponseDto();

        Optional<BannerEntity> optionalBanner = bannerRepository.findById(bannerIdx);

        if (optionalBanner.isPresent()) {
            BannerEntity bannerEntity = optionalBanner.get();
            BannerDto bannerDto = getBannerDto(bannerEntity);

            responseDto.setStatus(ResponseStatus.SUCCESS);
            responseDto.setResponseBody(bannerDto);
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
            responseDto.setResponseBody(null);
        }
        return responseDto;
    }

    private BannerDto getBannerDto(BannerEntity bannerEntity) {
        BannerDto bannerDto = BannerDto.builder()
                .bannerIdx(bannerEntity.getId())
                .bannerStartDate(bannerEntity.getBannerStartDate().toString())
                .bannerEndDate(bannerEntity.getBannerEndDate().toString())
                .fileCnt(String.valueOf(bannerEntity.getBannerFileEntity().size()))
                .title(bannerEntity.getTitle())
                .fileInfo(new ArrayList<BannerFileDto>())
                .build();
        for (BannerFileEntity bannerFileEntity : bannerEntity.getBannerFileEntity()) {
            bannerDto.getFileInfo().add(BannerFileDto.builder()
                    .bannerIdx(String.valueOf(bannerEntity.getId()))
                    .bannerFileIdx(String.valueOf(bannerFileEntity.getId()))
                    .filePath(bannerFileEntity.getFilePath())
                    .originalFileName(bannerFileEntity.getOriginalFileName())
                    .savedFileName(bannerFileEntity.getSavedFileName())
                    .fileSeq(String.valueOf(bannerFileEntity.getFileSeq()))
                    .regDate(bannerFileEntity.getRegDate().toString())
                    .build());
        }
        return bannerDto;
    }


    @Override
    public ResponseDto getBanners(int perPage, int currenPage, BannerType bannerType, String startDate, String endDate, String clientType) {
        logger.debug("{},{},{},{},{},{}", perPage, currenPage, bannerType, startDate, endDate, clientType);
        ResponseDto<BannerListDto> responseDto = new ResponseDto();
        BannerListDto bannerListDto = new BannerListDto();
        Page<BannerEntity> bannerEntities;
        Pageable pageWithTenElements = PageRequest.of(currenPage - 1, perPage, Sort.by("id").descending());

        if (clientType.equalsIgnoreCase("cms")) {
            if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
                bannerEntities = bannerRepository.findAllByBannerStartDateLessThanEqualAndAndBannerEndDateGreaterThanEqualAndBannerType(LocalDate.parse(startDate), LocalDate.parse(endDate), pageWithTenElements, bannerType);
            } else {
                bannerEntities = bannerRepository.findAll(pageWithTenElements);
            }
        } else {
            bannerEntities = bannerRepository.findAllByBannerStartDateLessThanEqualAndAndBannerEndDateGreaterThanEqualAndBannerType(LocalDate.now(), LocalDate.now(), pageWithTenElements, bannerType);
        }

        bannerEntities.forEach(bannerEntity -> {
            bannerListDto.getBannerDtoList().add(getBannerDto(bannerEntity));
        });
        bannerListDto.setCurrentPage(currenPage);
        bannerListDto.setTotalCount(bannerEntities.getTotalElements());
        bannerListDto.setTotalPage(bannerEntities.getTotalPages());

        responseDto.setStatus(ResponseStatus.SUCCESS);
        responseDto.setResponseBody(bannerListDto);

        return responseDto;
    }


    @Override
    public ResponseDto<BannerDto> deleteBanner(Long bannerIdx) {
        ResponseDto responseDto = new ResponseDto();
        try {
            bannerRepository.deleteById(bannerIdx);
            responseDto.setStatus(ResponseStatus.SUCCESS);
        } catch (EmptyResultDataAccessException e) {
            responseDto.setStatus(ResponseStatus.ALREADY_DELETED);
        }
        return responseDto;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseDto<BannerDto> modifyBanner(BannerDto banner) {
        ResponseDto responseDto = new ResponseDto();

        Optional<BannerEntity> optionalBannerEntity = bannerRepository.findById(banner.getBannerIdx());
        if (optionalBannerEntity.isPresent()) {
            try {
                BannerEntity bannerEntity = optionalBannerEntity.get();
                bannerFileRepository.deleteAllByBannerEntity(bannerEntity);
                BannerEntity build = BannerEntity.builder()
                        .id(bannerEntity.getId())
                        .bannerStartDate(LocalDate.parse(banner.getBannerStartDate()))
                        .bannerEndDate(LocalDate.parse(banner.getBannerEndDate()))
                        .title(banner.getTitle())
                        .regDate(bannerEntity.getRegDate())
                        .updateDate(LocalDateTime.now()).build();
                if (banner.getFileInfo().size() > 0) {
                    banner.getFileInfo().stream().forEach(bannerFileDto -> {
                        BannerFileEntity bannerFile = BannerFileEntity.builder()
                                .filePath("/files/banner/")
                                .originalFileName(bannerFileDto.getOriginalFileName())
                                .savedFileName(bannerFileDto.getSavedFileName())
                                .fileSeq(Integer.parseInt(bannerFileDto.getFileSeq()))
                                .build();
                        bannerFile.insertBannerEntity(build);
                        build.getBannerFileEntity().add(bannerFile);
                    });
                }
                bannerRepository.save(build);

                responseDto.setStatus(ResponseStatus.SUCCESS);
                responseDto.setResponseBody(getBannerDto(bannerEntity));

            } catch (EmptyResultDataAccessException e) {
                responseDto.setStatus(ResponseStatus.UPDATE_FAILED);
            }
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

}
