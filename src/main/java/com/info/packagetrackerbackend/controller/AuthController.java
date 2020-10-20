package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.LoginRequest;
import com.info.packagetrackerbackend.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private AuthorizationService authorizationService;

    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return new ResponseEntity<>(authorizationService.login(request), HttpStatus.OK);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout() {
        this.authorizationService.logout();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/app/{value}")
    public String appInfo(@PathVariable("value") String value) {
        return "Tracker app v1.0.1: " + value;
    }

}
