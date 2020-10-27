package com.info.packagetrackerbackend.service.repository;

import com.info.packagetrackerbackend.model.auth.Role;
import com.info.packagetrackerbackend.model.auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRole role);
}
