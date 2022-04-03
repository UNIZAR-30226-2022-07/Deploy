package com.cerea_p1.spring.jpa.postgresql.model.game;

import java.util.*;
import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;
import com.cerea_p1.spring.jpa.postgresql.model.game.Color;
import com.cerea_p1.spring.jpa.postgresql.model.game.Numero;

public class Partida {
    private List<Jugador> jugadores;
    private List<Carta> baraja;
    private List<Carta> descartes;
    private boolean iniciada;

    public Partida() {
        jugadores = new ArrayList<Jugador>();
        baraja = barajaInicial();
        descartes = new ArrayList<Carta>();
        iniciada = false;
    }

    /* Costrucción de la baraja inicial */
    private List<Carta> barajaInicial() {
        List<Carta> descartes = new ArrayList<Carta>();
        
        descartes.add(new Carta(Numero.CERO,Color.ROJO));
        descartes.add(new Carta(Numero.CERO,Color.AMARILLO));
        descartes.add(new Carta(Numero.CERO,Color.AZUL));
        descartes.add(new Carta(Numero.CERO,Color.VERDE));

        descartes.add(new Carta(Numero.UNO,Color.ROJO));
        descartes.add(new Carta(Numero.UNO,Color.ROJO));
        descartes.add(new Carta(Numero.UNO,Color.AMARILLO));
        descartes.add(new Carta(Numero.UNO,Color.AMARILLO));
        descartes.add(new Carta(Numero.UNO,Color.AZUL));
        descartes.add(new Carta(Numero.UNO,Color.AZUL));
        descartes.add(new Carta(Numero.UNO,Color.VERDE));
        descartes.add(new Carta(Numero.UNO,Color.VERDE));
        
        descartes.add(new Carta(Numero.DOS,Color.ROJO));
        descartes.add(new Carta(Numero.DOS,Color.ROJO));
        descartes.add(new Carta(Numero.DOS,Color.AMARILLO));
        descartes.add(new Carta(Numero.DOS,Color.AMARILLO));
        descartes.add(new Carta(Numero.DOS,Color.AZUL));
        descartes.add(new Carta(Numero.DOS,Color.AZUL));
        descartes.add(new Carta(Numero.DOS,Color.VERDE));
        descartes.add(new Carta(Numero.DOS,Color.VERDE));

        descartes.add(new Carta(Numero.TRES,Color.ROJO));
        descartes.add(new Carta(Numero.TRES,Color.ROJO));
        descartes.add(new Carta(Numero.TRES,Color.AMARILLO));
        descartes.add(new Carta(Numero.TRES,Color.AMARILLO));
        descartes.add(new Carta(Numero.TRES,Color.AZUL));
        descartes.add(new Carta(Numero.TRES,Color.AZUL));
        descartes.add(new Carta(Numero.TRES,Color.VERDE));
        descartes.add(new Carta(Numero.TRES,Color.VERDE));

        descartes.add(new Carta(Numero.CUATRO,Color.ROJO));
        descartes.add(new Carta(Numero.CUATRO,Color.ROJO));
        descartes.add(new Carta(Numero.CUATRO,Color.AMARILLO));
        descartes.add(new Carta(Numero.CUATRO,Color.AMARILLO));
        descartes.add(new Carta(Numero.CUATRO,Color.AZUL));
        descartes.add(new Carta(Numero.CUATRO,Color.AZUL));
        descartes.add(new Carta(Numero.CUATRO,Color.VERDE));
        descartes.add(new Carta(Numero.CUATRO,Color.VERDE));

        descartes.add(new Carta(Numero.CINCO,Color.ROJO));
        descartes.add(new Carta(Numero.CINCO,Color.ROJO));
        descartes.add(new Carta(Numero.CINCO,Color.AMARILLO));
        descartes.add(new Carta(Numero.CINCO,Color.AMARILLO));
        descartes.add(new Carta(Numero.CINCO,Color.AZUL));
        descartes.add(new Carta(Numero.CINCO,Color.AZUL));
        descartes.add(new Carta(Numero.CINCO,Color.VERDE));
        descartes.add(new Carta(Numero.CINCO,Color.VERDE));
        
        descartes.add(new Carta(Numero.SEIS,Color.ROJO));
        descartes.add(new Carta(Numero.SEIS,Color.ROJO));
        descartes.add(new Carta(Numero.SEIS,Color.AMARILLO));
        descartes.add(new Carta(Numero.SEIS,Color.AMARILLO));
        descartes.add(new Carta(Numero.SEIS,Color.AZUL));
        descartes.add(new Carta(Numero.SEIS,Color.AZUL));
        descartes.add(new Carta(Numero.SEIS,Color.VERDE));
        descartes.add(new Carta(Numero.SEIS,Color.VERDE));

        descartes.add(new Carta(Numero.SIETE,Color.ROJO));
        descartes.add(new Carta(Numero.SIETE,Color.ROJO));
        descartes.add(new Carta(Numero.SIETE,Color.AMARILLO));
        descartes.add(new Carta(Numero.SIETE,Color.AMARILLO));
        descartes.add(new Carta(Numero.SIETE,Color.AZUL));
        descartes.add(new Carta(Numero.SIETE,Color.AZUL));
        descartes.add(new Carta(Numero.SIETE,Color.VERDE));
        descartes.add(new Carta(Numero.SIETE,Color.VERDE));

        descartes.add(new Carta(Numero.OCHO,Color.ROJO));
        descartes.add(new Carta(Numero.OCHO,Color.ROJO));
        descartes.add(new Carta(Numero.OCHO,Color.AMARILLO));
        descartes.add(new Carta(Numero.OCHO,Color.AMARILLO));
        descartes.add(new Carta(Numero.OCHO,Color.AZUL));
        descartes.add(new Carta(Numero.OCHO,Color.AZUL));
        descartes.add(new Carta(Numero.OCHO,Color.VERDE));
        descartes.add(new Carta(Numero.OCHO,Color.VERDE));

        descartes.add(new Carta(Numero.NUEVE,Color.ROJO));
        descartes.add(new Carta(Numero.NUEVE,Color.ROJO));
        descartes.add(new Carta(Numero.NUEVE,Color.AMARILLO));
        descartes.add(new Carta(Numero.NUEVE,Color.AMARILLO));
        descartes.add(new Carta(Numero.NUEVE,Color.AZUL));
        descartes.add(new Carta(Numero.NUEVE,Color.AZUL));
        descartes.add(new Carta(Numero.NUEVE,Color.VERDE));
        descartes.add(new Carta(Numero.NUEVE,Color.VERDE));

        descartes.add(new Carta(Numero.MAS_DOS,Color.ROJO));
        descartes.add(new Carta(Numero.MAS_DOS,Color.ROJO));
        descartes.add(new Carta(Numero.MAS_DOS,Color.AMARILLO));
        descartes.add(new Carta(Numero.MAS_DOS,Color.AMARILLO));
        descartes.add(new Carta(Numero.MAS_DOS,Color.AZUL));
        descartes.add(new Carta(Numero.MAS_DOS,Color.AZUL));
        descartes.add(new Carta(Numero.MAS_DOS,Color.VERDE));
        descartes.add(new Carta(Numero.MAS_DOS,Color.VERDE));

        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.ROJO));
        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.ROJO));
        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AMARILLO));
        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AMARILLO));
        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AZUL));
        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AZUL));
        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.VERDE));
        descartes.add(new Carta(Numero.CAMBIO_SENTIDO,Color.VERDE));

        descartes.add(new Carta(Numero.BLOQUEO,Color.ROJO));
        descartes.add(new Carta(Numero.BLOQUEO,Color.ROJO));
        descartes.add(new Carta(Numero.BLOQUEO,Color.AMARILLO));
        descartes.add(new Carta(Numero.BLOQUEO,Color.AMARILLO));
        descartes.add(new Carta(Numero.BLOQUEO,Color.AZUL));
        descartes.add(new Carta(Numero.BLOQUEO,Color.AZUL));
        descartes.add(new Carta(Numero.BLOQUEO,Color.VERDE));
        descartes.add(new Carta(Numero.BLOQUEO,Color.VERDE));
        
        descartes.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));
        descartes.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));
        descartes.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));
        descartes.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));

        descartes.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));
        descartes.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));
        descartes.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));
        descartes.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));

        // barajar
        Collections.shuffle(baraja);

        return baraja;
    }

    private void repartirManos() {
        for(int i=0; i<7; ++i) {
            for (Jugador j : jugadores){
                j.addCarta(baraja.get(0));
                baraja.remove(0);
            }
        }
    }

    public void addJugador(Jugador j){
        jugadores.add(j);
    }

    public List<Jugador> getJugadores(){
        return jugadores;
    }
}
