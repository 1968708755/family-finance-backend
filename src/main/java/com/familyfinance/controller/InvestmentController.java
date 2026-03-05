package com.familyfinance.controller;

import com.familyfinance.entity.Investment;
import com.familyfinance.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investments")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InvestmentController {
    
    @Autowired
    private InvestmentService investmentService;
    
    @GetMapping
    public ResponseEntity<List<Investment>> getAllInvestments() {
        List<Investment> investments = investmentService.getAllInvestments();
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Investment>> getInvestmentsByUserId(@PathVariable Long userId) {
        List<Investment> investments = investmentService.getInvestmentsByUserId(userId);
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }
    
    @GetMapping("/project/{projectName}")
    public ResponseEntity<List<Investment>> getInvestmentsByProjectName(@PathVariable String projectName) {
        List<Investment> investments = investmentService.getInvestmentsByProjectName(projectName);
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}/project/{projectName}")
    public ResponseEntity<List<Investment>> getInvestmentsByUserIdAndProjectName(
            @PathVariable Long userId,
            @PathVariable String projectName) {
        List<Investment> investments = investmentService.getInvestmentsByUserIdAndProjectName(userId, projectName);
        return new ResponseEntity<>(investments, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Investment> saveInvestment(@RequestBody Investment investment) {
        Investment savedInvestment = investmentService.saveInvestment(investment);
        return new ResponseEntity<>(savedInvestment, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Investment> updateInvestment(@PathVariable Long id, @RequestBody Investment investment) {
        investment.setId(id);
        Investment updatedInvestment = investmentService.saveInvestment(investment);
        return new ResponseEntity<>(updatedInvestment, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Long id) {
        investmentService.deleteInvestment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}