package principal.maquinaestado.menujuego.menuEquipables;

import principal.GestorPrincipal;
import principal.herramientas.DibujoDebug;
import principal.maquinaestado.menujuego.*;

import java.awt.*;
import java.util.ArrayList;

public class GestorMenuEquipables {


    private final SeccionMenuEquipable[] secciones;
    private SeccionMenuEquipable seccionActual;
    private final EstructuraMenuEquipable estructuraMenu;
    private ArrayList<Rectangle> contenedores;


    public GestorMenuEquipables(ArrayList<Rectangle> contenedores) {
        secciones = new SeccionMenuEquipable[7];
        estructuraMenu = new EstructuraMenuEquipable();
        this.contenedores = contenedores;

        final Rectangle etiquetaInventario = new Rectangle(estructuraMenu.getSubPanel().x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS, estructuraMenu.getSubPanel().y
                + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS, estructuraMenu.ANCHO_ETIQUETAS,
                estructuraMenu.ALTO_ETIQUETAS);

        secciones[0] = new MenuArmaUnaMano("ARMA UNA MANO", etiquetaInventario, estructuraMenu, 1, contenedores.get(0));

        final Rectangle etiquetaEquipo = new Rectangle(estructuraMenu.getSubPanel().x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaInventario.y + etiquetaInventario.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[1] = new MenuArmaDosManos("ARMA DOS MANOS", etiquetaEquipo, estructuraMenu, 2, contenedores.get(1));

        // Nuevas etiquetas
        final Rectangle etiquetaBestiario = new Rectangle(estructuraMenu.getSubPanel().x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaEquipo.y + etiquetaEquipo.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[2] = new MenuCascos("CASCOS", etiquetaBestiario, estructuraMenu, 3, contenedores.get(3));

        final Rectangle etiquetaHabilidades = new Rectangle(estructuraMenu.getSubPanel().x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaBestiario.y + etiquetaBestiario.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[3] = new MenuArmaduras("ARMADURAS", etiquetaHabilidades, estructuraMenu, 4, contenedores.get(2));

        final Rectangle etiquetaCrecimiento = new Rectangle(estructuraMenu.getSubPanel().x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaHabilidades.y + etiquetaHabilidades.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[4] = new MenuGuantes("GUANTES", etiquetaCrecimiento, estructuraMenu, 5, contenedores.get(4));

        final Rectangle etiquetaCrecimiento2 = new Rectangle(estructuraMenu.getSubPanel().x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaCrecimiento.y + etiquetaCrecimiento.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[5] = new MenuBotas("BOTAS", etiquetaCrecimiento2, estructuraMenu, 6, contenedores.get(5));

        final Rectangle etiquetaCrecimiento3 = new Rectangle(estructuraMenu.getSubPanel().x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS,
                etiquetaCrecimiento2.y + etiquetaCrecimiento2.height + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS,
                estructuraMenu.ANCHO_ETIQUETAS, estructuraMenu.ALTO_ETIQUETAS);

        secciones[6] = new MenuJoyas("JOYAS", etiquetaCrecimiento3, estructuraMenu, 7, contenedoresJoyas());

        seccionActual = secciones[0];

    }

    //{}
    private ArrayList<Rectangle> contenedoresJoyas() {
        ArrayList<Rectangle> contenedoresJoyas = new ArrayList<>();
        for (int i = 6; i < contenedores.size(); i++) {
            contenedoresJoyas.add(contenedores.get(i));
        }
        return contenedoresJoyas;
    }

    public void actualizar() {
        for (SeccionMenuEquipable seccionesActual : secciones) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && GestorPrincipal.sd.getRaton().getPosicionRectangle().intersects(seccionesActual.getEtiquetaMenuEscalada())) {

                if (seccionesActual instanceof MenuArmaUnaMano seccion) {
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }
                } else if (seccionesActual instanceof MenuArmaDosManos seccion) {
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }
                } else if (seccionesActual instanceof MenuCascos seccion) {
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }
                } else if (seccionesActual instanceof MenuArmaduras seccion) {
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }
                } else if (seccionesActual instanceof MenuBotas seccion) {
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }
                } else if (seccionesActual instanceof MenuGuantes seccion) {
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }
                } else if (seccionesActual instanceof MenuJoyas seccion) {
                    if (seccion.getObjetoSeleccionado() != null) {
                        seccion.eliminarObjetoSeleccionado();
                    }
                }
                seccionActual = seccionesActual;
            }
        }
        seccionActual.actualizar();
    }

    public void dibujar(final Graphics g) {
        estructuraMenu.dibujar(g);

        for (SeccionMenuEquipable seccionActual : secciones) {

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
        seccionActual.dibujar(g);
    }

    public int getNumeroSeccion() {
        return seccionActual.getNumeroSeccion();
    }

    public ArrayList<Rectangle> getContenedores() {
        return contenedores;
    }

    public void setContenedores(ArrayList<Rectangle> contenedores) {
        this.contenedores = contenedores;
    }
}
