package com.info.packagetrackerbackend.security;

import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    SystemUserRepository userRepository;

    public UserDetailsServiceImpl(SystemUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + username)
        );
        return UserDetailsImpl.build(systemUser);
    }


}
