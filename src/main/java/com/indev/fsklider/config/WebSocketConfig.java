package com.indev.fsklider.config;

import com.indev.fsklider.beans.socket.WebSocketEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    WebSocketEventListener listener;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/ivr");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/wss").setAllowedOrigins("*");
    }



}
