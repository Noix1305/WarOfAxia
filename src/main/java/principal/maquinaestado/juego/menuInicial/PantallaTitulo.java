/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.juego.menuInicial;

import java.awt.*;
import java.awt.image.BufferedImage;

import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.herramientas.DibujoDebug;
import principal.maquinaestado.EstadoJuego;
import principal.maquinaestado.juego.GestorJuego;
import principal.maquinaestado.menujuego.EstructuraMenu;
import principal.maquinaestado.menujuego.MenuInventario;
import principal.maquinaestado.menujuego.SeccionMenu;
import principal.sprites.HojaSprites;

/**
 * @author GAMER ARRAX
 */
public class PantallaTitulo implements EstadoJuego {

    private static boolean esperaEnter = true;

    private final BufferedImage inicio;
    private final HojaSprites hojaInicio;
    private final String musicaInicio = "Final-Fantasy-Main-Theme-_Orchestral_";
    public static boolean musicaIniciada = false;
    private long tiempoUltimaPulsacion = 0;

    public PantallaTitulo() {
        ElementosPrincipales.reproductor.musica.repetir(0.8f);
        this.hojaInicio = new HojaSprites("/fondos/Inicio4.png", 640, 360, true);
        this.inicio = hojaInicio.getSprites(0).imagen;
    }

//    private void cambiarImagen() {
//        // Determinar qué imagen mostrar basándose en la imagen actual
//        if (start == s1.getSprites(0).imagen()) {
//            start = s2.getSprites(0).imagen();
//        }
//        else {
//            start = s1.getSprites(0).imagen();
//        }
//    }

    public void actualizar() {
        if (GestorPrincipal.pantallaTitulo && !musicaIniciada) {
            if (!ElementosPrincipales.reproductor.musica.getFilename().toUpperCase().equalsIgnoreCase(musicaInicio)) {
                ElementosPrincipales.reproductor.musica.cambiarArchivo(musicaInicio);
                ElementosPrincipales.reproductor.musica.repetir(0.8f);
                musicaIniciada = true;
            }
        }
        long tiempoActual = System.currentTimeMillis(); // Tiempo actual en milisegundos

        if (GestorPrincipal.pantallaTitulo && tiempoActual - tiempoUltimaPulsacion > 300) {
            if (GestorControles.teclado.enter.puedeProcesarse()) {
                GestorControles.teclado.enter.marcarComoProcesada();
                GestorControles.teclado.enter.teclaLiberada();
                System.out.println("Enter pulsada en pantalla título");
                tiempoUltimaPulsacion = tiempoActual;
                GestorPrincipal.menuInicio = true;
                GestorPrincipal.pantallaTitulo = false;
                 // Actualiza el tiempo
            }
        }
    }

    public void dibujar(Graphics2D g) {
        DibujoDebug.dibujarImagen(g, this.inicio, 0, 0);
//        if (esperaEnter) {
//            // Dibujar algún indicador para indicar al usuario que presione Enter
//        }
    }

    public static void setEsperaEnter(boolean espera) {
        esperaEnter = espera;
    }
}
