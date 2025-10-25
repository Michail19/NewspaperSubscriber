package com.ms.userservice.repository;

import com.ms.userservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findById(Long employeeId);

}
