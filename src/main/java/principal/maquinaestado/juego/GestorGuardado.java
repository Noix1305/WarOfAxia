package principal.maquinaestado.juego;

import principal.ElementosPrincipales;
import principal.entes.enemigo.Enemigo;
import principal.entes.jugador.Jugador;
import principal.habilidades.Habilidad;
import principal.inventario.Objeto;
import principal.inventario.RegistroObjetos;
import principal.maquinaestado.GestorEstados;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GestorGuardado {

    public void guardarJuego() {
        EstadoJuegoGuardar estadoJuego = getEstadoJuegoGuardar();
        // Guardar el estado del juego
        LocalDateTime fechaActual = LocalDateTime.now();

        // Formatear la fecha y hora como cadena
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String fechaFormateada = fechaActual.format(formato);
        JuegoGuardado.guardarEstadoJuego(estadoJuego, "juegosGuardados", "guardado." + fechaFormateada);
    }

    private static EstadoJuegoGuardar getEstadoJuegoGuardar() {
        Jugador jugador = ElementosPrincipales.jugador;
        Jugador jugadorGuardar = new Jugador(jugador.getGestorAt(),
                jugador.getAccionesJugador(), jugador.getAreaPosicional());
        ArrayList<Integer[]> listaObjetos = ElementosPrincipales.inventario.obtenerListaIndicesObjetos();
        ArrayList<Integer> listaHabilidades = ElementosPrincipales.inventario.obtenerIndicesHabilidades();
        ArrayList<Integer> listaEnemigos = ElementosPrincipales.inventario.obtenerIndiceEnemigosBestiario();
        ArrayList<Integer[]> listaAccesos = ElementosPrincipales.jugador.getAccesoRapido().obtenerIndiceYObjeto();
        ArrayList<Integer> listaEquipoActual = ElementosPrincipales.jugador.getAlmacenEquipo().obtenerIndicesEquipo();

        // Crear un estado del juego
        return new EstadoJuegoGuardar(jugadorGuardar, ElementosPrincipales.mapa.getNombreMapaActual(),
                listaObjetos, listaHabilidades, listaEnemigos, listaAccesos, listaEquipoActual);
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

        // Cargar el estado del juego
        EstadoJuegoGuardar estadoCargado = JuegoGuardado.cargarEstadoJuego(archivoMasReciente.getPath());

        // Verificar los datos cargados
        if (estadoCargado != null) {
            ArrayList<Objeto> listaObjetosCreados = ElementosPrincipales.inventario.
                    actualizarInventarioMochila(estadoCargado.getListaIndicesObjetos());
            ArrayList<Habilidad> listaHabilidadesCreadas = ElementosPrincipales.inventario.
                    actualizarListaHabilidades(estadoCargado.getListaHabilidades());
            ArrayList<Enemigo> listaEnemigos = ElementosPrincipales.inventario.actualizarListaEnemigos(estadoCargado.getListaIndicesEnemigos());


            GestorJuego.cargarJuego = true;
            ElementosPrincipales.jugador = estadoCargado.getJugador();
            ElementosPrincipales.inventario.objetos = listaObjetosCreados;
            ElementosPrincipales.inventario.habilidades = listaHabilidadesCreadas;
            ElementosPrincipales.inventario.enemigosEliminados = listaEnemigos;
            ElementosPrincipales.jugador.getAccesoRapido().actualizarAccesos(estadoCargado.getListaAccesos());
            ElementosPrincipales.jugador.getAlmacenEquipo().actualizarEquipoActual(estadoCargado.getListaEquipoActual());
            GestorJuego.cargarMapa(estadoCargado.getMapaActual());
        }
    }
}
