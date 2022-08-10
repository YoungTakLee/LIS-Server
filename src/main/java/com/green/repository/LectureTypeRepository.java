package com.green.repository;

import com.green.entity.LectureTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureTypeRepository extends JpaRepository<LectureTypeEntity,Integer> {
}
