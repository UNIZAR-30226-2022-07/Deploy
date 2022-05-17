package com.cerea_p1.spring.jpa.postgresql.security.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.List;

import com.cerea_p1.spring.jpa.postgresql.model.game.Carta;
import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;
import com.cerea_p1.spring.jpa.postgresql.payload.response.Jugada;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and 
 * send a sample message to server.
 * 
 * @author Kalyan
 *
 */
public class OneStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(OneStompSessionHandler.class);
    private String id ;
    private Carta c;
    private List<Jugador> j;
    private String n;
    public OneStompSessionHandler(String id, Carta c, List<Jugador> j, String n){
        this.id = id;
        this.c = c;
        this.j = j;
        this.n = n;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
    //    session.subscribe("/topic/messages", this);
        logger.info("Subscribed to /topic/messages");
        session.send("/topic/jugada"+id, getJugada(c, j, n));
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Jugada.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
     //   Message msg = (Message) payload;
        Jugada j = (Jugada) payload;
       // logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());
    }

    Jugada getJugada(Carta c, List<Jugador> j, String n){
       // Jugada play = new Jugada(getUltimaCartaJugada(),getJugadores(), getTurno().getNombre());
        return new Jugada(c,j,n);
    }
}