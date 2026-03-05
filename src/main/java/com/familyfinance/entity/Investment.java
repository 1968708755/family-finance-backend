package com.familyfinance.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "investment")
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "project_name", nullable = false, length = 100)
    private String projectName;
    
    @Column(name = "principal", nullable = false, precision = 10, scale = 2)
    private BigDecimal principal;
    
    @Column(name = "current_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal currentValue;
    
    @Column(name = "income", nullable = false, precision = 10, scale = 2)
    private BigDecimal income;
    
    @Column(name = "rate_of_return", precision = 5, scale = 2)
    private BigDecimal rateOfReturn;
    
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;
    
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;
    
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createTime = now;
        updateTime = now;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}