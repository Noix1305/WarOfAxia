package principal.maquinaestado.juego;

import principal.ElementosPrincipales;
import principal.entes.enemigo.Enemigo;
import principal.entes.jugador.Jugador;
import principal.maquinaestado.GestorEstados;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GestorGuardado {

    public void guardarJuego() {
        Jugador jugador = ElementosPrincipales.jugador;
        Jugador jugadorGuardar = new Jugador(jugador.getGestorAt(), jugador.getAlmacenEquipo(),
                jugador.getAccesoRapido(), jugador.getAccionesJugador(), jugador.getAreaPosicional());
        ArrayList<Enemigo> enemigos = new ArrayList<>();

        // Crear un estado del juego
        EstadoJuegoGuardar estadoJuego = new EstadoJuegoGuardar(jugadorGuardar, ElementosPrincipales.mapa.getNombreMapaActual());
        System.out.println(estadoJuego.getMapaActual());

        // Guardar el estado del juego
        LocalDateTime fechaActual = LocalDateTime.now();

        // Formatear la fecha y hora como cadena
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String fechaFormateada = fechaActual.format(formato);
        JuegoGuardado.guardarEstadoJuego(estadoJuego, "juegosGuardados", "guardado." + fechaFormateada);
    }

    public void cargarJuego() {
        // Directorio donde se guardan los archivos
        String carpetaGuardados = "juegosGuardados";

        // Buscar el archivo más reciente en la carpeta
        File directorio = new File(carpetaGuardados);
        File[] archivosGuardados = directorio.listFiles((dir, name) -> name.endsWith(".save"));

        if (archivosGuardados == null || archivosGuardados.length == 0) {
            System.out.println("No se encontraron archivos de guardado.");
            return;
        }

        // Ordenar los archivos por nombre (asumiendo que el formato de fecha garantiza el orden)
        Arrays.sort(archivosGuardados, Comparator.comparing(File::getName).reversed());

        // Seleccionar el más reciente
        File archivoMasReciente = archivosGuardados[0];
        System.out.println("Cargando archivo: " + archivoMasReciente.getName());

        // Cargar el estado del juego
        EstadoJuegoGuardar estadoCargado = JuegoGuardado.cargarEstadoJuego(archivoMasReciente.getPath());

        // Verificar los datos cargados
        if (estadoCargado != null) {
            GestorJuego.cargarJuego = true;
            System.out.println("Jugador: " + estadoCargado.getJugador());
            ElementosPrincipales.jugador = estadoCargado.getJugador();
            System.out.println("Mapa al cargar: " + estadoCargado.getMapaActual());
            GestorJuego.cargarMapa(estadoCargado.getMapaActual());
            System.out.println("Jugador nuevo: " + ElementosPrincipales.jugador);
        }
    }
}
