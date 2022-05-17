package com.wellsfargo.hackathon.pronunciation.web;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping()
    public ResponseEntity error() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
