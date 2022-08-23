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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
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
                    .materialCosts(lectureDetailDto.getMaterialCosts())
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
                        .materialCosts(lectureDetailEntity.getMaterialCosts())
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
                lectureDetailRepository.save(LectureDetailEntity.builder()
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
                        .materialCosts(lectureDetailDto.getMaterialCosts())
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


    @Override
    public void saveExcelFile(MultipartFile file) throws Exception{
            File excelFile = convert(file);
            FileInputStream xlsxFile = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(xlsxFile);

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowItr = sheet.iterator();
            Map<String, List<LectureDetailDto>> lectureMap = new HashMap<>();
            DataFormatter formatter = new DataFormatter();
            while (rowItr.hasNext()) {
                Row row = rowItr.next();
                if (row.getRowNum() == 0) {
                    continue;
                }
                Iterator<Cell> cellItr = row.cellIterator();
                LectureDetailDto lectureDetailDto = new LectureDetailDto();
                String type = null;
                while (cellItr.hasNext()) {
                    Cell cell = cellItr.next();
                    int index = cell.getColumnIndex();
                    switch (index) {
                        case 0:
                            type = cell.getStringCellValue();
                            break;
                        case 1:
                            lectureDetailDto.setMethod(formatter.formatCellValue(cell)); //수업방법
                            break;
                        case 2:
                            lectureDetailDto.setTitle(formatter.formatCellValue(cell)); //프로그램명
                            break;
                        case 3:
                            lectureDetailDto.setInstructor(formatter.formatCellValue(cell)); //강사명
                            break;
                        case 4:
                            lectureDetailDto.setRecruitmentCount(Integer.parseInt(formatter.formatCellValue(cell))); //모집인원
                            break;
                        case 5:
                            lectureDetailDto.setRoomNo(formatter.formatCellValue(cell)); //강의실
                            break;
                        case 6:
                            lectureDetailDto.setWeekDay(formatter.formatCellValue(cell)); //요일
                            break;
                        case 7:
                            lectureDetailDto.setLectureTime(formatter.formatCellValue(cell)); //강의시간
                            break;
                        case 8:
                            lectureDetailDto.setDesc(formatter.formatCellValue(cell)); //강좌소개
                            break;
                        case 9:
                            lectureDetailDto.setMaterialCosts(formatter.formatCellValue(cell)); //재료비
                            break;
                        default:
                            break;
                    }
                }
                List<LectureDetailDto> list = lectureMap.getOrDefault(type, new ArrayList<>());
                list.add(lectureDetailDto);
                lectureMap.put(type, list);
            }
            workbook.close();

            lectureDetailRepository.deleteAll();
            lectureTypeRepository.deleteAll();

            lectureMap.entrySet().stream().forEach(entry -> {
                LectureTypeEntity lectureTypeEntity = LectureTypeEntity.builder()
                        .title(entry.getKey()).build();
                LectureTypeEntity save = lectureTypeRepository.save(lectureTypeEntity);
                if(save.getLectureTypeSeq() != 0) {
                    entry.getValue().forEach(lectureDetailDto -> {
                        lectureDetailRepository.save(LectureDetailEntity.builder()
                                .lectureType(save)
                                .title(lectureDetailDto.getTitle())
                                .method(lectureDetailDto.getMethod())
                                .instructor(lectureDetailDto.getInstructor())
                                .recruitmentCount(lectureDetailDto.getRecruitmentCount())
                                .roomNo(lectureDetailDto.getRoomNo())
                                .weekDay(lectureDetailDto.getWeekDay())
                                .lectureTime(lectureDetailDto.getLectureTime())
                                .desc(lectureDetailDto.getDesc())
                                .materialCosts(lectureDetailDto.getMaterialCosts())
                                .build());
                    });
                }

            });
    }

    public File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
