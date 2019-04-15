package com.indev.fsklider;

import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.GraphBuilder;
import com.indev.fsklider.graph.GraphExecutor;
import com.indev.fsklider.graph.context.Context;
import com.indev.fsklider.graph.nodes.Node;
import org.asteriskjava.fastagi.AgiScript;
import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//    @Bean
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("AgiServer-");
//        executor.initialize();
//        return executor;
//    }
}
