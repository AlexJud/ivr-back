package com.indev.fsklider.controllers;

import com.indev.fsklider.services.FtpHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping
public class AsteriskController {
    @Autowired
    FtpHelper ftpHelper;

//    @CrossOrigin
//    @PostMapping("/api/grammar")
//    public ResponseEntity<ResponseMessage> getGrammar(@RequestParam("uploadFile") MultipartFile grammarFile, @RequestParam("fileName") String name) {
//        try {
//            Path grammarPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/grammars/" + name);
//            Files.write(grammarPath, grammarFile.getBytes());
//            ftpHelper.sendFile(name, new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/grammars/" + name));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Grammar file load successful"));
////        return ResponseEntity.ok(new ResponseMessage("Grammar file load successful"));
//}

    @GetMapping("/api/vars")
    public ArrayList<String> getVars() {
        return null;
    }
}
