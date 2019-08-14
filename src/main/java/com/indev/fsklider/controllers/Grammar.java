package com.indev.fsklider.controllers;

import com.indev.fsklider.beans.ResponseMessage;
import com.indev.fsklider.services.FtpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping
public class Grammar {
    @Autowired
    FtpHelper ftpHelper;

    @CrossOrigin
    @PostMapping("/api/grammar")
    public ResponseEntity<ResponseMessage> getGrammar(@RequestParam("uploadFile") MultipartFile grammarFile, @RequestParam("fileName") String name) {
        try {
            Path grammarPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/grammars/" + name);
            Files.write(grammarPath, grammarFile.getBytes());
            ftpHelper.sendFile(name, new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/grammars/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Grammar file load successful"));
//        return ResponseEntity.ok(new ResponseMessage("Grammar file load successful"));
    }
}
