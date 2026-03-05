package com.familyfinance.service.impl;

import com.familyfinance.entity.Income;
import com.familyfinance.repository.IncomeRepository;
import com.familyfinance.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IncomeServiceImpl implements IncomeService {
    
    @Autowired
    private IncomeRepository incomeRepository;
    
    @Override
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }
    
    @Override
    public List<Income> getIncomesByUserId(Long userId) {
        return incomeRepository.findByUserId(userId);
    }
    
    @Override
    public List<Income> getIncomesByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return incomeRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
    
    @Override
    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }
    
    @Override
    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }
    
    @Override
    public BigDecimal getTotalIncomeByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = incomeRepository.sumByUserIdAndDateBetween(userId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    public Map<String, BigDecimal> getIncomeByType(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = incomeRepository.groupByType(userId, startDate, endDate);
        Map<String, BigDecimal> incomeByType = new HashMap<>();
        
        for (Object[] result : results) {
            String type = (String) result[0];
            BigDecimal amount = (BigDecimal) result[1];
            incomeByType.put(type, amount);
        }
        
        return incomeByType;
    }
}