package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.entes.jugador.AlmacenEquipo;
import principal.herramientas.EscaladorElementos;
import principal.inventario.Objeto;
import principal.inventario.joyas.Accesorio;
import principal.inventario.joyas.Anillo;
import principal.inventario.joyas.Collar;
import principal.inventario.joyas.Joya;

import java.awt.*;
import java.util.ArrayList;

public class MenuJoyas extends SeccionMenuEquipable {
    private ArrayList<Rectangle> contenedoresJoyas;

    public MenuJoyas(String crecimiento, Rectangle etiquetaCrecimiento3, EstructuraMenuEquipable estructuraMenu,
                     int numeroSeccion, ArrayList<Rectangle> contenedoresJoyas) {
        super(crecimiento, etiquetaCrecimiento3, estructuraMenu, numeroSeccion);
        this.contenedoresJoyas = contenedoresJoyas;
    }

    @Override
    public void actualizar() {
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
        actualizarSeleccionJoyas();
    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : ElementosPrincipales.inventario.getJoyas()) {
            super.dibujarObjetoPosicionMenu(g, objeto);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getJoyas()) {
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

            for (Objeto objeto : ElementosPrincipales.inventario.getJoyas()) {

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
                if (ElementosPrincipales.inventario.getJoyas().isEmpty()) {
                    return;
                }
                for (Objeto objeto : ElementosPrincipales.inventario.getJoyas()) {
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

    private boolean sonMismosAnillos(Joya anillo1, Joya anillo2) {
        boolean flag = false;
        if (anillo1 != null && anillo2 != null) {
            if (anillo1.getId() == anillo2.getId()) {
                flag = true;
            }
        }
        return flag;
    }

    private void actualizarSeleccionJoyas() {
        actualizarSeleccionCollar();
        actualizarSeleccionAccesorio();
        actualizarSeleccionAnillo();
    }

    private void actualizarSeleccionCollar() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if ((objetoSeleccionado instanceof Collar)) {
            if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton.intersects(EscaladorElementos.
                    escalarRectangleArriba(contenedoresJoyas.get(0)))) {
                seleccionCollar();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                seleccionCollar();
            }
        }
    }

    private void seleccionCollar() {
        Objeto collar = ElementosPrincipales.jugador.getAlmacenEquipo().getCollar();
        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(collar);

        ElementosPrincipales.jugador.getAlmacenEquipo().setCollar((Joya) objetoSeleccionado);
        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
        objetoSeleccionado = null;
    }

    private void actualizarSeleccionAccesorio() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if ((objetoSeleccionado instanceof Accesorio)) {
            if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton.intersects(EscaladorElementos.
                    escalarRectangleArriba(contenedoresJoyas.get(0)))) {
                seleccionAccesorio();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                seleccionAccesorio();
            }
        }
    }

    private void seleccionAccesorio() {
        Objeto accesorio = ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio();
        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(accesorio);
        ElementosPrincipales.jugador.getAlmacenEquipo().setAccesorio((Joya) objetoSeleccionado);

        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
        objetoSeleccionado = null;
    }


    public void actualizarSeleccionAnillo() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if ((objetoSeleccionado instanceof Anillo)) {
            AlmacenEquipo ae = ElementosPrincipales.jugador.getAlmacenEquipo();
            if (GestorPrincipal.sd.getRaton().isClick()
                    && (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(2)))
                    || posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(3))))) {

                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(2)))) {
                    Joya anillo1 = ae.getAnillo1();
                    Joya anillo2 = ae.getAnillo2();

                    if (!sonMismosAnillos((Joya) objetoSeleccionado, anillo1)) {
                        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(anillo1);
                        ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo1((Joya) objetoSeleccionado);

                        if (anillo2 != null && sonMismosAnillos(anillo2, (Joya) objetoSeleccionado)) {
                            ae.setAnillo2(null);
                            ae.equipoActual.remove(anillo2);
                        }
                    }
                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(3)))) {
                    Joya anillo1 = ae.getAnillo1();
                    Joya anillo2 = ae.getAnillo2();

                    if (!sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                        ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(anillo2);
                        ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo2((Joya) objetoSeleccionado);

                        if (anillo1 != null && sonMismosAnillos(anillo1, (Joya) objetoSeleccionado)) {
                            ae.setAnillo1(null);
                            ae.equipoActual.remove(anillo1);
                        }
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
                objetoSeleccionado = null;
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                Joya anillo1 = ae.getAnillo1();
                Joya anillo2 = ae.getAnillo2();
                if (anillo1 == null && !sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                    ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo1((Joya) objetoSeleccionado);
                } else if (anillo2 == null && !sonMismosAnillos((Joya) objetoSeleccionado, anillo1)) {
                    ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo2((Joya) objetoSeleccionado);
                } else if (anillo1 != null && !sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                    ae.equipoActual.remove(anillo1);
                    ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo1((Joya) objetoSeleccionado);
                } else if (anillo1 != null && sonMismosAnillos((Joya) objetoSeleccionado, anillo1)
                        && !sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                    ae.equipoActual.remove(anillo2);
                    ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo2((Joya) objetoSeleccionado);
                }

                GestorPrincipal.sd.getRaton().setDobleClick(false);
            }
        }
    }
}

