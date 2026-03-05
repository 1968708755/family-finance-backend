package com.familyfinance.service.impl;

import com.familyfinance.entity.Expense;
import com.familyfinance.repository.ExpenseRepository;
import com.familyfinance.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    @Override
    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }
    
    @Override
    public List<Expense> getExpensesByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
    
    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    
    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
    
    @Override
    public BigDecimal getTotalExpenseByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = expenseRepository.sumByUserIdAndDateBetween(userId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    public Map<String, BigDecimal> getExpenseByType(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = expenseRepository.groupByType(userId, startDate, endDate);
        Map<String, BigDecimal> expenseByType = new HashMap<>();
        
        for (Object[] result : results) {
            String type = (String) result[0];
            BigDecimal amount = (BigDecimal) result[1];
            expenseByType.put(type, amount);
        }
        
        return expenseByType;
    }
}