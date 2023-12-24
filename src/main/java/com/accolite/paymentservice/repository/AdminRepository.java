package com.accolite.paymentservice.repository;

import com.accolite.paymentservice.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Boolean existsByUserName(String userName);
    Optional<Admin> findByUserName(String userName);
}
