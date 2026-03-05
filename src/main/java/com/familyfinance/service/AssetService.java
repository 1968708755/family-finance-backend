package com.familyfinance.service;

import com.familyfinance.entity.Asset;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AssetService {
    List<Asset> getAllAssets();
    List<Asset> getAssetsByUserId(Long userId);
    List<Asset> getAssetsByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Asset saveAsset(Asset asset);
    void deleteAsset(Long id);
    BigDecimal getTotalAssetByUserIdAndDateRange(Long userId, LocalDate startDate, LocalDate endDate);
    Map<String, BigDecimal> getAssetByType(Long userId, LocalDate startDate, LocalDate endDate);
    Asset getLatestAssetByUserIdAndType(Long userId, String type);
}