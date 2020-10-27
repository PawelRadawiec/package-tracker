package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.auth.LoginRequest;
import com.info.packagetrackerbackend.model.auth.LoginResponse;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.security.JwtUtils;
import com.info.packagetrackerbackend.security.UserDetailsImpl;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {
    private JwtUtils jwtUtils;
    private SystemUserRepository userRepository;
    private AuthenticationManager authenticationManager;

    public AuthorizationService(
            JwtUtils jwtUtils, SystemUserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
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
