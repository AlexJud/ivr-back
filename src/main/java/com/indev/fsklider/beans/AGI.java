/*
package com.indev.fsklider.beans;

import org.asteriskjava.Cli;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AGI extends Cli implements CommandLineRunner {
    public AGI() {
    }

    private void startAgiServer() throws IOException {
        this.startAgiServer((Integer)null);
    }

    private void startAgiServer(Integer port) throws IOException {
        DefaultAgiServer server = new DefaultAgiServer();
        if (port != null) {
            server.setPort(port);
        }

        server.startup();
    }

    private void exit(int code) {
        System.exit(code);
    }

    @Override
    public void run(String... args) throws Exception {
        initialize();
    }

    @Async
    public void initialize(String... args) {
        try {
            startAgiServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
