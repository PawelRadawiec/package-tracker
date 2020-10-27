package com.info.packagetrackerbackend.service;

import com.info.packagetrackerbackend.model.auth.Role;
import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.model.auth.UserRole;
import com.info.packagetrackerbackend.model.basket.Basket;
import com.info.packagetrackerbackend.service.repository.RoleRepository;
import com.info.packagetrackerbackend.service.repository.SystemUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SystemUserService {

    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private SystemUserRepository userRepository;
    private BasketService basketService;

    public SystemUserService(
            SystemUserRepository userRepository,
            PasswordEncoder encoder, RoleRepository roleRepository,
            BasketService basketService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.basketService = basketService;
    }

    public SystemUser registerUser(SystemUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(UserRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role not found")));
        user.setRoles(roles);
        userRepository.save(user);
        appendBasket(user);
        return user;
    }

    private void appendBasket(SystemUser user) {
        Basket basket = new Basket();
        basket.setOwner(user);
        basketService.create(basket);
    }
}
