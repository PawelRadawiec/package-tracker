package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.SystemUser;
import com.info.packagetrackerbackend.service.SystemUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user")
public class SystemUserController {

    private SystemUserService systemUserService;

    public SystemUserController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<SystemUser> register(@RequestBody SystemUser user) {
        return new ResponseEntity<>(systemUserService.registerUser(user), HttpStatus.OK);
    }

}
