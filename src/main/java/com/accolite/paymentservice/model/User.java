package com.accolite.paymentservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private Long corX;
    private Long corY;
    private String token;
    private Integer isActive;
    private Integer paymentMode;
    private BigDecimal balance;
    private BigDecimal offlineBalance;
}
