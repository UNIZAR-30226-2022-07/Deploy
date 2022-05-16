package com.cerea_p1.spring.jpa.postgresql.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Torneo{
    private List<Partida> lista_partidas = new ArrayList<Partida>();
    private List<Jugador> jugadores = new ArrayList<Jugador>();
    private String idTorneo;
    private EstadoPartidaEnum estado;
    private final static int nJugadores = 9;

    public Torneo (List<Partida> torneo){
        this.lista_partidas = torneo;
        idTorneo = UUID.randomUUID().toString();
        estado = EstadoPartidaEnum.NEW;
    }

    public String getIdTorneo(){
        return idTorneo;
    }

    public void addJugador(Jugador j) {
        jugadores.add(j);
    }

    public boolean removePlayer(Jugador j){
        if(jugadores.contains(j)){
            jugadores.remove(j);
            return true;
        } else return false;
    }

    public List<Partida> getPartidas(){
        return lista_partidas;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public int getNJugadores() {
        return nJugadores;
    }

    public boolean playerAlreadyIn(Jugador p){
        return jugadores.contains(p);
    }

    public EstadoPartidaEnum getEstadoTorneo(){
        return estado;
    }

    public void setEstadoTorneo(EstadoPartidaEnum e){
        this.estado = e;
    }

    public int getTiempoTurno(){
        return lista_partidas.get(0).getTTurno();
    }

    public List<Regla> getReglas(){
        return lista_partidas.get(0).getReglas();
    }
}