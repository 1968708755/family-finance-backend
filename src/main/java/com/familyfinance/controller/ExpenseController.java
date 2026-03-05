package com.familyfinance.controller;

import com.familyfinance.entity.Expense;
import com.familyfinance.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Expense>> getExpensesByUserId(@PathVariable Long userId) {
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<Expense>> getExpensesByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Expense> expenses = expenseService.getExpensesByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<BigDecimal> getTotalExpenseByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        BigDecimal total = expenseService.getTotalExpenseByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/by-type")
    public ResponseEntity<Map<String, BigDecimal>> getExpenseByType(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Map<String, BigDecimal> expenseByType = expenseService.getExpenseByType(userId, start, end);
        return new ResponseEntity<>(expenseByType, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Expense> saveExpense(@RequestBody Expense expense) {
        Expense savedExpense = expenseService.saveExpense(expense);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        expense.setId(id);
        Expense updatedExpense = expenseService.saveExpense(expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}