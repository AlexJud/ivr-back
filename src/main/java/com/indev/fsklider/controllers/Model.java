package com.indev.fsklider.controllers;

import com.indev.fsklider.dto.NodeDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping
public class Model {

    @CrossOrigin
    @PostMapping("/api/json")
    public ResponseEntity<String> setModel(@RequestBody String model) {
        System.out.println(model);
        try {
            Files.write(Paths.get(System.getProperty("user.dir") + "/src/main/resources" + "/graph_exec.json"), model.getBytes());
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
            byte[] array = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/main/resources" + "/graph_exec.json"));
            str = new String(array, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(str);
    }

    @CrossOrigin
    @PostMapping("/api/test")
    public ResponseEntity test(@RequestBody NodeDTO[] dto){
        System.out.println("DTO " + dto.length);

        return ResponseEntity.ok().build();
    }
}
