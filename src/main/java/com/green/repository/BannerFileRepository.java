package com.green.repository;

import com.green.entity.BannerEntity;
import com.green.entity.BannerFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BannerFileRepository extends JpaRepository<BannerFileEntity, Long> {
    void deleteAllByBannerEntity(BannerEntity bannerEntity);

    Optional<BannerFileEntity> findBannerFileEntityBySavedFileName(String savedName);
}
