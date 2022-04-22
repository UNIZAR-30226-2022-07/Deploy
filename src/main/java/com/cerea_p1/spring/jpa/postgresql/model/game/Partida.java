package com.cerea_p1.spring.jpa.postgresql.model.game;

import java.util.*;
import com.cerea_p1.spring.jpa.postgresql.model.game.Jugador;
import com.cerea_p1.spring.jpa.postgresql.model.game.Color;
import com.cerea_p1.spring.jpa.postgresql.model.game.Numero;

public class Partida {
    private List<Jugador> jugadores;
    private List<Carta> baraja;
    private List<Carta> descartes;
    private EstadoPartidaEnum estado;
    private String id;
    // true indica que la partida es privada, false indica que la partida es pública
    private boolean partidaPrivada;

    public Partida(boolean tipoPartida) {
        jugadores = new ArrayList<Jugador>();
        baraja = barajaInicial();
        descartes = new ArrayList<Carta>();
        partidaPrivada = tipoPartida;
    }

    public void setTipo(boolean tipo){
        partidaPrivada = tipo;
    }

    public boolean getTipo(){
        return this.partidaPrivada;
    }

    /* Costrucción de la baraja inicial */
    private List<Carta> barajaInicial() {
        List<Carta> baraja = new ArrayList<Carta>();
        
        baraja.add(new Carta(Numero.CERO,Color.ROJO));
        baraja.add(new Carta(Numero.CERO,Color.AMARILLO));
        baraja.add(new Carta(Numero.CERO,Color.AZUL));
        baraja.add(new Carta(Numero.CERO,Color.VERDE));

        baraja.add(new Carta(Numero.UNO,Color.ROJO));
        baraja.add(new Carta(Numero.UNO,Color.ROJO));
        baraja.add(new Carta(Numero.UNO,Color.AMARILLO));
        baraja.add(new Carta(Numero.UNO,Color.AMARILLO));
        baraja.add(new Carta(Numero.UNO,Color.AZUL));
        baraja.add(new Carta(Numero.UNO,Color.AZUL));
        baraja.add(new Carta(Numero.UNO,Color.VERDE));
        baraja.add(new Carta(Numero.UNO,Color.VERDE));
        
        baraja.add(new Carta(Numero.DOS,Color.ROJO));
        baraja.add(new Carta(Numero.DOS,Color.ROJO));
        baraja.add(new Carta(Numero.DOS,Color.AMARILLO));
        baraja.add(new Carta(Numero.DOS,Color.AMARILLO));
        baraja.add(new Carta(Numero.DOS,Color.AZUL));
        baraja.add(new Carta(Numero.DOS,Color.AZUL));
        baraja.add(new Carta(Numero.DOS,Color.VERDE));
        baraja.add(new Carta(Numero.DOS,Color.VERDE));

        baraja.add(new Carta(Numero.TRES,Color.ROJO));
        baraja.add(new Carta(Numero.TRES,Color.ROJO));
        baraja.add(new Carta(Numero.TRES,Color.AMARILLO));
        baraja.add(new Carta(Numero.TRES,Color.AMARILLO));
        baraja.add(new Carta(Numero.TRES,Color.AZUL));
        baraja.add(new Carta(Numero.TRES,Color.AZUL));
        baraja.add(new Carta(Numero.TRES,Color.VERDE));
        baraja.add(new Carta(Numero.TRES,Color.VERDE));

        baraja.add(new Carta(Numero.CUATRO,Color.ROJO));
        baraja.add(new Carta(Numero.CUATRO,Color.ROJO));
        baraja.add(new Carta(Numero.CUATRO,Color.AMARILLO));
        baraja.add(new Carta(Numero.CUATRO,Color.AMARILLO));
        baraja.add(new Carta(Numero.CUATRO,Color.AZUL));
        baraja.add(new Carta(Numero.CUATRO,Color.AZUL));
        baraja.add(new Carta(Numero.CUATRO,Color.VERDE));
        baraja.add(new Carta(Numero.CUATRO,Color.VERDE));

        baraja.add(new Carta(Numero.CINCO,Color.ROJO));
        baraja.add(new Carta(Numero.CINCO,Color.ROJO));
        baraja.add(new Carta(Numero.CINCO,Color.AMARILLO));
        baraja.add(new Carta(Numero.CINCO,Color.AMARILLO));
        baraja.add(new Carta(Numero.CINCO,Color.AZUL));
        baraja.add(new Carta(Numero.CINCO,Color.AZUL));
        baraja.add(new Carta(Numero.CINCO,Color.VERDE));
        baraja.add(new Carta(Numero.CINCO,Color.VERDE));
        
        baraja.add(new Carta(Numero.SEIS,Color.ROJO));
        baraja.add(new Carta(Numero.SEIS,Color.ROJO));
        baraja.add(new Carta(Numero.SEIS,Color.AMARILLO));
        baraja.add(new Carta(Numero.SEIS,Color.AMARILLO));
        baraja.add(new Carta(Numero.SEIS,Color.AZUL));
        baraja.add(new Carta(Numero.SEIS,Color.AZUL));
        baraja.add(new Carta(Numero.SEIS,Color.VERDE));
        baraja.add(new Carta(Numero.SEIS,Color.VERDE));

        baraja.add(new Carta(Numero.SIETE,Color.ROJO));
        baraja.add(new Carta(Numero.SIETE,Color.ROJO));
        baraja.add(new Carta(Numero.SIETE,Color.AMARILLO));
        baraja.add(new Carta(Numero.SIETE,Color.AMARILLO));
        baraja.add(new Carta(Numero.SIETE,Color.AZUL));
        baraja.add(new Carta(Numero.SIETE,Color.AZUL));
        baraja.add(new Carta(Numero.SIETE,Color.VERDE));
        baraja.add(new Carta(Numero.SIETE,Color.VERDE));

        baraja.add(new Carta(Numero.OCHO,Color.ROJO));
        baraja.add(new Carta(Numero.OCHO,Color.ROJO));
        baraja.add(new Carta(Numero.OCHO,Color.AMARILLO));
        baraja.add(new Carta(Numero.OCHO,Color.AMARILLO));
        baraja.add(new Carta(Numero.OCHO,Color.AZUL));
        baraja.add(new Carta(Numero.OCHO,Color.AZUL));
        baraja.add(new Carta(Numero.OCHO,Color.VERDE));
        baraja.add(new Carta(Numero.OCHO,Color.VERDE));

        baraja.add(new Carta(Numero.NUEVE,Color.ROJO));
        baraja.add(new Carta(Numero.NUEVE,Color.ROJO));
        baraja.add(new Carta(Numero.NUEVE,Color.AMARILLO));
        baraja.add(new Carta(Numero.NUEVE,Color.AMARILLO));
        baraja.add(new Carta(Numero.NUEVE,Color.AZUL));
        baraja.add(new Carta(Numero.NUEVE,Color.AZUL));
        baraja.add(new Carta(Numero.NUEVE,Color.VERDE));
        baraja.add(new Carta(Numero.NUEVE,Color.VERDE));

        baraja.add(new Carta(Numero.MAS_DOS,Color.ROJO));
        baraja.add(new Carta(Numero.MAS_DOS,Color.ROJO));
        baraja.add(new Carta(Numero.MAS_DOS,Color.AMARILLO));
        baraja.add(new Carta(Numero.MAS_DOS,Color.AMARILLO));
        baraja.add(new Carta(Numero.MAS_DOS,Color.AZUL));
        baraja.add(new Carta(Numero.MAS_DOS,Color.AZUL));
        baraja.add(new Carta(Numero.MAS_DOS,Color.VERDE));
        baraja.add(new Carta(Numero.MAS_DOS,Color.VERDE));

        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.ROJO));
        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.ROJO));
        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AMARILLO));
        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AMARILLO));
        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AZUL));
        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.AZUL));
        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.VERDE));
        baraja.add(new Carta(Numero.CAMBIO_SENTIDO,Color.VERDE));

        baraja.add(new Carta(Numero.BLOQUEO,Color.ROJO));
        baraja.add(new Carta(Numero.BLOQUEO,Color.ROJO));
        baraja.add(new Carta(Numero.BLOQUEO,Color.AMARILLO));
        baraja.add(new Carta(Numero.BLOQUEO,Color.AMARILLO));
        baraja.add(new Carta(Numero.BLOQUEO,Color.AZUL));
        baraja.add(new Carta(Numero.BLOQUEO,Color.AZUL));
        baraja.add(new Carta(Numero.BLOQUEO,Color.VERDE));
        baraja.add(new Carta(Numero.BLOQUEO,Color.VERDE));
        
        baraja.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));
        baraja.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));
        baraja.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));
        baraja.add(new Carta(Numero.UNDEFINED,Color.CAMBIO_COLOR));

        baraja.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));
        baraja.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));
        baraja.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));
        baraja.add(new Carta(Numero.MAS_CUATRO,Color.CAMBIO_COLOR));

        //barajar
        Collections.shuffle(baraja);

        return baraja;
    }

    public void barajarDescartes() {
        for (int i=0; i<descartes.size()-1; ++i) {
            baraja.add(descartes.get(i));
            descartes.remove(i);
        }
        Collections.shuffle(baraja);
    }

    public boolean barajaVacia() {
        return baraja.isEmpty();
    }

    public void repartirManos() {
        for(int i=0; i<7; ++i) {
            for (Jugador j : jugadores) {
                j.addCarta(baraja.get(0));
                baraja.remove(0);
            }
        }
    }

    public void jugarCarta(Carta c, String nombreJugador) {
        descartes.add(c);
        for (Jugador j : jugadores) {
            if (j.getNombre().equals(nombreJugador))
                j.deleteCarta(c);
        }
    }

    public List<Carta> robarCartas(String nombreJugador, int n) {
        // Frontend espera una lista pequeña en lugar de la mano entera.
        List<Carta> robadas = new ArrayList<Carta>();

        if(n > baraja.size())
            barajarDescartes();

        for(int i=0; i<n; ++i) {
            robadas.add(baraja.get(0));
            baraja.remove(0);
        }

        return robadas;
    }

    public void addJugador(Jugador j) {
        jugadores.add(j);
    }

    public List<Jugador> getJugadores(){
        return jugadores;
    }

    public boolean removePlayer(Jugador j){
        if(jugadores.contains(j)){
            jugadores.remove(j);
            return true;
        } else return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id; 
    }

    public EstadoPartidaEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartidaEnum estado) {
        this.estado = estado;
    }

    public boolean playerAlreadyIn(Jugador p){
        return jugadores.contains(p);
    }
}
