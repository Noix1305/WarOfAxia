/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package principal;

import principal.control.GestorControles; // Importa la clase GestorControles del paquete principal.control
import principal.graficos.SuperficieDibujo; // Importa la clase SuperficieDibujo del paquete principal.graficos
import principal.graficos.Ventana; // Importa la clase Ventana del paquete principal.graficos
import principal.maquinaestado.GestorEstados; // Importa la clase GestorEstados del paquete principal.maquinaestado


import java.util.ArrayList;

/**
 * Clase que gestiona el funcionamiento principal del juego.
 */
public class GestorPrincipal {

    private boolean enFuncionamiento = false; // Variable que indica si el juego está en funcionamiento
    private String titulo; // Título de la ventana del juego
    private int ancho; // Ancho de la ventana del juego
    private int alto; // Alto de la ventana del juego
    public static boolean pantallaTitulo = true;
    public static boolean juegoActivo = false;
    public static boolean tiendaActiva = false;
    public static boolean inventarioActivo = false;
    public static boolean menuInicio = false;// Variable que indica si se muestra la pantalla de título
    // Instancias principales del juego
    public static SuperficieDibujo sd; // Superficie de dibujo del juego
    public static GestorEstados ge; // Gestor de estados del juego

    private static int fps = 0; // FPS (cuadros por segundo) del juego
    private static int aps = 0; // APS (actualizaciones por segundo) del juego
    public static int contadorMapa;
    public static boolean enPausa = false;
    public static Ventana ventana;// Indica si el juego está en pausa


    // Constructor privado para evitar instanciación externa
    private GestorPrincipal(final String titulo, final int ancho, final int alto) {
        this.titulo = titulo;
        this.alto = alto;
        this.ancho = ancho;
        contadorMapa = 0;

    }


    // Método principal del programa
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("javafx.animation.fullspeed", "true");
        // Configuración de OpenGL
        GestorPrincipal gp = new GestorPrincipal("War of Axia", Constantes.ANCHO_PANTALLA_COMPLETA,
                Constantes.ALTO_PANTALLA_COMPLETA); // Creación de una instancia del gestor principal

        gp.iniciarJuego(); // Inicio del juego
        gp.iniciarBuclePrincipal(); // Inicio del bucle principal del juego
    }

    // Método para iniciar el juego
    private void iniciarJuego() {
        enFuncionamiento = true; // Establece que el juego está en funcionamiento
        inicializar(); // Inicializa los componentes del juego
    }

    // Método para inicializar los componentes del juego
    private void inicializar() {

        sd = new SuperficieDibujo(ancho, alto); // Inicializa la superficie de dibujo
        // Ventana del juego
        ventana = new Ventana(titulo, sd);
        ge = new GestorEstados(sd); // Inicializa el gestor de estados del juego
    }

    // Método para iniciar el bucle principal del juego
    private void iniciarBuclePrincipal() throws InterruptedException {
        // Variables para el control del bucle principal
        int actualizacionesAcumuladas = 0;
        int framesAcumulados = 0;

        final int NS_POR_SEGUNDO = 1000000000;
        final int APS_OBJETIVO = 60;
        final double NS_POR_ACTUALIZACION = (double) NS_POR_SEGUNDO / APS_OBJETIVO;

        long referenciaActualizacion = System.nanoTime();
        long referenciaContador = System.nanoTime();

        double tiempoTranscurrido;
        double delta = 0;

        // Bucle principal del juego
        while (enFuncionamiento) {
            final long inicioBucle = System.nanoTime();

            tiempoTranscurrido = inicioBucle - referenciaActualizacion;
            referenciaActualizacion = inicioBucle;

            delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;


            while (delta >= 1) {
                actualizar(); // Actualiza el estado del juego
                actualizacionesAcumuladas++;
                delta--;
            }
            dibujar(); // Dibuja el estado del juego
            framesAcumulados++;


            if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
                fps = framesAcumulados;
                aps = actualizacionesAcumuladas;

                actualizacionesAcumuladas = 0;
                framesAcumulados = 0;
                referenciaContador = System.nanoTime();
            }
        }
    }

    // Método para actualizar el estado del juego
    private void actualizar() throws InterruptedException {
        // Cambia el estado del juego según la interacción del jugador
        if (GestorControles.teclado.pausar.estaPulsada() && GestorControles.teclado.pausar.puedeProcesarse()) {
            enPausa = !enPausa;
            GestorControles.teclado.pausar.marcarComoProcesada(); // Marca la tecla como procesada para evitar cambios repetidos
        }
        if (!enPausa) {
            // Lógica de actualización solo si no está en pausa
            if (inventarioActivo && !juegoActivo && !tiendaActiva && !pantallaTitulo && !menuInicio) {
                ge.cambiarEstadoActual(1);
            } else if (tiendaActiva && !juegoActivo && !inventarioActivo && !pantallaTitulo && !menuInicio) {
                ge.cambiarEstadoActual(2);
            } else if (pantallaTitulo && !juegoActivo && !inventarioActivo && !tiendaActiva && !menuInicio) {
                ge.cambiarEstadoActual(3);
            } else if (juegoActivo && !pantallaTitulo && !inventarioActivo && !tiendaActiva && !menuInicio) {
                ge.cambiarEstadoActual(0);
            } else if (menuInicio && !pantallaTitulo && !inventarioActivo && !tiendaActiva && !juegoActivo) {
                ge.cambiarEstadoActual(4);
            }
            ge.actualizar();
            sd.actualizar();
        }

    }

    public void pausarJuego() {
        enPausa = true; // Establece el juego en pausa
    }

    public void reanudarJuego() {
        enPausa = false; // Quita la pausa
    }


    // Método para dibujar el estado del juego
    private void dibujar() {
        sd.dibujar(ge);
    }

    // Método para obtener los FPS del juego
    public static int getFps() {
        return fps;
    }

    // Método para obtener los APS del juego
    public static int getAps() {
        return aps;
    }


}
