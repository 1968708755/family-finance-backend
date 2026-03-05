package com.familyfinance.service;

import com.familyfinance.entity.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseService {
    List<Expense> getAllExpenses();
    List<Expense> getExpensesByUserId(Long userId);
    List<Expense> getExpensesByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Expense saveExpense(Expense expense);
    void deleteExpense(Long id);
    BigDecimal getTotalExpenseByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Map<String, BigDecimal> getExpenseByType(Long userId, LocalDate startDate, LocalDate endDate);
}