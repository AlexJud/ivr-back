package com.indev.fsklider.config;

import lombok.extern.log4j.Log4j;
import org.asteriskjava.manager.ManagerConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Log4j
public class AppStartupRunner implements ApplicationRunner {

    @Autowired
    private ManagerConnection managerConnection;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("AppStartupRunner");
        Path scen = Path.of("scenarios");
        if (!Files.exists(scen)){
            Files.createDirectory(scen);
        }
//        AMIEventListener listener = new AMIEventListener();
//        managerConnection.addEventListener(listener);
//        managerConnection.login();
//        managerConnection.sendAction(new StatusAction());

    }
}
