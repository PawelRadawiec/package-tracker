package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.*;
import com.info.packagetrackerbackend.security.JwtUtils;
import com.info.packagetrackerbackend.security.UserDetailsImpl;
import com.info.packagetrackerbackend.service.repository.RoleRepository;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        SystemUser user = userRepository.findByUsername(request.getUsername()).orElse(new SystemUser());
        user.setLogged(true);
        userRepository.save(user);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new LoginResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                token,
                roles);
    }

    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser user = userRepository.findByUsername(authentication.getName()).orElseGet(SystemUser::new);
        user.setLogged(false);
        userRepository.save(user);
    }

}
