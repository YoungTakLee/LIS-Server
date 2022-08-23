package com.green.service;

import com.green.dto.LectureDetailDto;
import com.green.dto.LectureTypeDto;
import com.green.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureService {
    ResponseDto<LectureTypeDto> insertLectureType(LectureTypeDto lectureTypeDto);

    ResponseDto<LectureTypeDto> updateLectureType(LectureTypeDto lectureTypeDto);

    ResponseDto<LectureDetailDto> insertLectureDetail(LectureDetailDto lectureDetailDto);

    ResponseDto<LectureDetailDto> updateLectureDetail(LectureDetailDto lectureDetailDto);
    ResponseDto<List<LectureTypeDto>> getAllLectureTypeList();

    ResponseDto<?> deleteLectureType(int typeSeq);

    ResponseDto<List<LectureDetailDto>> getLectures(int typeSeq);

    ResponseDto<LectureDetailDto> getLectureDetail(int typeSeq, int detailSeq);

    ResponseDto<?> deleteLectureDetail(int typeSeq, int detailSeq);
    void saveExcelFile(MultipartFile file) throws Exception;
}
