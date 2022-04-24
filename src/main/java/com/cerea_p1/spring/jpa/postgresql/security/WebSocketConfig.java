package com.cerea_p1.spring.jpa.postgresql.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;



@Configuration
//@EnableWebSocketMessageBroker
@EnableWebSocketMessageBroker
@Deprecated
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages.simpDestMatchers("/secured/**", "/secured/**/**").authenticated().anyMessage().authenticated();
    }

 /*   @Override 
    protected void configureInbound(
    MessageSecurityMetadataSourceRegistry messages) { 
        messages
        .simpDestMatchers("/secured/**").authenticated()
        .anyMessage().authenticated(); 
    }*/
 
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
        //registry.addEndpoint("/onep1-game").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/onep1-game").withSockJS();

        
    }
}
