package com.familyfinance.service;

import com.familyfinance.entity.Investment;

import java.util.List;

public interface InvestmentService {
    List<Investment> getAllInvestments();
    List<Investment> getInvestmentsByUserId(Long userId);
    List<Investment> getInvestmentsByProjectName(String projectName);
    List<Investment> getInvestmentsByUserIdAndProjectName(Long userId, String projectName);
    Investment saveInvestment(Investment investment);
    void deleteInvestment(Long id);
}