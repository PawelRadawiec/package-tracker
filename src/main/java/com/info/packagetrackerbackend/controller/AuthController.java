package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.LoginRequest;
import com.info.packagetrackerbackend.model.SystemUser;
import com.info.packagetrackerbackend.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private AuthorizationService authorizationService;

    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authorizationService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<SystemUser> register(@RequestBody SystemUser user) {
        return new ResponseEntity<>(authorizationService.registerUser(user), HttpStatus.OK);
    }


}