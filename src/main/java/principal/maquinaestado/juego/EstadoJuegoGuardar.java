package principal.maquinaestado.juego;

import principal.entes.jugador.AnimacionJugador;
import principal.entes.jugador.Jugador;

import java.io.Serializable;

public class EstadoJuegoGuardar implements Serializable {
    private Jugador jugador;
    private String mapaActual;

    public EstadoJuegoGuardar(Jugador jugador, String mapaActual) {
        //Agregar el mapa en donde se esta jugando y luego volver a cargar mapa
        this.jugador = jugador;
        this.mapaActual = mapaActual;
    }

    // Getters y setters
    public Jugador getJugador() {
        //Se añade Animacion jugador aparte debido a que BufferedImage no se puede serializar
        jugador.setAnimacionJugador(new AnimacionJugador(jugador.getAccionesJugador()));
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
}
