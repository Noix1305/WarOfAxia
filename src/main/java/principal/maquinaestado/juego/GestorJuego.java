/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.juego;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.EfectosVisuales;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.interfaz_usuario.MenuInferior;
import principal.mapas.MapaTiled;
import principal.mapas.Salida;
import principal.maquinaestado.EstadoJuego;

/**
 * @author GAMER ARRAX
 */
public class GestorJuego implements EstadoJuego {

    transient BufferedImage logo;
    MenuInferior menuInferior;
    EfectosVisuales ev;
    public static boolean recargar;
    public static boolean cargarJuego;

    public GestorJuego() {
        menuInferior = new MenuInferior();
        logo = CargadorRecursos.cargarImagenCompatibleTranslucida(Constantes.RUTA_LOGO);
        this.ev = new EfectosVisuales();
        recargar = false;
    }

    @Override
    public void actualizar() {
        revisarZonaSalida();
        ElementosPrincipales.mapa.actualizar();
        ElementosPrincipales.jugador.actualizar();
        recargarJuego();

    }

    public void recargarJuego() {
        if (recargar) {
            System.out.println("Recargar normal");
            ElementosPrincipales.mapa = new MapaTiled("textos/" + ElementosPrincipales.mapa.getSiguienteMapa());

            // Establecer la posición del jugador en el nuevo mapa
            ElementosPrincipales.jugador.getAccionesJugador().setPosicionX(ElementosPrincipales.mapa.getPuntoInicial().x);
            ElementosPrincipales.jugador.getAccionesJugador().setPosicionY(ElementosPrincipales.mapa.getPuntoInicial().y);
            recargar = false;
        }

    }

    public static void cargarMapa(String rutaMapa) {
        System.out.println("Ruta mapa Gestor juego: " + rutaMapa);
        System.out.println("Recargar sobrecargado");
        ElementosPrincipales.mapa = new MapaTiled(rutaMapa);
        // Establecer la posición del jugador en el nuevo mapa
        ElementosPrincipales.jugador.getAccionesJugador().setPosicionX(ElementosPrincipales.mapa.getPuntoInicial().x);
        ElementosPrincipales.jugador.getAccionesJugador().setPosicionY(ElementosPrincipales.mapa.getPuntoInicial().y);
        cargarJuego = false;

    }

    @Override
    public void dibujar(Graphics2D g) {

        ElementosPrincipales.mapa.dibujar(g);
        ElementosPrincipales.jugador.dibujar(g);
        ElementosPrincipales.mapa.dibujar2daCapa(g);
        menuInferior.dibujar(g);
        DibujoDebug.dibujarImagen(g, logo, Constantes.ANCHO_JUEGO - logo.getWidth(), 0);
        if (ElementosPrincipales.jugador.getAnimacionJugador().dibujarHabilidad) {
            ElementosPrincipales.jugador.getAnimacionJugador().dibujarHabilidad(g);
        }
        //g.fillRect((int) ElementosPrincipales.mapa.getZonaSalida().getX(), (int) ElementosPrincipales.mapa.getZonaSalida().getY(), (int) ElementosPrincipales.mapa.getZonaSalida().getWidth(), (int) ElementosPrincipales.mapa.getZonaSalida().getHeight());
    }

    private void revisarZonaSalida() {
        List<Salida> salidas = Salida.getSalidas();

        for (int i = 0; i < salidas.size(); i++) {
            Rectangle zonaSalida = ElementosPrincipales.mapa.zonasSalida.get(i);

            if (ElementosPrincipales.jugador.getAccionesJugador().getArea().intersects(zonaSalida)) {
                Salida salidaActual = salidas.get(i);

                Salida.puntoInicialSiguiente = salidaActual.getPuntoInicioSiguienteMapa();
                ElementosPrincipales.mapa.setSiguienteMapa(salidaActual.getNombreSiguienteMapa());
                GestorPrincipal.sd.cambioMapa = true;
                recargar = true;

                break;  // Salir del bucle una vez que se ha detectado la intersección
            }
        }
    }

}
