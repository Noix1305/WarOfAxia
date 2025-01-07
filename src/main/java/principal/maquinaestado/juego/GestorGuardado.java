package principal.maquinaestado.juego;

import principal.ElementosPrincipales;
import principal.entes.enemigo.Enemigo;
import principal.entes.jugador.AlmacenEquipo;
import principal.entes.jugador.Jugador;
import principal.habilidades.Habilidad;
import principal.inventario.Objeto;
import principal.inventario.RegistroObjetos;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionBaja;
import principal.inventario.armaduras.ProteccionLateral;
import principal.inventario.armaduras.ProteccionMedia;
import principal.inventario.armas.Arma;
import principal.inventario.joyas.Accesorio;
import principal.inventario.joyas.Anillo;
import principal.inventario.joyas.Collar;
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
        // Directorio donde se guardan los archivos
        String carpetaGuardados = "juegosGuardados";

        // Crear un objeto File para el directorio
        File directorio = new File(carpetaGuardados);

        // Obtener todos los archivos con extensión ".save"
        File[] archivosGuardados = directorio.listFiles((dir, name) -> name.endsWith(".save"));

        if (archivosGuardados != null && archivosGuardados.length >= 5) {
            // Ordenar los archivos por fecha de nombre (asumiendo que el nombre contiene fecha y es ordenable)
            Arrays.sort(archivosGuardados, Comparator.comparing(File::getName));

            // Eliminar el archivo más antiguo
            if (archivosGuardados[0].delete()) {
                System.out.println("Archivo más antiguo eliminado: " + archivosGuardados[0].getName());
            } else {
                System.out.println("No se pudo eliminar el archivo más antiguo.");
            }
        }

        // Formatear la fecha y hora actual como cadena para el nuevo archivo
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String fechaFormateada = fechaActual.format(formato);

        // Guardar el nuevo estado del juego
        JuegoGuardado.guardarEstadoJuego(estadoJuego, carpetaGuardados, "guardado." + fechaFormateada);
        System.out.println("Juego guardado como: guardado." + fechaFormateada);
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
        mostrarArchivosGuardados();

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

            ElementosPrincipales.jugador = estadoCargado.getJugador();
            ElementosPrincipales.inventario.objetos = listaObjetosCreados;
            ElementosPrincipales.inventario.habilidades = listaHabilidadesCreadas;
            ElementosPrincipales.inventario.enemigosEliminados = listaEnemigos;
            ElementosPrincipales.jugador.getAccesoRapido().actualizarAccesos(estadoCargado.getListaAccesos());
            ElementosPrincipales.jugador.getAlmacenEquipo().actualizarEquipoActual(estadoCargado.getListaEquipoActual());
            actualizarEquipo();
            GestorJuego.cargarJuego = true;
            GestorJuego.cargarMapa(estadoCargado.getMapaActual());
        }
    }

    private void actualizarEquipo() {
        AlmacenEquipo ae = ElementosPrincipales.jugador.getAlmacenEquipo();
        for (Objeto objeto : ae.getEquipoActual()) {
            switch (objeto.getClass().getSimpleName()) {
                case "ArmaUnaMano", "ArmaDosManos":
                    ae.cambiarArma1((Arma) objeto);
                    break;
                case "ProteccionAlta":
                    ae.setCasco((ProteccionAlta) objeto);
                    break;
                case "ProteccionMedia":
                    ae.setArmaduraMedia((ProteccionMedia) objeto);
                    break;
                case "ProteccionLateral":
                    ae.setGuante((ProteccionLateral) objeto);
                    break;
                case "ProteccionBaja":
                    ae.setBota((ProteccionBaja) objeto);
                    break;
                case "Collar":
                    ae.setCollar((Collar) objeto);
                    break;
                case "Anillo":
                    if (ae.getAnillo1() == null) {
                        ae.setAnillo1((Anillo) objeto);
                    } else {
                        ae.setAnillo2((Anillo) objeto);
                    }
                    break;
                case "Accesorio":
                    ae.setAccesorio((Accesorio) objeto);
                    break;
                default:
                    break;
            }
        }
    }

    public void mostrarArchivosGuardados() {
        // Directorio donde se guardan los archivos
        String carpetaGuardados = "juegosGuardados";

        // Crear un objeto File para el directorio
        File directorio = new File(carpetaGuardados);

        // Obtener todos los archivos con extensión ".save"
        File[] archivosGuardados = directorio.listFiles((dir, name) -> name.endsWith(".save"));

        // Verificar si hay archivos guardados
        if (archivosGuardados == null || archivosGuardados.length == 0) {
            System.out.println("No se encontraron archivos de guardado.");
            return;
        }

        // Mostrar los archivos en la consola
        System.out.println("Archivos guardados disponibles:");
        for (int i = 0; i < archivosGuardados.length; i++) {
            System.out.println((i + 1) + ". " + archivosGuardados[i].getName());
        }
    }

}
