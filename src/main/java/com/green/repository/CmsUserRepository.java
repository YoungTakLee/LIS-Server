package com.green.repository;

import com.green.entity.CmsUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CmsUserRepository extends JpaRepository<CmsUserEntity,Long> {

    @Modifying
    @Transactional
    @Query(value = "BACKUP TO CONCAT(FORMATDATETIME(now(),'yyyy_MM_dd_HH'),'_backup.zip')", nativeQuery = true)
    int backUpQuery();
    CmsUserEntity findByLoginId(String userId);
}
