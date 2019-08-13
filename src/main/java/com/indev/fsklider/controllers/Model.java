package com.indev.fsklider.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
@RequestMapping
public class Model {
    @CrossOrigin
    @PostMapping("/api/json")
    public ResponseEntity<String> setModel(@RequestBody String model) {
        System.out.println(model);
        try {
            Files.write(Paths.get(System.getProperty("user.dir") + "/src/main/resources" + "/graph.json"), model.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("OK");
    }

    @CrossOrigin
    @GetMapping(value = "/api/model", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getModel() {
        String str = null;
        try {
            str = Files.readString(Paths.get(System.getProperty("user.dir") + "/src/main/resources" + "/graph.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(str);
    }
}
