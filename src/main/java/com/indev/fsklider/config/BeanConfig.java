package com.indev.fsklider.config;

import com.indev.fsklider.agiscripts.GoogleServices;
import com.indev.fsklider.agiscripts.Incoming;
import com.indev.fsklider.graph.nodes.ActionNode;
import com.indev.fsklider.services.SocketService;
import org.asteriskjava.fastagi.AgiScript;
import org.asteriskjava.fastagi.AgiServerThread;
import org.asteriskjava.fastagi.DefaultAgiServer;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {
    @Bean
    public AgiServerThread agiServerThread() {
        AgiServerThread agiServerThread = new AgiServerThread(getDefaultAgiServer());
        agiServerThread.startup();
        return agiServerThread;
    }

    @Bean
    public DefaultAgiServer getDefaultAgiServer() {
        return new DefaultAgiServer(getAsteriskAgiScript());
    }

    @Bean
    public AgiScript getAsteriskAgiScript() {
        return new Incoming();
    }

//    @Bean
//    public SocketService getSocketService() {
//        return new SocketService();
//    }

    @Bean
    public ManagerConnection getManagerConnection() {
        ManagerConnectionFactory factory = new ManagerConnectionFactory("192.168.1.130", "manager", "pass");
        return factory.createManagerConnection();
    }
}
