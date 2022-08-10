package com.green;

import com.green.entity.LectureDetailEntity;
import com.green.entity.LectureTypeEntity;
import com.green.repository.LectureDetailRepository;
import com.green.repository.LectureTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private  LectureDetailRepository lectureDetailRepository;
    @Autowired
    private  LectureTypeRepository lectureTypeRepository;

    @Test
    public void 테스트() {
        LectureTypeEntity lectureTypeEntity = LectureTypeEntity.builder().title("직업능력교육").build();
        lectureTypeRepository.save(lectureTypeEntity);

        LectureDetailEntity lectureDetailEntity = LectureDetailEntity.builder()
                .lectureType(lectureTypeEntity)
                .title("양식조리기능사 자격증")
                .method("대면")
                .instructor("서수미")
                .recruitmentCount(16)
                .roomNo("210")
                .weekDay("수,목")
                .lectureTime("13:30 ~ 15:30")
                .desc("양식조리사 자격증 취득과정")
                .build();
        lectureDetailRepository.save(lectureDetailEntity);

        List<LectureDetailEntity> all = lectureDetailRepository.findAll();

        for (LectureDetailEntity entity : all) {
            System.out.println("entity = " + entity);
        }


    }
}
