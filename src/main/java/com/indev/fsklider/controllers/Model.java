package com.indev.fsklider.controllers;

import com.indev.fsklider.commands.system.SaveToRedMine;
import com.indev.fsklider.dto.CommandDTO;
import com.indev.fsklider.dto.NodeDTO;
import com.indev.fsklider.graph.nodes.Executable;
import lombok.extern.log4j.Log4j;
import org.reflections.Reflections;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@Log4j
public class Model {

    private final String DIR = System.getProperty("user.dir") + "/src/main/resources/scenarios" + "/";

    @CrossOrigin
    @PostMapping("/api/json")
    public ResponseEntity<String> setModel(@RequestBody String model, @RequestParam String fileName, @RequestParam Boolean call) {
//        System.out.println(model);
        try {
            if (call) {
                Files.write(Paths.get(System.getProperty("user.dir") + "/src/main/resources/graph_exec.json"), model.getBytes());
            } else {
                Files.write(Paths.get(DIR + fileName), model.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @GetMapping(value = "/api/models", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getModels() {
        log.debug("DIRECTORY " + DIR);
        File[] files = new File(DIR).listFiles();
        log.debug("FILES " + Arrays.toString(files));
        List list = new ArrayList(files.length);
        for (File file : files) {
            list.add(file.getName());
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/api/commands")
    public List getCommands() {
        Reflections reflections = new Reflections("com.indev.fsklider.commands.system");
        Set<Class<? extends Executable>> subTypesOf = reflections.getSubTypesOf(Executable.class);
       return subTypesOf.stream()
               .map(aClass -> {
                   try {
                       Executable instance = aClass.getConstructor().newInstance();
                       return new CommandDTO(aClass.getSimpleName(),instance.getDescription());
                   } catch (Exception e) {
                       e.printStackTrace();
                       log.warn("Не удалось создать класс системной команды. У класса должен быть пустой конструктор "+ aClass.getName());
                    }
                   return "error";
               })
               .collect(Collectors.toList());

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
