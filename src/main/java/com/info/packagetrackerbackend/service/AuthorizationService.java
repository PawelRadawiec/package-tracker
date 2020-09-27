package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.Role;
import com.info.packagetrackerbackend.model.SystemUser;
import com.info.packagetrackerbackend.model.UserRole;
import com.info.packagetrackerbackend.security.JwtUtils;
import com.info.packagetrackerbackend.service.repository.RoleRepository;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import javassist.NotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthorizationService {
    private JwtUtils jwtUtils;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private SystemUserRepository userRepository;
    private AuthenticationManager authenticationManager;

    public AuthorizationService(
            JwtUtils jwtUtils, PasswordEncoder encoder,
            RoleRepository roleRepository, SystemUserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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
