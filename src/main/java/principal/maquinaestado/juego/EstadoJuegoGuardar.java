package principal.maquinaestado.juego;

import principal.ElementosPrincipales;
import principal.entes.enemigo.Enemigo;
import principal.entes.jugador.AccesoRapido;
import principal.entes.jugador.AlmacenEquipo;
import principal.entes.jugador.AnimacionJugador;
import principal.entes.jugador.Jugador;
import principal.habilidades.Habilidad;
import principal.inventario.Inventario;
import principal.inventario.Objeto;

import java.io.Serializable;
import java.util.ArrayList;

public class EstadoJuegoGuardar implements Serializable {
    private Jugador jugador;
    private String mapaActual;
    private ArrayList<Integer[]> listaIndicesObjetos = new ArrayList<>();
    private ArrayList<Integer> listaHabilidades;
    private ArrayList<Integer> listaIndicesEnemigos;
    private ArrayList<Integer[]> listaAccesos;
    private ArrayList<Integer> listaEquipoActual;

    public EstadoJuegoGuardar(Jugador jugador, String mapaActual,
                              ArrayList<Integer[]> listaObjetosInventario, ArrayList<Integer> listaHabilidades,
                              ArrayList<Integer> enemigosEliminados, ArrayList<Integer[]> listaAccesos,
                              ArrayList<Integer> listaEquipoActual) {
        this.jugador = jugador;
        this.mapaActual = mapaActual;
        this.listaIndicesObjetos = listaObjetosInventario;
        this.listaHabilidades = listaHabilidades;
        this.listaIndicesEnemigos = enemigosEliminados;
        this.listaAccesos = listaAccesos;
        this.listaEquipoActual = listaEquipoActual;
    }

    // Getters y setters
    public Jugador getJugador() {
        jugador.setAnimacionJugador(new AnimacionJugador(jugador.getAccionesJugador()));
        jugador.setAccesoRapido(new AccesoRapido());
        jugador.setAlmacenEquipo(new AlmacenEquipo());
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public String getMapaActual() {
        return mapaActual;
    }

    public void setMapaActual(String mapaActual) {
        this.mapaActual = mapaActual;
    }

    public ArrayList<Integer[]> getListaIndicesObjetos() {
        return listaIndicesObjetos;
    }

    public void setListaIndicesObjetos(ArrayList<Integer[]> listaIndicesObjetos) {
        this.listaIndicesObjetos = listaIndicesObjetos;
    }

    public ArrayList<Integer> getListaHabilidades() {
        return listaHabilidades;
    }

    public void setListaHabilidades(ArrayList<Integer> listaHabilidades) {
        this.listaHabilidades = listaHabilidades;
    }

    public ArrayList<Integer> getListaIndicesEnemigos() {
        return listaIndicesEnemigos;
    }

    public void setListaIndicesEnemigos(ArrayList<Integer> listaIndicesEnemigos) {
        this.listaIndicesEnemigos = listaIndicesEnemigos;
    }

    public ArrayList<Integer[]> getListaAccesos() {
        return listaAccesos;
    }

    public void setListaAccesos(ArrayList<Integer[]> listaAccesos) {
        this.listaAccesos = listaAccesos;
    }

    public ArrayList<Integer> getListaEquipoActual() {
        return listaEquipoActual;
    }

    public void setListaEquipoActual(ArrayList<Integer> listaEquipoActual) {
        this.listaEquipoActual = listaEquipoActual;
    }
}
