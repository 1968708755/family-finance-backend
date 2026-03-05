package com.familyfinance.service.impl;

import com.familyfinance.entity.Asset;
import com.familyfinance.repository.AssetRepository;
import com.familyfinance.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {
    
    @Autowired
    private AssetRepository assetRepository;
    
    @Override
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
    
    @Override
    public List<Asset> getAssetsByUserId(Long userId) {
        return assetRepository.findByUserId(userId);
    }
    
    @Override
    public List<Asset> getAssetsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        return assetRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
    
    @Override
    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }
    
    @Override
    public void deleteAsset(Long id) {
        assetRepository.deleteById(id);
    }
    
    @Override
    public BigDecimal getTotalAssetByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal total = assetRepository.sumByUserIdAndDateBetween(userId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    public Map<String, BigDecimal> getAssetByType(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = assetRepository.groupByType(userId, startDate, endDate);
        Map<String, BigDecimal> assetByType = new HashMap<>();
        
        for (Object[] result : results) {
            String type = (String) result[0];
            BigDecimal amount = (BigDecimal) result[1];
            assetByType.put(type, amount);
        }
        
        return assetByType;
    }
    
    @Override
    public Asset getLatestAssetByUserIdAndType(Long userId, String type) {
        return assetRepository.findLatestByUserIdAndType(userId, type);
    }
}