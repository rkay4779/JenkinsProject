package com.usermanagement.user_management.repository;

import com.usermanagement.user_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
