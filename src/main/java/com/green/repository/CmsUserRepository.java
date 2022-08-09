package com.green.repository;

import com.green.entity.CmsUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CmsUserRepository extends JpaRepository<CmsUserEntity,Long> {

    @Modifying
    @Transactional
    @Query(value = "BACKUP TO 'back.zip'", nativeQuery = true)
    int backUpQuery(String date);
    CmsUserEntity findByLoginId(String userId);
}
