package com.green.service;

import com.green.dto.CenterDto;
import com.green.dto.LectureDetailDto;
import com.green.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LectureService {
    ResponseDto<CenterDto> insertLectureType(CenterDto lectureTypeDto);

    ResponseDto<CenterDto> updateLectureType(CenterDto lectureTypeDto);

    ResponseDto<LectureDetailDto> insertLectureDetail(LectureDetailDto lectureDetailDto);

    ResponseDto<LectureDetailDto> updateLectureDetail(LectureDetailDto lectureDetailDto);
    ResponseDto<List<CenterDto>> getAllLectureTypeList();

    ResponseDto<?> deleteLectureType(int typeSeq, String password);

    ResponseDto<List<LectureDetailDto>> getLectures(int typeSeq);

    ResponseDto<LectureDetailDto> getLectureDetail(int typeSeq, int detailSeq);

    ResponseDto<?> deleteLectureDetail(int typeSeq, int detailSeq);
    ResponseDto<?> saveExcelFile(MultipartFile file,CenterDto centerDto) throws Exception;
}
