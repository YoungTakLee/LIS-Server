package com.green.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.dto.CenterDto;
import com.green.dto.LectureDetailDto;
import com.green.dto.ResponseDto;
import com.green.entity.CenterEntity;
import com.green.entity.LectureDetailEntity;
import com.green.enums.ResponseStatus;
import com.green.repository.CenterRepository;
import com.green.repository.LectureDetailRepository;
import com.green.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    private final CenterRepository centerRepository;
    private final LectureDetailRepository lectureDetailRepository;

    @Override
    public ResponseDto<CenterDto> insertLectureType(CenterDto centerDto) {
        ResponseDto responseDto = new ResponseDto();
        CenterEntity savedEntity = centerRepository.save(CenterEntity.builder().title(centerDto.getTitle()).password(centerDto.getPassword()).build());
        centerDto.setSeq(savedEntity.getCenterSeq());
        responseDto.setResponseBody(centerDto);
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }

    @Override
    public ResponseDto<CenterDto> updateLectureType(CenterDto centerDto) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CenterEntity> typeEntity = centerRepository.findById(centerDto.getSeq());
        if (typeEntity.isPresent()) {
            CenterEntity build = CenterEntity.builder()
                    .centerSeq(typeEntity.get().getCenterSeq())
                    .title(centerDto.getTitle())
                    .password(centerDto.getPassword())
                    .build();
            centerRepository.save(build);
            responseDto.setStatus(ResponseStatus.SUCCESS);
            responseDto.setResponseBody(centerDto);
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<?> deleteLectureType(int typeSeq, String password) {
        ResponseDto responseDto = new ResponseDto<>();
        Optional<CenterEntity> lectureType = centerRepository.findById(typeSeq);
        if (lectureType.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                Map<String,String> map = objectMapper.readValue(password, Map.class);
                password = map.getOrDefault("password",null);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            if(lectureType.get().getPassword().equalsIgnoreCase(password)) {
                centerRepository.delete(lectureType.get());
                responseDto.setStatus(ResponseStatus.SUCCESS);
                responseDto.setResponseBody(centerRepository
                        .findAll()
                        .stream()
                        .map(entity -> new CenterDto(entity.getCenterSeq(), entity.getTitle(), entity.getPassword()))
                    .collect(Collectors.toList()));
            } else {
                responseDto.setStatus(ResponseStatus.CHECK_THE_CENTER_PASSWORD);
            }
        } else {
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<List<CenterDto>> getAllLectureTypeList() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setStatus(ResponseStatus.SUCCESS);
        responseDto.setResponseBody(centerRepository
                .findAll()
                .stream()
                .map(entity -> new CenterDto(entity.getCenterSeq(), entity.getTitle(), entity.getPassword()))
                .collect(Collectors.toList()));
        return responseDto;
    }

    @Override
    public ResponseDto<LectureDetailDto> insertLectureDetail(LectureDetailDto lectureDetailDto) {
        ResponseDto responseDto = new ResponseDto();
        Optional<CenterEntity> typeRepositoryById = centerRepository.findByCenterSeqAndPassword(lectureDetailDto.getCenterSeq(), lectureDetailDto.getCenterPassword());
        if (typeRepositoryById.isPresent()) {
            LectureDetailEntity savedEntity = lectureDetailRepository.save(LectureDetailEntity.builder()
                    .centerEntity(typeRepositoryById.get())
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
            responseDto.setStatus(ResponseStatus.CHECK_THE_CENTER_PASSWORD);
        }
        return responseDto;
    }

    @Override
    public ResponseDto<List<LectureDetailDto>> getLectures(int typeSeq) {
        ResponseDto<List<LectureDetailDto>> responseDto = new ResponseDto<>();
        Optional<CenterEntity> centerEntity = centerRepository.findById(typeSeq);
        if (centerEntity.isPresent()) {
            List<LectureDetailDto> collect = lectureDetailRepository.findAllByCenterEntity(centerEntity.get()).stream()
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
        Optional<CenterEntity> CenterEntity = centerRepository.findById(typeSeq);
        if (CenterEntity.isPresent()) {
            Optional<LectureDetailEntity> optional = lectureDetailRepository.findByCenterEntityAndLectureDetailSeq(CenterEntity.get(), detailSeq);
            if (optional.isPresent()) {
                LectureDetailEntity lectureDetailEntity = optional.get();
                LectureDetailDto lectureDetailDto = LectureDetailDto.builder()
                        .seq(lectureDetailEntity.getLectureDetailSeq())
                        .centerSeq(lectureDetailEntity.getCenterEntity().getCenterSeq())
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
        Optional<CenterEntity> centerEntity = centerRepository.findById(typeSeq);
        if (centerEntity.isPresent()) {
            Optional<LectureDetailEntity> optional = lectureDetailRepository.findByCenterEntityAndLectureDetailSeq(centerEntity.get(), detailSeq);
            if (optional.isPresent()) {
                lectureDetailRepository.deleteById(optional.get().getLectureDetailSeq());
                responseDto.setStatus(ResponseStatus.SUCCESS);
                List<LectureDetailEntity> allByLectureType = lectureDetailRepository.findAllByCenterEntity(centerEntity.get());
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
            Optional<CenterEntity> findType = centerRepository.findByCenterSeqAndPassword(lectureDetailDto.getCenterSeq(), lectureDetailDto.getCenterPassword());
            if (findType.isPresent()) {
                lectureDetailRepository.save(LectureDetailEntity.builder()
                        .lectureDetailSeq(lectureDetailDto.getSeq())
                        .centerEntity(findType.get())
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
                log.debug("There is no LectureType, lectureDetailDto.getLectureTypeSeq : {}, lectureDetailDto.getCenterPassword(): {}", lectureDetailDto.getCenterSeq(), lectureDetailDto.getCenterPassword());
                responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
            }
        } else {
            log.debug("There is no LectureDetail, lectureDetailDto.getSeq : {}", lectureDetailDto.getSeq());
            responseDto.setStatus(ResponseStatus.DATA_NOT_FOUND);
        }
        return responseDto;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ResponseDto<?> saveExcelFile(MultipartFile file, CenterDto centerDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        File excelFile = convert(file);
        FileInputStream xlsxFile = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(xlsxFile);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowItr = sheet.iterator();
        List<LectureDetailDto> lectureList = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();
        while (rowItr.hasNext()) {
            Row row = rowItr.next();
            if (row.getRowNum() == 0) {
                continue;
            }
            Iterator<Cell> cellItr = row.cellIterator();
            LectureDetailDto lectureDetailDto = new LectureDetailDto();
            while (cellItr.hasNext()) {
                Cell cell = cellItr.next();
                int index = cell.getColumnIndex();
                switch (index) {
                    case 0:
                        lectureDetailDto.setMethod(formatter.formatCellValue(cell)); //수업방법
                        break;
                    case 1:
                        lectureDetailDto.setTitle(formatter.formatCellValue(cell)); //프로그램명
                        break;
                    case 2:
                        lectureDetailDto.setInstructor(formatter.formatCellValue(cell)); //강사명
                        break;
                    case 3:
                        lectureDetailDto.setRecruitmentCount(formatter.formatCellValue(cell)); //모집인원
                        break;
                    case 4:
                        lectureDetailDto.setRoomNo(formatter.formatCellValue(cell)); //강의실
                        break;
                    case 5:
                        lectureDetailDto.setWeekDay(formatter.formatCellValue(cell)); //요일
                        break;
                    case 6:
                        lectureDetailDto.setLectureTime(formatter.formatCellValue(cell)); //강의시간
                        break;
                    case 7:
                        lectureDetailDto.setDesc(formatter.formatCellValue(cell)); //강좌소개
                        break;
                    case 8:
                        lectureDetailDto.setMaterialCosts(formatter.formatCellValue(cell)); //재료비
                        break;
                    default:
                        break;
                }
            }
            if(StringUtils.hasText(lectureDetailDto.getTitle())) {
                lectureList.add(lectureDetailDto);
            }
        }
        workbook.close();

        Optional<CenterEntity> find = centerRepository.findByTitleAndPassword(centerDto.getTitle(), centerDto.getPassword());

        if (find.isPresent()) {
            lectureDetailRepository.deleteByCenterEntity(find.get());
            lectureList.forEach(lectureDetailDto -> {
                lectureDetailRepository.save(LectureDetailEntity.builder()
                        .centerEntity(find.get())
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
            responseDto.setResponseBody(ResponseStatus.SUCCESS);
        } else {
            responseDto.setResponseBody(ResponseStatus.CHECK_THE_CENTER_PASSWORD);
        }
        return responseDto;
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
