package com.green.controller;

import com.green.dto.CenterDto;
import com.green.dto.LectureDetailDto;
import com.green.dto.CenterDto;
import com.green.dto.ResponseDto;
import com.green.enums.ResponseStatus;
import com.green.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {
    private final LectureService lectureService;

    @PostMapping
    public ResponseEntity<?> insertLectureType(@RequestBody CenterDto centerDto) {
        ResponseDto<CenterDto> responseDto;
        try {
            responseDto = lectureService.insertLectureType(centerDto);
        } catch (Exception e) {
            log.error("Error occur from insertLectureType : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "{typeSeq}")
    public ResponseEntity<?> deleteLectureType(@PathVariable int typeSeq, @RequestBody String password) {
        ResponseDto<?> responseDto;
        try {
            responseDto = lectureService.deleteLectureType(typeSeq,password);
        } catch (Exception e) {
            log.error("Error occur from deleteLectureType : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateLectureType(@RequestBody CenterDto ce) {
        ResponseDto<CenterDto> responseDto;
        try {
            responseDto = lectureService.updateLectureType(ce);
        } catch (Exception e) {
            log.error("Error occur from updateLectureType : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getLectureTitles() {
        ResponseDto<List<CenterDto>> responseDto = new ResponseDto<>();
        try {
            responseDto = lectureService.getAllLectureTypeList();
        } catch (Exception e) {
            log.error("Error occur from getLectureTitles : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/detail")
    public ResponseEntity<?> insertLectureDetail(@RequestBody LectureDetailDto lectureDetailDto) {
        ResponseDto<LectureDetailDto> responseDto;
        try {
            responseDto = lectureService.insertLectureDetail(lectureDetailDto);
        } catch (Exception e) {
            log.error("Error occur from insertLectureDetail : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{typeSeq}")
    public ResponseEntity<?> getLectures(@PathVariable int typeSeq) {
        ResponseDto<List<LectureDetailDto>> responseDto;
        try {
            responseDto = lectureService.getLectures(typeSeq);
        } catch (Exception e) {
            log.error("Error occur from getLectureTitles : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/{typeSeq}/{detailSeq}")
    public ResponseEntity<?> getLectureDetail(@PathVariable int typeSeq, @PathVariable int detailSeq) {
        ResponseDto<LectureDetailDto> responseDto;
        try {
            responseDto = lectureService.getLectureDetail(typeSeq, detailSeq);
        } catch (Exception e) {
            log.error("Error occur from getLectureTitles : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{typeSeq}/{detailSeq}")
    public ResponseEntity<?> deleteLectureDetail(@PathVariable int typeSeq, @PathVariable int detailSeq) {
        ResponseDto<?> responseDto;
        try {
            responseDto = lectureService.deleteLectureDetail(typeSeq, detailSeq);
        } catch (Exception e) {
            log.error("Error occur from deleteLectureDetail : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping(value = "/detail")
    public ResponseEntity<?> updateLectureDetail(@RequestBody LectureDetailDto lectureDetailDto) {
        ResponseDto<LectureDetailDto> responseDto;
        try {
            responseDto = lectureService.updateLectureDetail(lectureDetailDto);
        } catch (Exception e) {
            log.error("Error occur from insertLectureDetail : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/excel")
    public ResponseEntity<?> readExcelFile(@RequestPart("file") MultipartFile file, @RequestPart("center") CenterDto centerDto) {
        try {
            return new ResponseEntity<>(lectureService.saveExcelFile(file,centerDto),HttpStatus.OK);
        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.EXCEL_INSERT_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
