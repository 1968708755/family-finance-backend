package com.familyfinance.service.impl;

import com.familyfinance.entity.Liability;
import com.familyfinance.repository.LiabilityRepository;
import com.familyfinance.service.LiabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LiabilityServiceImpl implements LiabilityService {
    
    @Autowired
    private LiabilityRepository liabilityRepository;
    
    @Override
    public List<Liability> getAllLiabilities() {
        return liabilityRepository.findAll();
    }
    
    @Override
    public List<Liability> getLiabilitiesByUserId(Long userId) {
        return liabilityRepository.findByUserId(userId);
    }
    
    @Override
    public List<Liability> getLiabilitiesByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return liabilityRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
    
    @Override
    public Liability saveLiability(Liability liability) {
        return liabilityRepository.save(liability);
    }
    
    @Override
    public void deleteLiability(Long id) {
        liabilityRepository.deleteById(id);
    }
    
    @Override
    public BigDecimal getTotalLiabilityByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = liabilityRepository.sumByUserIdAndDateBetween(userId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    public Map<String, BigDecimal> getLiabilityByType(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = liabilityRepository.groupByType(userId, startDate, endDate);
        Map<String, BigDecimal> liabilityByType = new HashMap<>();
        
        for (Object[] result : results) {
            String type = (String) result[0];
            BigDecimal amount = (BigDecimal) result[1];
            liabilityByType.put(type, amount);
        }
        
        return liabilityByType;
    }
    
    @Override
    public Liability getLatestLiabilityByUserIdAndType(Long userId, String type) {
        return liabilityRepository.findLatestByUserIdAndType(userId, type);
    }
}