package com.indev.fsklider.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/message")
public class Message {

    private Map<String, String> result;

    @GetMapping
    public ResponseEntity<Map<String, String>> getMessage() {
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public void setMessage(@RequestBody Map<String, String> message) {
        result = message;
    }
}
