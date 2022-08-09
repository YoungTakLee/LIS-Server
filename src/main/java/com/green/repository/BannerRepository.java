package com.green.repository;

import com.green.entity.BannerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
    Page<BannerEntity> findAllByBannerStartDateLessThanEqualAndAndBannerEndDateGreaterThanEqual(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
