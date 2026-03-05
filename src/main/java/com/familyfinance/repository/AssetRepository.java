package com.familyfinance.repository;

import com.familyfinance.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByUserId(Long userId);
    
    List<Asset> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(a.amount) FROM Asset a WHERE a.userId = :userId AND a.date BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserIdAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT a.type, SUM(a.amount) FROM Asset a WHERE a.userId = :userId AND a.date BETWEEN :startDate AND :endDate GROUP BY a.type")
    List<Object[]> groupByType(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT a FROM Asset a WHERE a.userId = :userId AND a.type = :type ORDER BY a.date DESC LIMIT 1")
    Asset findLatestByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}