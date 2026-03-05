package com.familyfinance.controller;

import com.familyfinance.entity.Asset;
import com.familyfinance.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssetController {
    
    @Autowired
    private AssetService assetService;
    
    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Asset>> getAssetsByUserId(@PathVariable Long userId) {
        List<Asset> assets = assetService.getAssetsByUserId(userId);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<Asset>> getAssetsByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Asset> assets = assetService.getAssetsByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<BigDecimal> getTotalAssetByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        BigDecimal total = assetService.getTotalAssetByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/by-type")
    public ResponseEntity<Map<String, BigDecimal>> getAssetByType(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Map<String, BigDecimal> assetByType = assetService.getAssetByType(userId, start, end);
        return new ResponseEntity<>(assetByType, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/latest/{type}")
    public ResponseEntity<Asset> getLatestAssetByUserIdAndType(
            @PathVariable Long userId,
            @PathVariable String type) {
        Asset asset = assetService.getLatestAssetByUserIdAndType(userId, type);
        return asset != null ? new ResponseEntity<>(asset, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    public ResponseEntity<Asset> saveAsset(@RequestBody Asset asset) {
        Asset savedAsset = assetService.saveAsset(asset);
        return new ResponseEntity<>(savedAsset, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        asset.setId(id);
        Asset updatedAsset = assetService.saveAsset(asset);
        return new ResponseEntity<>(updatedAsset, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}