package com.indev.fsklider.controllers;

import com.indev.fsklider.dto.NodeDTO;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
@Log4j
public class Model {

    private final String DIR = System.getProperty("user.dir") + "/src/main/resources/scenarios" + "/";

    @CrossOrigin
    @PostMapping("/api/json")
    public ResponseEntity<String> setModel(@RequestBody String model, @RequestParam  String fileName, @RequestParam Boolean call) {
//        System.out.println(model);
        try {
            if (call){
                Files.write(Paths.get(System.getProperty("user.dir") + "/src/main/resources/graph_exec.json" ), model.getBytes());
            } else {
                Files.write(Paths.get(DIR + fileName), model.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("OK");
    }

    @CrossOrigin
    @GetMapping(value = "/api/models", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getModels() {
        File[] files = new File(DIR).listFiles();
        List list = new ArrayList(files.length);
        for (File file : files) {
            list.add(file.getName());
        }
        return ResponseEntity.ok(list);
    }

    @CrossOrigin
    @GetMapping(value = "/api/model/{file}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getModel(@PathVariable String file) {
        log.info("INCOMING REQUEST ");
        String str = null;
        try {
            byte[] array = Files.readAllBytes(Paths.get(DIR + file));
            str = new String(array, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(str);
    }

    @CrossOrigin
    @PostMapping("/api/test")
    public ResponseEntity test(@RequestBody NodeDTO[] dto) {
        System.out.println("DTO " + dto.length);

        return ResponseEntity.ok().build();
    }

    public static void main(String[] args) {
        final String DIR = System.getProperty("user.dir") + "/src/main/resources/scenarios" + "/";
        System.out.println("START");
        File dir = new File(DIR);
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println("FILES '"+file.getName());
        }
    }

}
