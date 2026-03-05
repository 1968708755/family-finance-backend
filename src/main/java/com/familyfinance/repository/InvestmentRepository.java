package com.familyfinance.repository;

import com.familyfinance.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUserId(Long userId);
    
    List<Investment> findByProjectName(String projectName);
    
    List<Investment> findByUserIdAndProjectName(Long userId, String projectName);
}