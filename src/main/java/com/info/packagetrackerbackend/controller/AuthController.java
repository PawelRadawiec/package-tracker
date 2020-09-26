package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.security.JwtUtils;
import com.info.packagetrackerbackend.service.repository.RoleRepository;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    JwtUtils jwtUtils;
    PasswordEncoder encoder;
    RoleRepository roleRepository;
    SystemUserRepository userRepository;
    AuthenticationManager authenticationManager;

    public AuthController(
            JwtUtils jwtUtils, PasswordEncoder encoder, RoleRepository roleRepository,
            SystemUserRepository userRepository, AuthenticationManager authenticationManager
    ) {
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }


}
