package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;
import principal.inventario.armas.Arma;
import principal.inventario.armas.ArmaUnaMano;

import java.awt.*;
import java.util.ArrayList;

public class MenuArmaUnaMano extends SeccionMenuEquipable {

    private Rectangle contenedorArma;

    public MenuArmaUnaMano(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenuEquipable estructuraMenu,
                           int numeroSeccion, Rectangle contenedorArma) {
        super(nombreSeccion, etiquetaMenu, estructuraMenu, numeroSeccion);
        this.contenedorArma = contenedorArma;

    }

    @Override
    public void actualizar() {
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
        seleccionArma1();
    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
    }

    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objetoActual : ElementosPrincipales.inventario.getUnaMano()) {
            super.dibujarObjetoPosicionMenu(g, objetoActual);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (ArmaUnaMano arma : ElementosPrincipales.inventario.getUnaMano()) {
            super.actualizarPosicionMenuObjeto(arma, contador);
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

            for (Objeto objeto : ElementosPrincipales.inventario.getUnaMano()) {

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
                if (ElementosPrincipales.inventario.getUnaMano().isEmpty()) {
                    return;
                }
                for (Objeto objeto : ElementosPrincipales.inventario.getUnaMano()) {
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

    public void seleccionArma1() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado instanceof Arma) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedorArma))) {
                seleccionarArma();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                    seleccionarArma();
                GestorPrincipal.sd.getRaton().setDobleClick(false);
            }
        }
    }

    private void seleccionarArma() {
        if (!sonMismasArmas((Arma) objetoSeleccionado, ElementosPrincipales.jugador.getAlmacenEquipo().getArma1())) {
            Objeto arma1 = ElementosPrincipales.jugador.getAlmacenEquipo().getArma1();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(arma1);
            ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma1((Arma) objetoSeleccionado);

            // Actualiza la lista de equipo actual
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(objetoSeleccionado);
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }
    }

    private boolean sonMismasArmas(Arma arma1, Arma arma2) {
        return arma1 != null && arma1.equals(arma2);
    }


}
