package com.green.repository;

import com.green.entity.CenterEntity;
import com.green.entity.LectureDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureDetailRepository extends JpaRepository<LectureDetailEntity, Integer> {
    List<LectureDetailEntity> findAllByCenterEntity(CenterEntity centerEntity);
    Optional<LectureDetailEntity> findByCenterEntityAndLectureDetailSeq(CenterEntity centerEntity, int detailSeq);
    int deleteByCenterEntity(CenterEntity centerEntity);
}
