package com.green.service.impl;

import com.green.dto.LectureDetailDto;
import com.green.dto.LectureTypeDto;
import com.green.dto.ResponseDto;
import com.green.entity.LectureDetailEntity;
import com.green.entity.LectureTypeEntity;
import com.green.enums.ResponseStatus;
import com.green.repository.LectureDetailRepository;
import com.green.repository.LectureTypeRepository;
import com.green.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureServiceImpl implements LectureService {
    private final LectureTypeRepository lectureTypeRepository;
    private final LectureDetailRepository lectureDetailRepository;

    @Override
    public ResponseDto<LectureTypeDto> insertLectureType(LectureTypeDto lectureTypeDto) {
        ResponseDto responseDto = new ResponseDto();
        LectureTypeEntity savedEntity = lectureTypeRepository.save(LectureTypeEntity.builder().title(lectureTypeDto.getTitle()).build());
        lectureTypeDto.setSeq(savedEntity.getLectureTypeSeq());
        responseDto.setResponseBody(lectureTypeDto);
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }

    @Override
    public ResponseDto<LectureTypeDto> updateLectureType(LectureTypeDto lectureTypeDto) {
        ResponseDto responseDto = new ResponseDto();
        Optional<LectureTypeEntity> typeEntity = lectureTypeRepository.findById(lectureTypeDto.getSeq());
        if (typeEntity.isPresent()) {
            LectureTypeEntity build = LectureTypeEntity.builder()
                    .lectureTypeSeq(typeEntity.get().getLectureTypeSeq())
                    .title(lectureTypeDto.getTitle())
                    .build();
            lectureTypeRepository.save(build);
            responseDto.setStatus(ResponseStatus.SUCCESS);
            responseDto.setResponseBody(lectureTypeDto);
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<?> deleteLectureType(int typeSeq) {
        ResponseDto responseDto = new ResponseDto<>();
        Optional<LectureTypeEntity> lectureType = lectureTypeRepository.findById(typeSeq);
        if (lectureType.isPresent()) {
            lectureTypeRepository.delete(lectureType.get());
            responseDto.setStatus(ResponseStatus.SUCCESS);
            responseDto.setResponseBody(lectureTypeRepository
                    .findAll()
                    .stream()
                    .map(entity -> new LectureTypeDto(entity.getLectureTypeSeq(), entity.getTitle()))
                    .collect(Collectors.toList()));
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<List<LectureTypeDto>> getAllLectureTypeList() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseStatus.SUCCESS);
        responseDto.setResponseBody(lectureTypeRepository
                .findAll()
                .stream()
                .map(entity -> new LectureTypeDto(entity.getLectureTypeSeq(), entity.getTitle()))
                .collect(Collectors.toList()));
        return responseDto;
    }

    @Override
    public ResponseDto<LectureDetailDto> insertLectureDetail(LectureDetailDto lectureDetailDto) {
        ResponseDto responseDto = new ResponseDto();
        Optional<LectureTypeEntity> typeRepositoryById = lectureTypeRepository.findById(lectureDetailDto.getLectureTypeSeq());
        if (typeRepositoryById.isPresent()) {
            LectureDetailEntity savedEntity = lectureDetailRepository.save(LectureDetailEntity.builder()
                    .lectureType(typeRepositoryById.get())
                    .title(lectureDetailDto.getTitle())
                    .method(lectureDetailDto.getMethod())
                    .instructor(lectureDetailDto.getInstructor())
                    .recruitmentCount(lectureDetailDto.getRecruitmentCount())
                    .roomNo(lectureDetailDto.getRoomNo())
                    .weekDay(lectureDetailDto.getWeekDay())
                    .lectureTime(lectureDetailDto.getLectureTime())
                    .desc(lectureDetailDto.getDesc())
                    .build());
            lectureDetailDto.setSeq(savedEntity.getLectureDetailSeq());
            responseDto.setResponseBody(lectureDetailDto);
            responseDto.setStatus(ResponseStatus.SUCCESS);
        } else {
            responseDto.setStatus(ResponseStatus.INSERT_FAILED);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<List<LectureDetailDto>> getLectures(int typeSeq) {
        ResponseDto<List<LectureDetailDto>> responseDto = new ResponseDto<>();
        Optional<LectureTypeEntity> lectureTypeEntity = lectureTypeRepository.findById(typeSeq);
        if (lectureTypeEntity.isPresent()) {
            List<LectureDetailDto> collect = lectureDetailRepository.findAllByLectureType(lectureTypeEntity.get()).stream()
                    .map(entity -> LectureDetailDto.builder()
                            .seq(entity.getLectureDetailSeq())
                            //.lectureTypeSeq(entity.getLectureType().getLectureTypeSeq())
                            .title(entity.getTitle())
                            //.method(entity.getMethod())
                            .instructor(entity.getInstructor())
                            //.recruitmentCount(entity.getRecruitmentCount())
                            //.roomNo(entity.getRoomNo())
                            //.weekDay(entity.getWeekDay())
                            //.lectureTime(entity.getLectureTime())
                            //.desc(entity.getDesc())
                            .build()
                    ).collect(Collectors.toList());
            responseDto.setStatus(ResponseStatus.SUCCESS);
            responseDto.setResponseBody(collect);
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<LectureDetailDto> getLectureDetail(int typeSeq, int detailSeq) {
        ResponseDto<LectureDetailDto> responseDto = new ResponseDto<>();
        Optional<LectureTypeEntity> lectureTypeEntity = lectureTypeRepository.findById(typeSeq);
        if (lectureTypeEntity.isPresent()) {
            Optional<LectureDetailEntity> optional = lectureDetailRepository.findByLectureTypeAndLectureDetailSeq(lectureTypeEntity.get(), detailSeq);
            if (optional.isPresent()) {
                LectureDetailEntity lectureDetailEntity = optional.get();
                LectureDetailDto lectureDetailDto = LectureDetailDto.builder()
                        .seq(lectureDetailEntity.getLectureDetailSeq())
                        .lectureTypeSeq(lectureDetailEntity.getLectureType().getLectureTypeSeq())
                        .title(lectureDetailEntity.getTitle())
                        .method(lectureDetailEntity.getMethod())
                        .instructor(lectureDetailEntity.getInstructor())
                        .recruitmentCount(lectureDetailEntity.getRecruitmentCount())
                        .roomNo(lectureDetailEntity.getRoomNo())
                        .weekDay(lectureDetailEntity.getWeekDay())
                        .lectureTime(lectureDetailEntity.getLectureTime())
                        .desc(lectureDetailEntity.getDesc())
                        .build();
                responseDto.setStatus(ResponseStatus.SUCCESS);
                responseDto.setResponseBody(lectureDetailDto);
            } else {
                responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
            }
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<?> deleteLectureDetail(int typeSeq, int detailSeq) {
        ResponseDto responseDto = new ResponseDto<>();
        Optional<LectureTypeEntity> lectureTypeEntity = lectureTypeRepository.findById(typeSeq);
        if (lectureTypeEntity.isPresent()) {
            Optional<LectureDetailEntity> optional = lectureDetailRepository.findByLectureTypeAndLectureDetailSeq(lectureTypeEntity.get(), detailSeq);
            if (optional.isPresent()) {
                lectureDetailRepository.deleteById(optional.get().getLectureDetailSeq());
                responseDto.setStatus(ResponseStatus.SUCCESS);
                List<LectureDetailEntity> allByLectureType = lectureDetailRepository.findAllByLectureType(lectureTypeEntity.get());
                responseDto.setResponseBody(allByLectureType);
            } else {
                responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
            }
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<LectureDetailDto> updateLectureDetail(LectureDetailDto lectureDetailDto) {
        ResponseDto responseDto = new ResponseDto<>();
        Optional<LectureDetailEntity> detailEntity = lectureDetailRepository.findById(lectureDetailDto.getSeq());
        if (detailEntity.isPresent()) {
            Optional<LectureTypeEntity> findType = lectureTypeRepository.findById(lectureDetailDto.getLectureTypeSeq());
            if (findType.isPresent()) {
                LectureDetailEntity updatedEntity = lectureDetailRepository.save(LectureDetailEntity.builder()
                        .lectureDetailSeq(lectureDetailDto.getSeq())
                        .lectureType(findType.get())
                        .title(lectureDetailDto.getTitle())
                        .method(lectureDetailDto.getMethod())
                        .instructor(lectureDetailDto.getInstructor())
                        .recruitmentCount(lectureDetailDto.getRecruitmentCount())
                        .roomNo(lectureDetailDto.getRoomNo())
                        .weekDay(lectureDetailDto.getWeekDay())
                        .lectureTime(lectureDetailDto.getLectureTime())
                        .desc(lectureDetailDto.getDesc())
                        .updateDate(LocalDateTime.now())
                        .build());
                responseDto.setStatus(ResponseStatus.SUCCESS);
                responseDto.setResponseBody(lectureDetailDto);
            } else {
                log.debug("There is no LectureType, lectureDetailDto.getLectureTypeSeq : {}", lectureDetailDto.getLectureTypeSeq());
                responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
            }
        } else {
            log.debug("There is no LectureDetail, lectureDetailDto.getSeq : {}", lectureDetailDto.getSeq());
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }
}
