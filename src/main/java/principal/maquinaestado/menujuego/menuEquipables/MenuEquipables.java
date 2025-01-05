package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.entes.jugador.AlmacenEquipo;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;
import principal.inventario.armaduras.*;
import principal.inventario.armas.Arma;
import principal.inventario.armas.ArmaDosManos;
import principal.inventario.armas.ArmaUnaMano;
import principal.inventario.joyas.Accesorio;
import principal.inventario.joyas.Anillo;
import principal.inventario.joyas.Collar;
import principal.inventario.joyas.Joya;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class MenuEquipables {
    private int x = 156;
    private int y = 44;
    private int width = 172;
    private int height = (Constantes.ALTO_JUEGO - 36) - 8 * 2;
    private Rectangle margen = new Rectangle(x, y, width, height);
    private Rectangle titularPanelEquipo = new Rectangle(x, y, width, 24);
    private Rectangle subPanel = new Rectangle(x - 105, y + 58, 104, 200);
    public static Objeto objetoSeleccionado = null;
    private ArrayList<Rectangle> contenedores;

    public void dibujar(Graphics g) {
        dibujarPanel(g, margen, titularPanelEquipo, "EQUIPABLES");


        // Dibujar el borde
        Color colorBorde = Color.DARK_GRAY;
        DibujoDebug.dibujarRectanguloContorno(g, subPanel, colorBorde);

        // Dibujar un rectángulo interno más pequeño (relleno)
        Rectangle rectInterno = new Rectangle(
                subPanel.x,
                subPanel.y,
                subPanel.width,
                subPanel.height
        );


        Color colorRelleno = Color.WHITE;
        DibujoDebug.dibujarRectanguloRelleno(g, rectInterno, colorRelleno);
        dibujarElementosEquipables(g);
    }

    public MenuEquipables(ArrayList<Rectangle> contenedores) {
        this.contenedores = contenedores;
    }

    private void dibujarPanel(final Graphics g, final Rectangle panel, final Rectangle titularPanel,
                              final String nombrePanel) {

    }

    private void dibujarObjetosEquipables(Graphics g) {
        int z = margen.x + 2;
        for (int i = 0; i < 7; i++) {
            Rectangle rectangulo = new Rectangle(z, margen.y + 14, 19, 10);
            z += 23;
        }
        BufferedImage hojaEtiquetas = CargadorRecursos.cargarImagenCompatibleOpaca("/icons/EtiquetasEquipables.png");
        DibujoDebug.dibujarImagen(g, hojaEtiquetas, margen.x, margen.y + 14);
        if (!ElementosPrincipales.inventario.getEquipo().isEmpty()) {

            switch (1) {
                case 0:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmas()) {

                        if (objetoActual instanceof ArmaUnaMano) {
                            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                                    objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

                            DibujoDebug.dibujarRectanguloRelleno(g,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                                    12, 8, Color.black);

                            g.setColor(Color.white);

                            String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                                    : String.valueOf(objetoActual.getCantidad());

                            DibujoDebug.dibujarString(g, texto,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                                            - MedidorString.medirAnchoPixeles(g, texto),
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
                        }
                    }
                    break;

                case 1:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmas()) {
                        if (objetoActual instanceof ArmaDosManos) {
                            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                                    objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

                            DibujoDebug.dibujarRectanguloRelleno(g,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                                    12, 8, Color.black);

                            g.setColor(Color.white);

                            String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                                    : String.valueOf(objetoActual.getCantidad());

                            DibujoDebug.dibujarString(g, texto,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                                            - MedidorString.medirAnchoPixeles(g, texto),
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
                        }
                    }
                    break;

                case 2:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionMedia) {

                            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                                    objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

                            DibujoDebug.dibujarRectanguloRelleno(g,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                                    12, 8, Color.black);

                            g.setColor(Color.white);

                            String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                                    : String.valueOf(objetoActual.getCantidad());

                            DibujoDebug.dibujarString(g, texto,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                                            - MedidorString.medirAnchoPixeles(g, texto),
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
                        }
                    }
                    break;

                case 3:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionAlta) {

                            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                                    objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

                            DibujoDebug.dibujarRectanguloRelleno(g,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                                    12, 8, Color.black);

                            g.setColor(Color.white);

                            String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                                    : String.valueOf(objetoActual.getCantidad());

                            DibujoDebug.dibujarString(g, texto,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                                            - MedidorString.medirAnchoPixeles(g, texto),
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
                        }
                    }
                    break;

                case 4:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionLateral) {

                            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                                    objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

                            DibujoDebug.dibujarRectanguloRelleno(g,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                                    12, 8, Color.black);

                            g.setColor(Color.white);

                            String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                                    : String.valueOf(objetoActual.getCantidad());

                            DibujoDebug.dibujarString(g, texto,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                                            - MedidorString.medirAnchoPixeles(g, texto),
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
                        }
                    }
                    break;

                case 5:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionBaja) {

                            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                                    objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

                            DibujoDebug.dibujarRectanguloRelleno(g,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                                    12, 8, Color.black);

                            g.setColor(Color.white);

                            String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                                    : String.valueOf(objetoActual.getCantidad());

                            DibujoDebug.dibujarString(g, texto,
                                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                                            - MedidorString.medirAnchoPixeles(g, texto),
                                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
                        }
                    }
                    break;

                case 6:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getJoyas()) {
                        DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                                objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

                        DibujoDebug.dibujarRectanguloRelleno(g,
                                objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                                objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                                12, 8, Color.black);

                        g.setColor(Color.white);

                        String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                                : String.valueOf(objetoActual.getCantidad());

                        DibujoDebug.dibujarString(g, texto,
                                objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                                        - MedidorString.medirAnchoPixeles(g, texto),
                                objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
                    }
                    break;
            }

            if (objetoSeleccionado != null) {
                DibujoDebug.dibujarImagen(g, objetoSeleccionado.getSprite().getImagen(),
                        new Point(objetoSeleccionado.getPosicionFlotante().x,
                                objetoSeleccionado.getPosicionFlotante().y));
            }

        }
    }


    public void actualizarObjetoSeleccionado() {
    }

    private boolean sonMismasArmas(Arma arma1, Arma arma2) {
        return arma1 != null && arma1.equals(arma2);
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

    public void actualizarSeleccionArma2(Rectangle panelEquipo) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelEquipo))
                && objetoSeleccionado instanceof Arma
                && GestorPrincipal.sd.getRaton().isClick()
                && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(1)))) {

            if (!sonMismasArmas((Arma) objetoSeleccionado, ElementosPrincipales.jugador.getAlmacenEquipo().getArma1())) {
                // Verifica si el contenedor de arma2 tiene un arma a distancia
                if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma2() instanceof ArmaDosManos
                        || ElementosPrincipales.jugador.getAlmacenEquipo().getArma2() == null) {
                    // Remueve el arma a distancia del contenedor 2 y de la lista de equipo actual
                    Arma armaEnContenedor2 = ElementosPrincipales.jugador.getAlmacenEquipo().getArma2();
                    ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma2(null);
                    ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(armaEnContenedor2);
                }
                Objeto arma2 = (Objeto) ElementosPrincipales.jugador.getAlmacenEquipo().getArma1();
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(arma2);
                ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma1((Arma) objetoSeleccionado);

                // Actualiza la lista de equipo actual
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
                objetoSeleccionado = null;
            }

        }
    }

    public void actualizarSeleccionArmadura(Rectangle contenedor, Rectangle panelEquipo) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelEquipo))
                && objetoSeleccionado != null
                && GestorPrincipal.sd.getRaton().isClick()
                && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedor))) {

            if (objetoSeleccionado instanceof ProteccionMedia) {
                Objeto armadura = (Objeto) ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia();
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(armadura);
                ElementosPrincipales.jugador.getAlmacenEquipo().setArmaduraMedia((Armadura) objetoSeleccionado);
            } else if (objetoSeleccionado instanceof ProteccionAlta) {
                Objeto casco = (Objeto) ElementosPrincipales.jugador.getAlmacenEquipo().getCasco();
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(casco);
                ElementosPrincipales.jugador.getAlmacenEquipo().setCasco((Armadura) objetoSeleccionado);
            } else if (objetoSeleccionado instanceof ProteccionLateral) {
                Objeto guantes = (Objeto) ElementosPrincipales.jugador.getAlmacenEquipo().getGuante();
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(guantes);
                ElementosPrincipales.jugador.getAlmacenEquipo().setGuante((Armadura) objetoSeleccionado);
            } else if (objetoSeleccionado instanceof ProteccionBaja) {
                Objeto botas = (Objeto) ElementosPrincipales.jugador.getAlmacenEquipo().getBota();
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(botas);
                ElementosPrincipales.jugador.getAlmacenEquipo().setBota((Armadura) objetoSeleccionado);
            }

            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }
    }

    public void actualizarSeleccionCollar(Rectangle panelEquipo) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelEquipo))
                && (objetoSeleccionado instanceof Collar)
                && GestorPrincipal.sd.getRaton().isClick()
                && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(9)))) {

            Objeto collar = (Objeto) ElementosPrincipales.jugador.getAlmacenEquipo().getCollar();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(collar);

            ElementosPrincipales.jugador.getAlmacenEquipo().setCollar((Joya) objetoSeleccionado);
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }
    }

    public void actualizarSeleccionAccesorio(Rectangle panelEquipo) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelEquipo))
                && (objetoSeleccionado instanceof Accesorio)
                && GestorPrincipal.sd.getRaton().isClick()
                && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(8)))) {

            Objeto accesorio = ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(accesorio);
            ElementosPrincipales.jugador.getAlmacenEquipo().setAccesorio((Joya) objetoSeleccionado);

            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }
    }

    public void actualizarSeleccionAnillo(Rectangle panelEquipo) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelEquipo))
                && (objetoSeleccionado instanceof Anillo)
                && GestorPrincipal.sd.getRaton().isClick()
                && (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(6)))
                || posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(7))))) {

            AlmacenEquipo ae = ElementosPrincipales.jugador.getAlmacenEquipo();

            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(6)))) {
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
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(7)))) {
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
        }
    }

    public void removerObjetoSeleccionado(Rectangle panelEquipo) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelEquipo)) && objetoSeleccionado == null) {

            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(0)))
                    && GestorPrincipal.sd.getRaton().isClick2()) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto armaEquipada = iterator.next();
                    if (armaEquipada.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getArma1().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma1(null);

            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(2)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia()
                    != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto armaduraMedia = iterator.next();
                    if (armaduraMedia.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setArmaduraMedia(null);
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(3)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getCasco() != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto casco = iterator.next();
                    if (casco.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getCasco().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setCasco(null);
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(4)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getGuante() != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto guantes = iterator.next();
                    if (guantes.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getGuante().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setGuante(null);
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(5)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getBota() != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto bota = iterator.next();
                    if (bota.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getBota().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setBota(null);
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(9)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getCollar() != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto collar = iterator.next();
                    if (collar.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getCollar().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setCollar(null);
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(8)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio() != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto accesorio = iterator.next();
                    if (accesorio.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setAccesorio(null);
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(6)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo1() != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto anillo1 = iterator.next();
                    if (anillo1.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo1().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo1(null);
            } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(7)))
                    && GestorPrincipal.sd.getRaton().isClick2() && ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo2() != null) {
                for (Iterator<Objeto> iterator = ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual().iterator(); iterator.hasNext(); ) {
                    Objeto anillo2 = iterator.next();
                    if (anillo2.getId() == ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo2().getId()) {
                        iterator.remove();
                        break;
                    }
                }
                ElementosPrincipales.jugador.getAlmacenEquipo().setAnillo2(null);

            }
        }
    }


    public void dibujarElementosEquipables(Graphics g) {
        int z = margen.x + 2;

        for (int i = 0; i < 7; i++) {
            Rectangle rectangulo = new Rectangle(z, margen.y + 14, 19, 10);

        }
        ArrayList<Objeto> listaObjetos = new ArrayList<>();
        if (!ElementosPrincipales.inventario.getEquipo().isEmpty()) {
            int etiquetaEquipo = 2;

            switch (etiquetaEquipo) {
                case 0:
                    listaObjetos = ElementosPrincipales.inventario.getUnaMano(0);
                    break;

                case 1:
                    listaObjetos = ElementosPrincipales.inventario.getDosManos(0);
                    break;

                case 2:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionMedia) {
                            listaObjetos.add(objetoActual);
                        }
                    }
                    break;

                case 3:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionAlta) {
                            listaObjetos.add(objetoActual);
                        }
                    }
                    break;

                case 4:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionLateral) {
                            listaObjetos.add(objetoActual);
                        }
                    }
                    break;

                case 5:
                    for (Objeto objetoActual : ElementosPrincipales.inventario.getArmaduras()) {
                        if (objetoActual instanceof ProteccionBaja) {
                            listaObjetos.add(objetoActual);
                        }
                    }
                    break;

                case 6:
                    listaObjetos = ElementosPrincipales.inventario.getJoyas(0);
                    break;
            }

            if (objetoSeleccionado != null) {
                System.out.println("Dibujando objetoSeleccionado");
                DibujoDebug.dibujarImagen(g, objetoSeleccionado.getSprite().getImagen(),
                        new Point(objetoSeleccionado.getPosicionFlotante().x,
                                objetoSeleccionado.getPosicionFlotante().y));
            }

        }

        for (Objeto objetoActual : listaObjetos) {
            DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(),
                    objetoActual.getPosicionMenu().x, objetoActual.getPosicionMenu().y);

            DibujoDebug.dibujarRectanguloRelleno(g,
                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width - 12,
                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 8,
                    12, 8, Color.black);

            g.setColor(Color.white);

            String texto = (objetoActual.getCantidad() < 10) ? "0" + objetoActual.getCantidad()
                    : String.valueOf(objetoActual.getCantidad());

            DibujoDebug.dibujarString(g, texto,
                    objetoActual.getPosicionMenu().x + objetoActual.getPosicionMenu().width
                            - MedidorString.medirAnchoPixeles(g, texto),
                    objetoActual.getPosicionMenu().y + objetoActual.getPosicionMenu().height - 1);
        }
    }

    public void seleccionArma1(Rectangle panelEquipo) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelEquipo))
                && objetoSeleccionado instanceof Arma
                && GestorPrincipal.sd.getRaton().isClick()
                && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedores.get(0)))) {

            if (!sonMismasArmas((Arma) objetoSeleccionado, ElementosPrincipales.jugador.getAlmacenEquipo().getArma1())) {
                Objeto arma1 = (Objeto) ElementosPrincipales.jugador.getAlmacenEquipo().getArma1();
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(arma1);
                ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma1((Arma) objetoSeleccionado);

                // Actualiza la lista de equipo actual
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(objetoSeleccionado);
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
                objetoSeleccionado = null;
            }

        }
    }
}
