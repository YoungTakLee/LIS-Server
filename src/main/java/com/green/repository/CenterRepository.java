package com.green.repository;

import com.green.entity.CenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<CenterEntity,Integer> {
    Optional<CenterEntity> findByCenterSeqAndPassword(int centerSeq,String password);
    Optional<CenterEntity> findByTitleAndPassword(String title,String password);
}
