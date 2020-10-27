package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.auth.SystemUser;
import com.info.packagetrackerbackend.service.SystemUserService;
import com.info.packagetrackerbackend.validators.RegistrationValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user")
public class SystemUserController {

    private SystemUserService systemUserService;
    private RegistrationValidator validator;

    public SystemUserController(SystemUserService systemUserService, RegistrationValidator validator) {
        this.systemUserService = systemUserService;
        this.validator = validator;
    }

    @InitBinder
    private void bindValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(validator);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<SystemUser> register(@Valid @RequestBody SystemUser user) {
        return new ResponseEntity<>(systemUserService.registerUser(user), HttpStatus.OK);
    }

}
