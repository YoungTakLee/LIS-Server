package com.green.controller;

import com.green.dto.LectureDetailDto;
import com.green.dto.LectureTypeDto;
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
    public ResponseEntity<?> insertLectureType(@RequestBody LectureTypeDto lectureTypeDto) {
        ResponseDto<LectureTypeDto> responseDto;
        try {
            responseDto = lectureService.insertLectureType(lectureTypeDto);
        } catch (Exception e) {
            log.error("Error occur from insertLectureType : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "{typeSeq}")
    public ResponseEntity<?> deleteLectureType(@PathVariable int typeSeq) {
        ResponseDto<?> responseDto;
        try {
            responseDto = lectureService.deleteLectureType(typeSeq);
        } catch (Exception e) {
            log.error("Error occur from deleteLectureType : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateLectureType(@RequestBody LectureTypeDto lectureTypeDto) {
        ResponseDto<LectureTypeDto> responseDto;
        try {
            responseDto = lectureService.updateLectureType(lectureTypeDto);
        } catch (Exception e) {
            log.error("Error occur from updateLectureType : ", e);
            responseDto = new ResponseDto<>(com.green.enums.ResponseStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getLectureTitles() {
        ResponseDto<List<LectureTypeDto>> responseDto = new ResponseDto<>();
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
    public ResponseEntity<?> readExcelFile(@RequestPart("file") MultipartFile file) {
        try {
            lectureService.saveExcelFile(file);
            return new ResponseEntity<>(new ResponseDto<>(ResponseStatus.SUCCESS),HttpStatus.OK);
        } catch (Exception e) {
            log.error("message", e);
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
