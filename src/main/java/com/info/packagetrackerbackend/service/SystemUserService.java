package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Role;
import com.info.packagetrackerbackend.model.SystemUser;
import com.info.packagetrackerbackend.model.UserRole;
import com.info.packagetrackerbackend.service.repository.RoleRepository;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SystemUserService {

    private SystemUserRepository userRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;

    public SystemUserService(
            SystemUserRepository userRepository,
            PasswordEncoder encoder, RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    public SystemUser registerUser(SystemUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(UserRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found")));
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }
}
