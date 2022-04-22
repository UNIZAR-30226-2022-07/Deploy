package com.cerea_p1.spring.jpa.postgresql.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/onep1-game").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
      //  registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic");
       // config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/game");
    }
}
