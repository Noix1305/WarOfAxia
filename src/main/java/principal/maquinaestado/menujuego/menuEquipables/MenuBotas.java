package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.herramientas.EscaladorElementos;
import principal.inventario.Objeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionBaja;

import java.awt.*;


public class MenuBotas extends SeccionMenuEquipable {
    private Rectangle contenedorBotas;
    public MenuBotas(String crecimiento, Rectangle etiquetaCrecimiento2, EstructuraMenuEquipable estructuraMenu, int numeroSeccion, Rectangle contenedorBotas) {
        super(crecimiento, etiquetaCrecimiento2, estructuraMenu, numeroSeccion);
        this.contenedorBotas = contenedorBotas;
    }

    @Override
    public void actualizar() {
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
        actualizarSeleccionBotas();
    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionBaja) {
                super.dibujarObjetoPosicionMenu(g, objeto);
            }
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionBaja) {
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
                if (objeto instanceof ProteccionBaja) {
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
                    if (objeto instanceof ProteccionBaja) {
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

    private void actualizarSeleccionBotas() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado != null) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedorBotas))) {
                seleccionBotas();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                seleccionBotas();
            }
        }

    }

    private void seleccionBotas() {
        if (objetoSeleccionado instanceof ProteccionBaja) {
            Objeto bota = ElementosPrincipales.jugador.getAlmacenEquipo().getBota();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(bota);
            ElementosPrincipales.jugador.getAlmacenEquipo().setBota((Armadura) objetoSeleccionado);
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }

    }
}
