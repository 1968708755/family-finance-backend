package com.familyfinance.repository;

import com.familyfinance.entity.Liability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LiabilityRepository extends JpaRepository<Liability, Long> {
    List<Liability> findByUserId(Long userId);
    
    List<Liability> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(l.amount) FROM Liability l WHERE l.userId = :userId AND l.date BETWEEN :startDate AND :endDate")
    BigDecimal sumByUserIdAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT l.type, SUM(l.amount) FROM Liability l WHERE l.userId = :userId AND l.date BETWEEN :startDate AND :endDate GROUP BY l.type")
    List<Object[]> groupByType(@Param("userId") Long userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT l FROM Liability l WHERE l.userId = :userId AND l.type = :type ORDER BY l.date DESC LIMIT 1")
    Liability findLatestByUserIdAndType(@Param("userId") Long userId, @Param("type") String type);
}