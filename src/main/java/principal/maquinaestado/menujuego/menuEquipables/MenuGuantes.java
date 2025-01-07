package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.inventario.Objeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armaduras.ProteccionBaja;
import principal.inventario.armaduras.ProteccionLateral;
import principal.maquinaestado.menujuego.MenuEquipo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MenuGuantes extends SeccionMenuEquipable {
    private Rectangle contenedorGuantes;

    public MenuGuantes(String crecimiento, Rectangle etiquetaCrecimiento, EstructuraMenuEquipable estructuraMenu,
                       int numeroSeccion, Rectangle contenedorGuantes) {
        super(crecimiento, etiquetaCrecimiento, estructuraMenu, numeroSeccion);
        this.contenedorGuantes = contenedorGuantes;
    }

    @Override
    public void actualizar() {
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
        actualizarSeleccionGuantes();
    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
        dibujarTooltip(g, GestorPrincipal.sd);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : obtenerGuantes()) {
            super.dibujarObjetoPosicionMenu(g, objeto);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    private void dibujarTooltip(Graphics g, SuperficieDibujo sd) {
            Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
                for (Objeto objeto : obtenerGuantes()) {
                    if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                        dibujarTooltipGuantes(g, GestorPrincipal.sd, objeto);
                    }
                }
            }

    }

    private void dibujarTooltipGuantes(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu(), Color.DARK_GRAY);
        ProteccionLateral bota = (ProteccionLateral) objeto;
        GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nDEF FISICA: "
                + bota.getDefensaF() + "\nDEF MAGICA: " + bota.getDefensaM() + "\nPESO: " + objeto.getPeso() + " oz.");
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : obtenerGuantes()) {
            super.actualizarPosicionMenuObjeto(objeto, contador);
            contador++;
        }
    }

    @Override
    public void actualizarObjetoSeleccionado() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado != null) {

            if (GestorPrincipal.sd.getRaton().isClick2()) {
                objetoSeleccionado = null;
                return;
            }

            for (Objeto objeto : obtenerGuantes()) {
                if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton
                        .intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                    objetoSeleccionado = objeto;
                }
            }
            Point pr = EscaladorElementos.escalarAbajo(GestorPrincipal.sd.getRaton().getPosicion());
            objetoSeleccionado.setPosicionFlotante(
                    new Rectangle(pr.x, pr.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));
        }
    }

    @Override
    protected void actualizarSeleccionRaton() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado == null) {
            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
                if (ElementosPrincipales.inventario.getArmaduras().isEmpty()) {
                    return;
                }
                for (Objeto objeto : obtenerGuantes()) {
                    if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton
                            .intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                        objetoSeleccionado = objeto;
                    }
                }
            } else {
                objetoSeleccionado = null;
            }
        }
    }

    private void actualizarSeleccionGuantes() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado != null) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedorGuantes))) {
                seleccionGuante();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                seleccionGuante();
            }
        }

    }

    private void seleccionGuante() {
        if (objetoSeleccionado instanceof ProteccionLateral) {
            Objeto guante = ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(guante);
            ElementosPrincipales.jugador.getAlmacenEquipo().setGuante((Armadura) objetoSeleccionado);
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }

    }

    private ArrayList<Objeto> obtenerGuantes() {
        ArrayList<Objeto> listaGuantes = new ArrayList<>();
        for (Objeto objeto : ElementosPrincipales.inventario.objetos) {
            if (objeto instanceof ProteccionLateral) {
                listaGuantes.add(objeto);
            }
        }
        return listaGuantes;
    }
}
