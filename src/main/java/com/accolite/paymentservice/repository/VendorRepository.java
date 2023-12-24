package com.accolite.paymentservice.repository;

import com.accolite.paymentservice.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorRepository  extends JpaRepository<Vendor, Long> {
    Boolean existsByUserName(String userName);
    Optional<Vendor> findByUserName(String userName);
    List<Vendor> findAllByIsActive(int value);
}
