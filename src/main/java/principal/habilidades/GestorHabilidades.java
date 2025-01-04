package principal.habilidades;

import java.util.ArrayList;
import java.util.List;

import principal.ElementosPrincipales;
import principal.entes.Entidad;
import principal.inventario.Objeto;
import principal.inventario.TipoObjeto;
import principal.inventario.consumibles.Consumible;

/**
 * Clase que gestiona las habilidades del jugador.
 */
public class GestorHabilidades {

    /**
     * Constructor de la clase GestorHabilidades.
     */
    public GestorHabilidades() {
    }

    public static Habilidad obtenerHabilidad(final int idHabilidad) {
        Curacion curacion;
        Danho danho;
        return switch (idHabilidad) {
            case 1 -> {
                curacion = new Curacion(1, "Curacion Basica", 1, 10,
                        ElementosPrincipales.jugador, 10, 0, 30,
                        1, 0, TipoObjeto.ACTIVA, TipoObjeto.CURACION, 0, 15);
                yield curacion;
            }
            case 2 -> {
                curacion = new Curacion(2, "Curacion Media", 1, 15,
                        ElementosPrincipales.jugador, 15, 0, 40,
                        1, 1, TipoObjeto.ACTIVA, TipoObjeto.CURACION, 0, 25);
                yield curacion;
            }
            case 3 -> {
                curacion = new Curacion(3, "Curacion Avanzada", 1, 30,
                        ElementosPrincipales.jugador, 30, 0, 50,
                        2, 5, TipoObjeto.ACTIVA, TipoObjeto.CURACION, 0, 35);
                yield curacion;
            }
            case 4 -> {
                danho = new Danho(4, "Ataque Básico",
                        0, 10, 10, 0, 1,
                        2, 2, TipoObjeto.ACTIVA, TipoObjeto.AOT, 3);
                yield danho;
            }
            default -> null;
        };
    }
}
