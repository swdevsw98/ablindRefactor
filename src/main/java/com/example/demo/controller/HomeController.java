package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public ResponseEntity home() {
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity admin() { return new ResponseEntity("success", HttpStatus.OK); }

    @GetMapping("/user")
    public ResponseEntity user() { return new ResponseEntity("success", HttpStatus.OK); }
}
