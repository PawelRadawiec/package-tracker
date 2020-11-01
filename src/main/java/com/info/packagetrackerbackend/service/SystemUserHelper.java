package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SystemUserHelper {

    private SystemUserRepository userRepository;

    public SystemUserHelper(SystemUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SystemUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseGet(SystemUser::new);
    }

}
