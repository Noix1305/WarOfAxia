package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.herramientas.EscaladorElementos;
import principal.inventario.Objeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armaduras.ProteccionLateral;

import java.awt.*;

public class MenuGuantes extends SeccionMenuEquipable {
    private Rectangle contenedorGuantes;
    public MenuGuantes(String crecimiento, Rectangle etiquetaCrecimiento, EstructuraMenuEquipable estructuraMenu,
                       int numeroSeccion, Rectangle contenedorGuantes) {
        super(crecimiento, etiquetaCrecimiento, estructuraMenu,numeroSeccion);
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
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionLateral) {
                super.dibujarObjetoPosicionMenu(g, objeto);
            }
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionLateral) {
                super.actualizarPosicionMenuObjeto(objeto, contador);
                contador++;
            }
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

            for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
                if (objeto instanceof ProteccionLateral) {
                    if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton
                            .intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                        objetoSeleccionado = objeto;
                    }
                }
            }
            Point pr = EscaladorElementos.escalarAbajo(GestorPrincipal.sd.getRaton().getPosicion());
            objetoSeleccionado.setPosicionFlotante(
                    new Rectangle(pr.x, pr.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));
        }
    }

    @Override
    protected void actualizarSeleccionRaton(){
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado == null) {
            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
                if (ElementosPrincipales.inventario.getArmaduras().isEmpty()) {
                    return;
                }
                for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
                    if (objeto instanceof ProteccionLateral) {
                        if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton
                                .intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                            objetoSeleccionado = objeto;
                        }
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
}
