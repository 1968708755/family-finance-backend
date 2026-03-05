package com.familyfinance.repository;

import com.familyfinance.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    
    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserIdAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT e.type, SUM(e.amount) FROM Expense e WHERE e.userId = :userId AND e.date BETWEEN :startDate AND :endDate GROUP BY e.type")
    List<Object[]> groupByType(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}