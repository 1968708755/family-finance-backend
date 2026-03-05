package com.familyfinance.controller;

import com.familyfinance.entity.Liability;
import com.familyfinance.service.LiabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/liabilities")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LiabilityController {
    
    @Autowired
    private LiabilityService liabilityService;
    
    @GetMapping
    public ResponseEntity<List<Liability>> getAllLiabilities() {
        List<Liability> liabilities = liabilityService.getAllLiabilities();
        return new ResponseEntity<>(liabilities, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Liability>> getLiabilitiesByUserId(@PathVariable Long userId) {
        List<Liability> liabilities = liabilityService.getLiabilitiesByUserId(userId);
        return new ResponseEntity<>(liabilities, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<List<Liability>> getLiabilitiesByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Liability> liabilities = liabilityService.getLiabilitiesByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(liabilities, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<BigDecimal> getTotalLiabilityByUserIdAndDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        BigDecimal total = liabilityService.getTotalLiabilityByUserIdAndDateRange(userId, start, end);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/by-type")
    public ResponseEntity<Map<String, BigDecimal>> getLiabilityByType(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Map<String, BigDecimal> liabilityByType = liabilityService.getLiabilityByType(userId, start, end);
        return new ResponseEntity<>(liabilityByType, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/latest/{type}")
    public ResponseEntity<Liability> getLatestLiabilityByUserIdAndType(
            @PathVariable Long userId,
            @PathVariable String type) {
        Liability liability = liabilityService.getLatestLiabilityByUserIdAndType(userId, type);
        return liability != null ? new ResponseEntity<>(liability, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping
    public ResponseEntity<Liability> saveLiability(@RequestBody Liability liability) {
        Liability savedLiability = liabilityService.saveLiability(liability);
        return new ResponseEntity<>(savedLiability, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Liability> updateLiability(@PathVariable Long id, @RequestBody Liability liability) {
        liability.setId(id);
        Liability updatedLiability = liabilityService.saveLiability(liability);
        return new ResponseEntity<>(updatedLiability, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLiability(@PathVariable Long id) {
        liabilityService.deleteLiability(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}