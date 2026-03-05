package com.familyfinance.repository;

import com.familyfinance.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByUserId(Long userId);
    
    List<Income> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.userId = :userId AND i.date BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserIdAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT i.type, SUM(i.amount) FROM Income i WHERE i.userId = :userId AND i.date BETWEEN :startDate AND :endDate GROUP BY i.type")
    List<Object[]> groupByType(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}