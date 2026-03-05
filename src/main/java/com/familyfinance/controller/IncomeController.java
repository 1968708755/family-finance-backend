package com.familyfinance.controller;

import com.familyfinance.entity.Income;
import com.familyfinance.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IncomeController {
    
    @Autowired
    private IncomeService incomeService;
    
    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Income>> getIncomesByUserId(@PathVariable Long userId) {
        List<Income> incomes = incomeService.getIncomesByUserId(userId);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<Income>> getIncomesByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Income> incomes = incomeService.getIncomesByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<BigDecimal> getTotalIncomeByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        BigDecimal total = incomeService.getTotalIncomeByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/by-type")
    public ResponseEntity<Map<String, BigDecimal>> getIncomeByType(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Map<String, BigDecimal> incomeByType = incomeService.getIncomeByType(userId, start, end);
        return new ResponseEntity<>(incomeByType, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Income> saveIncome(@RequestBody Income income) {
        Income savedIncome = incomeService.saveIncome(income);
        return new ResponseEntity<>(savedIncome, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @RequestBody Income income) {
        income.setId(id);
        Income updatedIncome = incomeService.saveIncome(income);
        return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}