package com.accolite.paymentservice.repository;

import com.accolite.paymentservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
    List<User> findAllByIsActive(int value);
}
