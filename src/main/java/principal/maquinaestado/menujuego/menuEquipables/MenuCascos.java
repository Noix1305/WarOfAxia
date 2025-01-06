package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.inventario.Objeto;
import principal.inventario.armaduras.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

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
        dibujarTooltip(g, GestorPrincipal.sd);
    }

    private void dibujarTooltip(Graphics g, SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
            for (Objeto objeto : obtenerCascos()) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                    dibujarTooltipCasco(g, GestorPrincipal.sd, objeto);
                }
            }
        }
    }

    private void dibujarTooltipCasco(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu(), Color.DARK_GRAY);
        ProteccionAlta casco = (ProteccionAlta) objeto;
        GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nDEF FISICA: "
                + casco.getDefensaF() + "\nDEF MAGICA: " + casco.getDefensaM() + "\nPESO: " + objeto.getPeso() + " oz.");
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : obtenerCascos()) {
            super.dibujarObjetoPosicionMenu(g, objeto);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : obtenerCascos()) {
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

            for (Objeto objeto : obtenerCascos()) {

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
                for (Objeto objeto : obtenerCascos()) {
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

        Objeto casco = ElementosPrincipales.jugador.getAlmacenEquipo().getCasco();
        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(casco);
        ElementosPrincipales.jugador.getAlmacenEquipo().setCasco((Armadura) objetoSeleccionado);
        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
        objetoSeleccionado = null;

    }

    private ArrayList<Objeto> obtenerCascos() {
        ArrayList<Objeto> listaCascos = new ArrayList<>();
        for (Objeto objeto : ElementosPrincipales.inventario.objetos) {
            if (objeto instanceof ProteccionAlta) {
                listaCascos.add(objeto);
            }
        }
        return listaCascos;
    }
}
