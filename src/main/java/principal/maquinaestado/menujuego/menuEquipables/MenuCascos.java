package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.EscaladorElementos;
import principal.inventario.Objeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionBaja;
import principal.inventario.armaduras.ProteccionMedia;

import java.awt.*;

public class MenuCascos extends SeccionMenuEquipable {
    private final Rectangle contenedorCasco;

    public MenuCascos(String bestiario, Rectangle etiquetaBestiario, EstructuraMenuEquipable estructuraMenu,
                      int numeroSeccion, Rectangle contenedorCasco) {
        super(bestiario, etiquetaBestiario, estructuraMenu, numeroSeccion);
        this.contenedorCasco = contenedorCasco;
    }

    @Override
    public void actualizar() {
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
        actualizarSeleccionCasco();
    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionAlta) {
                super.dibujarObjetoPosicionMenu(g, objeto);
            }
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionAlta) {
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
                if (objeto instanceof ProteccionAlta) {
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
    protected void actualizarSeleccionRaton() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado == null) {
            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
                if (ElementosPrincipales.inventario.getArmaduras().isEmpty()) {
                    return;
                }
                for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
                    if (objeto instanceof ProteccionAlta) {
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

    private void actualizarSeleccionCasco() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado != null) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedorCasco))) {
                seleccionCasco();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                seleccionCasco();
            }
        }

    }

    private void seleccionCasco() {
        if (objetoSeleccionado instanceof ProteccionAlta) {
            Objeto casco = ElementosPrincipales.jugador.getAlmacenEquipo().getCasco();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(casco);
            ElementosPrincipales.jugador.getAlmacenEquipo().setCasco((Armadura) objetoSeleccionado);
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }

    }
}
