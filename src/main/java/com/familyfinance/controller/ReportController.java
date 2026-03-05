package com.familyfinance.controller;

import com.familyfinance.service.AssetService;
import com.familyfinance.service.ExpenseService;
import com.familyfinance.service.IncomeService;
import com.familyfinance.service.LiabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReportController {
    
    @Autowired
    private IncomeService incomeService;
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private LiabilityService liabilityService;
    
    @GetMapping("/balance-sheet")
    public ResponseEntity<Map<String, Object>> getBalanceSheet(
            @RequestParam Long userId,
            @RequestParam String date) {
        LocalDate reportDate = LocalDate.parse(date);
        
        // 获取资产总计
        BigDecimal totalAssets = assetService.getTotalAssetByUserIdAndDateRange(userId, reportDate.withDayOfMonth(1), reportDate);
        
        // 获取负债总计
        BigDecimal totalLiabilities = liabilityService.getTotalLiabilityByUserIdAndDateRange(userId, reportDate.withDayOfMonth(1), reportDate);
        
        // 计算净资产
        BigDecimal netAssets = totalAssets.subtract(totalLiabilities);
        
        // 获取资产明细
        Map<String, BigDecimal> assetsByType = assetService.getAssetByType(userId, reportDate.withDayOfMonth(1), reportDate);
        
        // 获取负债明细
        Map<String, BigDecimal> liabilitiesByType = liabilityService.getLiabilityByType(userId, reportDate.withDayOfMonth(1), reportDate);
        
        Map<String, Object> balanceSheet = new HashMap<>();
        balanceSheet.put("reportDate", reportDate);
        balanceSheet.put("totalAssets", totalAssets);
        balanceSheet.put("totalLiabilities", totalLiabilities);
        balanceSheet.put("netAssets", netAssets);
        balanceSheet.put("assetsByType", assetsByType);
        balanceSheet.put("liabilitiesByType", liabilitiesByType);
        
        return new ResponseEntity<>(balanceSheet, HttpStatus.OK);
    }
    
    @GetMapping("/income-statement")
    public ResponseEntity<Map<String, Object>> getIncomeStatement(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        // 获取总收入
        BigDecimal totalIncome = incomeService.getTotalIncomeByUserIdAndDateRange(userId, start, end);
        
        // 获取总支出
        BigDecimal totalExpense = expenseService.getTotalExpenseByUserIdAndDateRange(userId, start, end);
        
        // 计算净利润
        BigDecimal netIncome = totalIncome.subtract(totalExpense);
        
        // 获取收入明细
        Map<String, BigDecimal> incomeByType = incomeService.getIncomeByType(userId, start, end);
        
        // 获取支出明细
        Map<String, BigDecimal> expenseByType = expenseService.getExpenseByType(userId, start, end);
        
        Map<String, Object> incomeStatement = new HashMap<>();
        incomeStatement.put("period", Map.of("startDate", start, "endDate", end));
        incomeStatement.put("totalIncome", totalIncome);
        incomeStatement.put("totalExpense", totalExpense);
        incomeStatement.put("netIncome", netIncome);
        incomeStatement.put("incomeByType", incomeByType);
        incomeStatement.put("expenseByType", expenseByType);
        
        return new ResponseEntity<>(incomeStatement, HttpStatus.OK);
    }
    
    @GetMapping("/cash-flow-statement")
    public ResponseEntity<Map<String, Object>> getCashFlowStatement(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        // 获取经营活动现金流入（总收入）
        BigDecimal operatingCashIn = incomeService.getTotalIncomeByUserIdAndDateRange(userId, start, end);
        
        // 获取经营活动现金流出（总支出）
        BigDecimal operatingCashOut = expenseService.getTotalExpenseByUserIdAndDateRange(userId, start, end);
        
        // 计算经营活动现金流量净额
        BigDecimal netOperatingCashFlow = operatingCashIn.subtract(operatingCashOut);
        
        Map<String, Object> cashFlowStatement = new HashMap<>();
        cashFlowStatement.put("period", Map.of("startDate", start, "endDate", end));
        cashFlowStatement.put("operatingCashIn", operatingCashIn);
        cashFlowStatement.put("operatingCashOut", operatingCashOut);
        cashFlowStatement.put("netOperatingCashFlow", netOperatingCashFlow);
        
        return new ResponseEntity<>(cashFlowStatement, HttpStatus.OK);
    }
    
    @GetMapping("/financial-overview")
    public ResponseEntity<Map<String, Object>> getFinancialOverview(
            @RequestParam Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        // 获取财务概览数据
        BigDecimal totalIncome = incomeService.getTotalIncomeByUserIdAndDateRange(userId, start, end);
        BigDecimal totalExpense = expenseService.getTotalExpenseByUserIdAndDateRange(userId, start, end);
        BigDecimal netIncome = totalIncome.subtract(totalExpense);
        BigDecimal totalAssets = assetService.getTotalAssetByUserIdAndDateRange(userId, end.withDayOfMonth(1), end);
        BigDecimal totalLiabilities = liabilityService.getTotalLiabilityByUserIdAndDateRange(userId, end.withDayOfMonth(1), end);
        BigDecimal netAssets = totalAssets.subtract(totalLiabilities);
        
        // 计算储蓄率
        BigDecimal savingsRate = BigDecimal.ZERO;
        if (totalIncome.compareTo(BigDecimal.ZERO) > 0) {
            savingsRate = netIncome.divide(totalIncome, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        
        Map<String, Object> overview = new HashMap<>();
        overview.put("period", Map.of("startDate", start, "endDate", end));
        overview.put("totalIncome", totalIncome);
        overview.put("totalExpense", totalExpense);
        overview.put("netIncome", netIncome);
        overview.put("totalAssets", totalAssets);
        overview.put("totalLiabilities", totalLiabilities);
        overview.put("netAssets", netAssets);
        overview.put("savingsRate", savingsRate);
        
        return new ResponseEntity<>(overview, HttpStatus.OK);
    }
}