package com.green.repository;

import com.green.entity.LectureDetailEntity;
import com.green.entity.LectureTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureDetailRepository extends JpaRepository<LectureDetailEntity, Integer> {
    List<LectureDetailEntity> findAllByLectureType(LectureTypeEntity lectureTypeEntity);
    Optional<LectureDetailEntity> findByLectureTypeAndLectureDetailSeq(LectureTypeEntity lectureTypeEntity, int detailSeq);
}
