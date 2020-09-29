package com.info.packagetrackerbackend.controller;

import com.info.packagetrackerbackend.model.Bullet;
import com.info.packagetrackerbackend.service.BulletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/bullet/")
public class BulletController {

    private BulletService service;

    public BulletController(BulletService service) {
        this.service = service;
    }

    @GetMapping(value = "list")
    public ResponseEntity<List<Bullet>> getBullets() {
        return new ResponseEntity<>(service.getBullets(), HttpStatus.OK);
    }

}
