/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.menujuego;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.maquinaestado.EstadoJuego;

/**
 * @author GAMER ARRAX
 */
public class GestorMenu implements EstadoJuego {


    private final SeccionMenu[] secciones;
    private SeccionMenu seccionActual;

    private final EstructuraMenu estructuraMenu;

    public GestorMenu() {

        estructuraMenu = new EstructuraMenu();

        secciones = new SeccionMenu[5]; // Ajusta la longitud del arreglo según la cantidad de secciones

        final Rectangle etiquetaInventario = new Rectangle(estructuraMenu.BANNER_LATERAL.x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS, estructuraMenu.BANNER_LATERAL.y
                + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS, estructuraMenu.ANCHO_ETIQUETAS,
                estructuraMenu.ALTO_ETIQUETAS);

        secciones[0] = new MenuInventario("INVENTARIO", etiquetaInventario, estructuraMenu);

        final Rectangle etiquetaEquipo = new Rectangle(estructuraMenu.BANNER_LATERAL.x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaInventario.y + etiquetaInventario.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[1] = new MenuEquipo("EQUIPO", etiquetaEquipo, estructuraMenu);

        // Nuevas etiquetas
        final Rectangle etiquetaBestiario = new Rectangle(estructuraMenu.BANNER_LATERAL.x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaEquipo.y + etiquetaEquipo.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[2] = new MenuBestiario("BESTIARIO", etiquetaBestiario, estructuraMenu);

        final Rectangle etiquetaHabilidades = new Rectangle(estructuraMenu.BANNER_LATERAL.x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaBestiario.y + etiquetaBestiario.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[3] = new MenuHabilidades("HABILIDADES", etiquetaHabilidades, estructuraMenu);

        final Rectangle etiquetaCrecimiento = new Rectangle(estructuraMenu.BANNER_LATERAL.x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaHabilidades.y + etiquetaHabilidades.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[4] = new MenuCrecimiento("CRECIMIENTO", etiquetaCrecimiento, estructuraMenu);

        seccionActual = secciones[0];
    }

    @Override
    public void actualizar() {
        // Determinar la sección activa al hacer clic
        for (SeccionMenu seccion : secciones) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && GestorPrincipal.sd.getRaton().getPosicionRectangle().intersects(seccion.getEtiquetaMenuEscalada())) {
                this.seccionActual = seccion;
            }
        }

        // Si la sección activa es MenuEquipo, ajusta las etiquetas de las demás secciones
        if (seccionActual instanceof MenuEquipo) {
            for (SeccionMenu seccion : secciones) {
                if (!(seccion instanceof MenuEquipo) && !(seccion instanceof MenuInventario)) {
                    seccion.etiquetaMenu.width = 30; // Cambia el ancho de las etiquetas para secciones que no sean Equipo e Inventario
                }
            }
        } else {
            // Si la sección activa NO es MenuEquipo, restaura el ancho de todas las etiquetas
            for (SeccionMenu seccion : secciones) {
                seccion.etiquetaMenu.width = 100; // Ancho predeterminado para todas las etiquetas
            }
        }

        // Llama al método `actualizar` de la sección activa
        if (seccionActual != null) {
            seccionActual.actualizar();
        }
    }


    @Override
    public void dibujar(final Graphics2D g) {
        estructuraMenu.dibujar(g);

        for (SeccionMenu seccionActual : secciones) {

            if (this.seccionActual == seccionActual) {
                if (GestorPrincipal.sd.getRaton().getPosicionRectangle().intersects(seccionActual.getEtiquetaMenuEscalada())) {
                    seccionActual.dibujarEtiquetaActivaResaltada(g);
                } else {
                    seccionActual.dibujarEtiquetaActiva(g);
                }
            } else {
                if (GestorPrincipal.sd.getRaton().getPosicionRectangle().intersects(seccionActual.getEtiquetaMenuEscalada())) {
                    seccionActual.dibujarEtiquetaInactResaltada(g);
                } else {
                    seccionActual.dibujarEtiquetaInactiva(g);

                }

            }
        }
        seccionActual.dibujar(g, GestorPrincipal.sd);
    }

}
