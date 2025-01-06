package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.herramientas.EscaladorElementos;
import principal.inventario.Objeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armaduras.ProteccionBaja;
import principal.inventario.armaduras.ProteccionMedia;

import java.awt.*;

public class MenuArmaduras extends SeccionMenuEquipable {
    private Rectangle contenedorArmadura;
    public MenuArmaduras(String habilidades, Rectangle etiquetaHabilidades, EstructuraMenuEquipable estructuraMenu, int numeroSeccion, Rectangle contenedorArmadura) {
        super(habilidades, etiquetaHabilidades, estructuraMenu, numeroSeccion);
        this.contenedorArmadura = contenedorArmadura;
    }

    @Override
    public void actualizar() {
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
        actualizarSeleccionArmaduras();

    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionMedia) {
                super.dibujarObjetoPosicionMenu(g, objeto);
            }
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionMedia) {
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

            for (Objeto objeto : ElementosPrincipales.inventario.objetos) {
                if (objeto instanceof ProteccionMedia) {
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
                    if (objeto instanceof ProteccionMedia) {
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

    private void actualizarSeleccionArmaduras() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado != null) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedorArmadura))) {
                seleccionArmaduras();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                seleccionArmaduras();
            }
        }

    }

    private void seleccionArmaduras() {
        if (objetoSeleccionado instanceof ProteccionMedia) {
            Objeto armadura = ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(armadura);
            ElementosPrincipales.jugador.getAlmacenEquipo().setArmaduraMedia((Armadura) objetoSeleccionado);
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }

    }
}
