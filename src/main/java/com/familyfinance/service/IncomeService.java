package com.familyfinance.service;

import com.familyfinance.entity.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IncomeService {
    List<Income> getAllIncomes();
    List<Income> getIncomesByUserId(Long userId);
    List<Income> getIncomesByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Income saveIncome(Income income);
    void deleteIncome(Long id);
    BigDecimal getTotalIncomeByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Map<String, BigDecimal> getIncomeByType(Long userId, LocalDate startDate, LocalDate endDate);
}