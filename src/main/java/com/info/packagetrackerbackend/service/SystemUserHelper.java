package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SystemUserHelper {

    public SystemUser getCurrentUser() {
        return mapToSystemUser(SecurityContextHolder.getContext().getAuthentication());
    }

    private SystemUser mapToSystemUser(Authentication authentication) {
        SystemUser user = new SystemUser();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        user.setId(userDetails.getId());
        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        return user;
    }

}
