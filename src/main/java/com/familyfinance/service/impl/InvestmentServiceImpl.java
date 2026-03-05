package com.familyfinance.service.impl;

import com.familyfinance.entity.Investment;
import com.familyfinance.repository.InvestmentRepository;
import com.familyfinance.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentServiceImpl implements InvestmentService {
    
    @Autowired
    private InvestmentRepository investmentRepository;
    
    @Override
    public List<Investment> getAllInvestments() {
        return investmentRepository.findAll();
    }
    
    @Override
    public List<Investment> getInvestmentsByUserId(Long userId) {
        return investmentRepository.findByUserId(userId);
    }
    
    @Override
    public List<Investment> getInvestmentsByProjectName(String projectName) {
        return investmentRepository.findByProjectName(projectName);
    }
    
    @Override
    public List<Investment> getInvestmentsByUserIdAndProjectName(Long userId, String projectName) {
        return investmentRepository.findByUserIdAndProjectName(userId, projectName);
    }
    
    @Override
    public Investment saveInvestment(Investment investment) {
        // 计算收益率
        if (investment.getPrincipal().compareTo(java.math.BigDecimal.ZERO) > 0) {
            java.math.BigDecimal income = investment.getCurrentValue().subtract(investment.getPrincipal());
            investment.setIncome(income);
            java.math.BigDecimal rate = income.divide(investment.getPrincipal(), 4, java.math.RoundingMode.HALF_UP).multiply(new java.math.BigDecimal(100));
            investment.setRateOfReturn(rate);
        }
        return investmentRepository.save(investment);
    }
    
    @Override
    public void deleteInvestment(Long id) {
        investmentRepository.deleteById(id);
    }
}