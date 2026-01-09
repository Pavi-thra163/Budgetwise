package com.budgetwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.budgetwise.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
