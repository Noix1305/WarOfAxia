package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.entes.jugador.AlmacenEquipo;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
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
        dibujarTooltip(g, GestorPrincipal.sd);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : obtenerJoyas()) {
            super.dibujarObjetoPosicionMenu(g, objeto);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    private void dibujarTooltip(Graphics g, SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
            for (Objeto objeto : obtenerJoyas()) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                    dibujarTooltipJoyas(g, GestorPrincipal.sd, objeto);
                }
            }
        }
    }

    private void dibujarTooltipJoyas(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu(), Color.DARK_GRAY);
        String texto = devolverStringJoyas((Joya) objeto);
        GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + texto);
    }


    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : obtenerJoyas()) {
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

            for (Objeto objeto : obtenerJoyas()) {

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
                for (Objeto objeto : obtenerJoyas()) {
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
                GestorPrincipal.sd.getRaton().setDobleClick(false);
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
                GestorPrincipal.sd.getRaton().setDobleClick(false);
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

        if (objetoSeleccionado instanceof Anillo) {
            AlmacenEquipo ae = ElementosPrincipales.jugador.getAlmacenEquipo();

            // Manejo de clic simple
            if (GestorPrincipal.sd.getRaton().isClick() &&
                    (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(2))) ||
                            posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(3))))) {

                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(2)))) {
                    Joya anillo1 = ae.getAnillo1();
                    Joya anillo2 = ae.getAnillo2();

                    if (!sonMismosAnillos((Joya) objetoSeleccionado, anillo1)) {
                        ae.equipoActual.remove(anillo1);
                        ae.setAnillo1((Joya) objetoSeleccionado);

                        if (anillo2 != null && sonMismosAnillos(anillo2, (Joya) objetoSeleccionado)) {
                            ae.equipoActual.remove(anillo2);
                        }
                    }
                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedoresJoyas.get(3)))) {
                    Joya anillo1 = ae.getAnillo1();
                    Joya anillo2 = ae.getAnillo2();

                    if (!sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                        ae.equipoActual.remove(anillo2);
                        ae.setAnillo2((Joya) objetoSeleccionado);

                        if (anillo1 != null && sonMismosAnillos(anillo1, (Joya) objetoSeleccionado)) {
                            ae.equipoActual.remove(anillo1);
                        }
                    }
                }

                // Limpiar objetoSeleccionado en caso de clic en contenedor
                objetoSeleccionado = null;

                // Manejo de doble clic
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                Joya anillo1 = ae.getAnillo1();
                Joya anillo2 = ae.getAnillo2();

                if (anillo1 == null && !sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                    ae.setAnillo1((Joya) objetoSeleccionado);
                } else if (anillo2 == null && !sonMismosAnillos((Joya) objetoSeleccionado, anillo1)) {
                    ae.setAnillo2((Joya) objetoSeleccionado);
                } else if (anillo1 != null && !sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                    ae.equipoActual.remove(anillo1);
                    ae.setAnillo1((Joya) objetoSeleccionado);
                } else if (anillo1 != null && sonMismosAnillos((Joya) objetoSeleccionado, anillo1) &&
                        !sonMismosAnillos((Joya) objetoSeleccionado, anillo2)) {
                    ae.equipoActual.remove(anillo2);
                    ae.setAnillo2((Joya) objetoSeleccionado);
                }

                // Limpiar objetoSeleccionado en caso de doble clic
                objetoSeleccionado = null;
                GestorPrincipal.sd.getRaton().setDobleClick(false);
            }

            // Agregar a equipoActual solo si no existe
            if (!ae.equipoActual.contains(objetoSeleccionado)) {
                ae.equipoActual.add(objetoSeleccionado);
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

    private String devolverStringJoyas(Joya joya) {
        String[] propiedades = {
                (joya.getAtkF() > 0) ? "\nATAQUE: " + joya.getAtkF() : "",
                (joya.getDefensaF() > 0) ? "\nDEF FISICA: " + joya.getDefensaF() : "",
                (joya.getAtkM() > 0) ? "\nMAGIA: " + joya.getAtkM() : "",
                (joya.getDefensaM() > 0) ? "\nDEF MAGICA: " + joya.getDefensaM() : "",
                (joya.getCrit() > 0.0) ? "\nCRITICO: " + joya.getCrit() : "",
                (joya.getEva() > 0.0) ? "\nEVASION: " + joya.getEva() : "",
                (joya.getResF() > 0.0) ? "\nRES FISICA: " + joya.getResF() : "",
                (joya.getResM() > 0.0) ? "\nRES MAGICA: " + joya.getResM() : "",
                (joya.getPeso() > 0) ? "\nPESO: " + joya.getPeso() + " oz." : ""
        };

        return String.join("", propiedades);
    }

    private ArrayList<Joya> obtenerJoyas() {
        ArrayList<Joya> listaJoyas = new ArrayList<>();
        for (Objeto objeto : ElementosPrincipales.inventario.getJoyas()) {
            if (objeto instanceof Joya joya) {
                listaJoyas.add(joya);
            }
        }
        return listaJoyas;
    }
}

