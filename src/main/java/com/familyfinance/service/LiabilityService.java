package com.familyfinance.service;

import com.familyfinance.entity.Liability;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LiabilityService {
    List<Liability> getAllLiabilities();
    List<Liability> getLiabilitiesByUserId(Long userId);
    List<Liability> getLiabilitiesByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Liability saveLiability(Liability liability);
    void deleteLiability(Long id);
    BigDecimal getTotalLiabilityByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Map<String, BigDecimal> getLiabilityByType(Long userId, LocalDate startDate, LocalDate endDate);
    Liability getLatestLiabilityByUserIdAndType(Long userId, String type);
}