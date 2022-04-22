package com.cerea_p1.spring.jpa.postgresql.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import com.cerea_p1.spring.jpa.postgresql.security.websocket.WebSocketHandler;



@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
 
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/game");
    }
 
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
     //   registry.addEndpoint("/broadcast");  // it is OK to leave it here
        // registry.addEndpoint("/broadcast").withSockJS();
        // custom heartbeat, every 60 sec
        registry.addEndpoint("/onep1-game").withSockJS();
        
    }
}
