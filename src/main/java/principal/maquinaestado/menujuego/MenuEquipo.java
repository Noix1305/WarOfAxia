/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.menujuego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;
import principal.inventario.TipoObjeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionBaja;
import principal.inventario.armaduras.ProteccionLateral;
import principal.inventario.armaduras.ProteccionMedia;
import principal.inventario.armas.Arma;
import principal.inventario.armas.ArmaDosManos;
import principal.inventario.armas.ArmaUnaMano;
import principal.inventario.armas.SinArma;
import principal.inventario.joyas.Joya;
import principal.maquinaestado.menujuego.menuEquipables.GestorMenuEquipables;
import principal.maquinaestado.menujuego.menuEquipables.MenuEquipables;

/**
 * @author GAMER ARRAX
 */
public class MenuEquipo extends SeccionMenu {

    public static boolean mostrarTooltip = true;
    private int etiquetaEquipo;

    private final int x = em.FONDO.x + margenGeneral;
    private final int y = barraPeso.y + barraPeso.height + margenGeneral;
    private final int width = 180;
    private final int height = Constantes.ALTO_JUEGO - barraPeso.y - barraPeso.height - margenGeneral * 2;

    GestorMenuEquipables gestorMenuEquipables;
    private boolean mostrandoEquipables = false;
    final Rectangle panelEquipo = new Rectangle(x + width + margenGeneral,
            y, 146, height);

    final Rectangle panelAtributos = new Rectangle(panelEquipo.x + panelEquipo.width + margenGeneral,
            y, 132, panelEquipo.height);
    final Rectangle titularPanelObjetos = new Rectangle(x, y,
            width, 24);

    final Rectangle titularPanelEquipo = new Rectangle(panelEquipo.x, panelEquipo.y,
            panelEquipo.width, 24);

    final Rectangle titularPanelAtributos = new Rectangle(panelAtributos.x, panelAtributos.y,
            panelAtributos.width, 24);

    final Rectangle etiquetaArma = new Rectangle(titularPanelEquipo.x + margenGeneral,
            titularPanelEquipo.y + titularPanelEquipo.height + margenGeneral / 2,
            titularPanelEquipo.width - margenGeneral * 2, margenGeneral * 2 - 16
            + MedidorString.medirAltoPixeles(GestorPrincipal.sd.getGraphics(), "ARMADURA"));
    final Rectangle contenedorArma1 = new Rectangle(etiquetaArma.x + 1, etiquetaArma.y + etiquetaArma.height,
            etiquetaArma.width / 2 - 2,
            Constantes.LADO_SPRITE + 2);
    final Rectangle contenedorArma2 = new Rectangle(etiquetaArma.x + etiquetaArma.width / 2 + 1, etiquetaArma.y
            + etiquetaArma.height, etiquetaArma.width / 2 - 2,
            Constantes.LADO_SPRITE + 2);

    final Rectangle etiquetaArmaduras = new Rectangle(etiquetaArma.x,
            etiquetaArma.y + titularPanelEquipo.height + contenedorArma1.height + margenGeneral / 2,
            titularPanelEquipo.width - margenGeneral * 2, margenGeneral * 2 - 16
            + MedidorString.medirAltoPixeles(GestorPrincipal.sd.getGraphics(), "ARMADURA"));
    final Rectangle contenedorArmadura1 = new Rectangle(etiquetaArmaduras.x + 1, etiquetaArmaduras.y
            + etiquetaArmaduras.height, etiquetaArmaduras.width / 2 - 2,
            Constantes.LADO_SPRITE);
    final Rectangle contenedorArmadura2 = new Rectangle(etiquetaArmaduras.x + etiquetaArmaduras.width / 2 + 1,
            etiquetaArmaduras.y + etiquetaArmaduras.height, etiquetaArmaduras.width / 2 - 2,
            Constantes.LADO_SPRITE);
    final Rectangle contenedorArmadura3 = new Rectangle(etiquetaArmaduras.x + 1,
            contenedorArmadura2.y + contenedorArmadura1.height + 2, etiquetaArmaduras.width / 2 - 2,
            Constantes.LADO_SPRITE);
    final Rectangle contenedorArmadura4 = new Rectangle(etiquetaArmaduras.x + etiquetaArmaduras.width / 2 + 1,
            contenedorArmadura2.y + contenedorArmadura1.height + 2, etiquetaArmaduras.width / 2 - 2,
            Constantes.LADO_SPRITE);

    final Rectangle etiquetaJoyas = new Rectangle(etiquetaArma.x,
            etiquetaArmaduras.y + titularPanelEquipo.height + contenedorArmadura3.height + margenGeneral * 5,
            titularPanelEquipo.width - margenGeneral * 2, margenGeneral * 2 - 16
            + MedidorString.medirAltoPixeles(GestorPrincipal.sd.getGraphics(), "JOYERIA"));

    final Rectangle contenedorCollar = new Rectangle(etiquetaJoyas.x + 1, etiquetaJoyas.y
            + etiquetaJoyas.height, etiquetaJoyas.width / 2 - 2,
            Constantes.LADO_SPRITE);
    final Rectangle contenedorAccesorio = new Rectangle(etiquetaJoyas.x + etiquetaJoyas.width / 2 + 1,
            etiquetaJoyas.y + etiquetaJoyas.height, etiquetaJoyas.width / 2 - 2,
            Constantes.LADO_SPRITE);
    final Rectangle contenedorAnillo1 = new Rectangle(etiquetaJoyas.x + 1,
            contenedorCollar.y + contenedorCollar.height + 2, etiquetaJoyas.width / 2 - 2,
            Constantes.LADO_SPRITE);
    final Rectangle contenedorAnillo2 = new Rectangle(etiquetaJoyas.x + etiquetaJoyas.width / 2 + 1,
            contenedorCollar.y + contenedorCollar.height + 2, etiquetaJoyas.width / 2 - 2,
            Constantes.LADO_SPRITE);

    private TipoObjeto tipoObjetoSeleccionado;
    private Rectangle armaEquipada1;
    private Rectangle armaEquipada2;

    public MenuEquipo(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenu em) {
        super(nombreSeccion, etiquetaMenu, em);
        etiquetaEquipo = 0;
        EstructuraMenu.ANCHO_ETIQUETAS = 30;
        gestorMenuEquipables = new GestorMenuEquipables();
    }

    @Override
    public void actualizar() {
        actualizarEtiquetasEquipables();
//       actualizarPosicionesMenu(etiquetaEquipo);
        actualizarSeleccionRaton();
//       actualizarObjetoSeleccionado();
//       removerObjetoEquipado();
        gestorMenuEquipables.actualizar();

    }

    @Override
    public void dibujar(final Graphics g, final SuperficieDibujo sd) {
        dibujarLimitePeso(g);
        dibujarPaneles(g);
        dibujarPaginador(g);
        gestorMenuEquipables.dibujar(g);
    }

    private ArrayList<Rectangle> listaContenedores() {
        //{arma1:0,arma2:1,armadura1:2,armadura2:3,armadura3:4,armadura4:5,anillo1:6,anillo2:7,accesorio:7,collar:9}
        return new ArrayList<>(List.of(contenedorArma1, contenedorArma2, contenedorArmadura1,
                contenedorArmadura2, contenedorArmadura3, contenedorArmadura4, contenedorAnillo1, contenedorAnillo2,
                contenedorAccesorio, contenedorCollar));
    }

//    private void actualizarSeleccionArma1() {
//        menuEquipables.seleccionArma1(panelEquipo);
//    }
//
//    private void actualizarSeleccionArma2() {
//        menuEquipables.actualizarSeleccionArma2(panelEquipo);
//    }
//
//    private void actualizarSeleccionArmadura(Rectangle contenedor) {
//        menuEquipables.actualizarSeleccionArmadura(contenedor, panelEquipo);
//
//    }
//
//    private void actualizarSeleccionCollar() {
//        menuEquipables.actualizarSeleccionCollar(panelEquipo);
//    }
//
//    private void actualizarSeleccionAccesorio() {
//        menuEquipables.actualizarSeleccionAccesorio(panelEquipo);
//    }
//
//    private void actualizarSeleccionAnillo() {
//        menuEquipables.actualizarSeleccionAnillo(panelEquipo);
//    }

    private void actualizarSeleccionRaton() {

//        actualizarSeleccionArma1();
//        //actualizarSeleccionArma2();
//        actualizarSeleccionArmadura(contenedorArmadura1);
//        actualizarSeleccionArmadura(contenedorArmadura2);
//        actualizarSeleccionArmadura(contenedorArmadura3);
//        actualizarSeleccionArmadura(contenedorArmadura4);
//        actualizarSeleccionCollar();
//        actualizarSeleccionAccesorio();
//        actualizarSeleccionAnillo();
    }

    private void actualizarEtiquetasEquipables() {
    }

//    private void actualizarObjetoSeleccionado() {
//        menuEquipables.actualizarObjetoSeleccionado();
//    }
//
//    private void removerObjetoEquipado() {
//        menuEquipables.removerObjetoSeleccionado(panelEquipo);
//    }
//
//    private void actualizarPosicionesMenu(int etiqueta) {
//        if (ElementosPrincipales.inventario.getEquipo().isEmpty()) {
//            return;
//        }
//        menuEquipables.actualizarPosicionesMenu(2);
//
//    }

    private void dibujarTooltipPeso(final Graphics g, SuperficieDibujo sd) {
        String textoCarga = String.format("%.1f", ElementosPrincipales.jugador.getGestorAt().getPesoActual());
        String textoCargaTotal = String.format("%.1f", ElementosPrincipales.jugador.getGestorAt().getLimitePeso());
        String textoFinal = textoCarga + "/" + textoCargaTotal;
        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(barraPeso))) {
            GeneradorTooltip.dibujarTooltip(g, sd, textoFinal);
        }

    }

    private void dibujarTooltipEquipo(final Graphics g, final SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();

//        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetos))) {
//            for (Objeto objeto : ElementosPrincipales.inventario.getEquipo()) {
//                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
//
//                    // Dibuja el tooltip solo si objetoSeleccionado no es null
//                    dibujarTooltipObjetosEquipados(g, sd, objeto);
//                }
//            }
//        }
    }

    private void dibujarTooltipObjeto(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        // Aquí puedes personalizar la apariencia del tooltip según tus necesidades
        if (etiquetaEquipo < 2 && objeto instanceof Arma) {

            switch (etiquetaEquipo) {
                case 0:
                    if (!(objeto instanceof ArmaUnaMano)) {
                        break;
                    }
                    DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
                    ArmaUnaMano arma = (ArmaUnaMano) objeto;
                    GeneradorTooltip.dibujarTooltipMejorado(g, sd, arma.getNombre() + "\nATAQUE: " + arma.getAtaque()
                            + "\nALCANCE: " + arma.getAlcanceInt() + "\nPESO: " + arma.getPeso() + " oz.");
                    break;
                case 1:
                    if (!(objeto instanceof ArmaDosManos)) {
                        break;
                    }
                    DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
                    ArmaDosManos armaDosManos = (ArmaDosManos) objeto;
                    GeneradorTooltip.dibujarTooltipMejorado(g, sd, armaDosManos.getNombre() + "\nATAQUE: " + armaDosManos.getAtaque()
                            + "\nALCANCE: " + armaDosManos.getAlcanceInt() + "\nPESO: " + armaDosManos.getPeso() + " oz.");
                    break;

            }

        } else if (etiquetaEquipo > 1 || etiquetaEquipo < 6 && objeto instanceof Armadura) {
            switch (etiquetaEquipo) {
                case 2:
                    if (!(objeto instanceof ProteccionMedia)) {
                        break;
                    }
                    DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
                    ProteccionMedia media = (ProteccionMedia) objeto;
                    GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nDEF FISICA: "
                            + media.getDefensaF() + "\nDEF MAGICA: " + media.getDefensaM() + "\nPESO: " + objeto.getPeso() + " oz.");
                    break;
                case 3:
                    if (!(objeto instanceof ProteccionAlta)) {
                        break;
                    }
                    DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
                    ProteccionAlta alta = (ProteccionAlta) objeto;
                    GeneradorTooltip.dibujarTooltipMejorado(g, sd, alta.getNombre() + "\nDEF FISICA: "
                            + alta.getDefensaF() + "\nDEF MAGICA: " + alta.getDefensaM() + "\nPESO: " + alta.getPeso() + " oz.");
                    break;
                case 4:
                    if (!(objeto instanceof ProteccionLateral)) {
                        break;
                    }
                    DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
                    ProteccionLateral lateral = (ProteccionLateral) objeto;
                    GeneradorTooltip.dibujarTooltipMejorado(g, sd, lateral.getNombre() + "\nDEF FISICA: "
                            + lateral.getDefensaF() + "\nDEF MAGICA: " + lateral.getDefensaM() + "\nPESO: " + lateral.getPeso() + " oz.");
                    break;
                case 5:
                    if (!(objeto instanceof ProteccionBaja)) {
                        break;
                    }
                    DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
                    ProteccionBaja baja = (ProteccionBaja) objeto;
                    GeneradorTooltip.dibujarTooltipMejorado(g, sd, baja.getNombre() + "\nDEF FISICA: "
                            + baja.getDefensaF() + "\nDEF MAGICA: " + baja.getDefensaM() + "\nPESO: " + baja.getPeso() + " oz.");
                    break;
            }
        }
        if (etiquetaEquipo == 6 && objeto instanceof Joya) {

            DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
            Joya joya = (Joya) objeto;
            String texto = devolverStringJoyas(joya);
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, joya.getNombre() + texto);
            System.out.println("Joya");
        }
    }

    private void dibujarTooltipObjetosEquipados(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        // Aquí puedes personalizar la apariencia del tooltip según tus necesidades

        if ((objeto instanceof Arma)) {

            DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
            Arma arma = (Arma) objeto;
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, arma.getNombre() + "\nATAQUE: " + arma.getAtaque()
                    + "\nALCANCE: " + arma.getAlcanceInt() + "\nPESO: " + arma.getPeso() + " oz.");

        } else if (objeto instanceof Armadura) {

            DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
            Armadura media = (Armadura) objeto;
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nDEF FISICA: "
                    + media.getDefensaF() + "\nDEF MAGICA: " + media.getDefensaM() + "\nPESO: " + objeto.getPeso() + " oz.");

        } else if (objeto instanceof Joya) {

            DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu());
            Joya joya = (Joya) objeto;
            String texto = devolverStringJoyas(joya);
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, joya.getNombre() + texto);

        }
    }

    private void dibujarTooltipArmas(final Graphics g, final SuperficieDibujo sd) {
        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorArma1))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getArma1() != null) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getArma1().getNombre()
                    + "\nATAQUE: " + ElementosPrincipales.jugador.getAlmacenEquipo().getArma1().getAtaque() + "\nALCANCE: "
                    + ElementosPrincipales.jugador.getAlmacenEquipo().getArma1().getAlcanceInt());
        } else if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorArma2))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getArma2() != null) {

            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getArma2().getNombre()
                    + "\nATAQUE: " + ElementosPrincipales.jugador.getAlmacenEquipo().getArma2().getAtaque() + "\nALCANCE: "
                    + ElementosPrincipales.jugador.getAlmacenEquipo().getArma2().getAlcanceInt());
        }
    }

    private void dibujarTooltipArmaduras(final Graphics g, final SuperficieDibujo sd) {
        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorArmadura1))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia() != null) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia().getNombre()
                    + "\nDEF FISICA: " + ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia().getDefensaF()
                    + "\nDEF MAGICA: " + ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia().getDefensaM());
        }
        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorArmadura2))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getCasco() != null) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getCasco().getNombre()
                    + "\nDEF FISICA: " + ElementosPrincipales.jugador.getAlmacenEquipo().getCasco().getDefensaF()
                    + "\nDEF MAGICA: " + ElementosPrincipales.jugador.getAlmacenEquipo().getCasco().getDefensaM());
        }

        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorArmadura3))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getGuante() != null) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd,
                    ElementosPrincipales.jugador.getAlmacenEquipo().getGuante().getNombre() + "\nDEF FISICA: "
                            + ElementosPrincipales.jugador.getAlmacenEquipo().getGuante().getDefensaF() + "\nDEF MAGICA: "
                            + ElementosPrincipales.jugador.getAlmacenEquipo().getGuante().getDefensaM());
        }
        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorArmadura4))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getBota() != null) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd,
                    ElementosPrincipales.jugador.getAlmacenEquipo().getBota().getNombre() + "\nDEF FISICA: "
                            + ElementosPrincipales.jugador.getAlmacenEquipo().getBota().getDefensaF() + "\nDEF MAGICA: "
                            + ElementosPrincipales.jugador.getAlmacenEquipo().getBota().getDefensaM());
        }
    }

    private void dibujarTooltipJoyas(final Graphics g, final SuperficieDibujo sd) {

        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorCollar))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getCollar() != null) {
            String texto = devolverStringJoyas(ElementosPrincipales.jugador.getAlmacenEquipo().getCollar());
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getCollar().getNombre() + texto);
        }

        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorAccesorio))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio() != null) {
            String texto = devolverStringJoyas(ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio());
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio().getNombre() + texto);
        }

        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorAnillo1))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo1() != null) {
            String texto = devolverStringJoyas(ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo1());
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo1().getNombre() + texto);
        }

        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(contenedorAnillo2))
                && ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo2() != null) {
            String texto = devolverStringJoyas(ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo2());
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo2().getNombre() + texto);
        }
    }

    private void dibujarPaneles(final Graphics g) {
        //dibujarPanelObjetos(g, panelObjetos, titularPanelObjetos, "EQUIPABLES");
        dibujarPanelEquipo(g, panelEquipo, titularPanelEquipo, "EQUIPO ACTUAL");
        dibujarPanelAtributos(g, panelAtributos, titularPanelAtributos, "ATRIBUTOS");
    }

    private void dibujarPanelObjetos(Graphics g, final Rectangle panel, final Rectangle titularPanel,
                                     final String nombrePanel) {
        dibujarPanel(g, panel, titularPanel, nombrePanel);

    }

    private void dibujarPanelEquipo(Graphics g, final Rectangle panel, final Rectangle titularPanel, final String nombrePanel) {
        dibujarPanel(g, panel, titularPanel, nombrePanel);
        g.setColor(Color.black);

        if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma1() != null) {
            Point coordenadaImagen1 = new Point(contenedorArma1.x + contenedorArma1.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorArma1.y + 1);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getArma1().getSprite().getImagen(),
                    coordenadaImagen1);
        }
        if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma2() != null) {
            if (!(ElementosPrincipales.jugador.getAlmacenEquipo().getArma2() instanceof SinArma)) {
                Point coordenadaImagen2 = new Point(contenedorArma2.x + contenedorArma2.width / 2 - Constantes.LADO_SPRITE / 2,
                        contenedorArma2.y + 1);

                DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getArma2().getSprite().getImagen(),
                        coordenadaImagen2);
            }
        }

        if (ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia() != null) {

            Point coordenadaImagen3 = new Point(contenedorArmadura1.x + contenedorArmadura1.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorArmadura1.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia().getSprite().getImagen(),
                    coordenadaImagen3);

        }
        if (ElementosPrincipales.jugador.getAlmacenEquipo().getCasco() != null) {

            Point coordenadaImagen4 = new Point(contenedorArmadura2.x + contenedorArmadura2.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorArmadura2.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getCasco().getSprite().getImagen(),
                    coordenadaImagen4);

        }

        if (ElementosPrincipales.jugador.getAlmacenEquipo().getGuante() != null) {

            Point coordenadaImagen5 = new Point(contenedorArmadura3.x + contenedorArmadura3.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorArmadura3.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getGuante().getSprite().getImagen(),
                    coordenadaImagen5);

        }

        if (ElementosPrincipales.jugador.getAlmacenEquipo().getBota() != null) {

            Point coordenadaImagen6 = new Point(contenedorArmadura4.x + contenedorArmadura4.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorArmadura4.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getBota().getSprite().getImagen(),
                    coordenadaImagen6);

        }
        if (ElementosPrincipales.jugador.getAlmacenEquipo().getCollar() != null) {

            Point coordenadaImagen7 = new Point(contenedorCollar.x + contenedorCollar.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorCollar.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getCollar().getSprite().getImagen(),
                    coordenadaImagen7);

        }

        if (ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio() != null) {

            Point coordenadaImagen8 = new Point(contenedorAccesorio.x + contenedorAccesorio.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorAccesorio.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio().getSprite().getImagen(),
                    coordenadaImagen8);

        }

        if (ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo1() != null) {

            Point coordenadaImagen9 = new Point(contenedorAnillo1.x + contenedorAnillo1.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorAnillo1.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo1().getSprite().getImagen(),
                    coordenadaImagen9);

        }

        if (ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo2() != null) {

            Point coordenadaImagen10 = new Point(contenedorAnillo2.x + contenedorAnillo2.width / 2 - Constantes.LADO_SPRITE / 2,
                    contenedorAnillo2.y);

            DibujoDebug.dibujarImagen(g, ElementosPrincipales.jugador.getAlmacenEquipo().getAnillo2().getSprite().getImagen(),
                    coordenadaImagen10);

        }

        for (Rectangle contenedor : listaContenedores()) {
            DibujoDebug.dibujarRectanguloContorno(g, contenedor);
        }
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaArma);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaArmaduras);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaJoyas);


        for (Objeto objeto : ElementosPrincipales.inventario.getEquipo()) {
            switch (etiquetaEquipo) {
                case 0:
                    if (objeto instanceof ArmaUnaMano && objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getArma1()) {
                        armaEquipada1 = objeto.getPosicionMenu();
                        if (gestorMenuEquipables.getNumeroSeccion() == 1) {
                            DibujoDebug.dibujarString(g, "H1", armaEquipada1.x + armaEquipada1.width - 10, armaEquipada1.y + 7);
                            DibujoDebug.dibujarRectanguloContorno(g, armaEquipada1, Color.green);
                        }

                    }

                    break;
                case 1:
                    if (objeto instanceof ArmaDosManos && objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getArma1()) {
                        armaEquipada2 = objeto.getPosicionMenu();
                        if (gestorMenuEquipables.getNumeroSeccion() == 2) {
                            DibujoDebug.dibujarRectanguloContorno(g, armaEquipada2, Color.BLACK);
                            DibujoDebug.dibujarString(g, "H2", armaEquipada2.x + 2, armaEquipada2.y + 7);
                        }
                    }
                    break;

                case 2:
                    if (objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getArmaduraMedia()) {
                        Rectangle armaduraEquipada1 = objeto.getPosicionMenu();
                        DibujoDebug.dibujarRectanguloContorno(g, armaduraEquipada1, Color.gray);
                        DibujoDebug.dibujarString(g, "E", armaduraEquipada1.x + 1, armaduraEquipada1.y + 7);
                    }
                    break;
                case 3:

                    if (objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getCasco()) {
                        Rectangle armaduraEquipada2 = objeto.getPosicionMenu();
                        DibujoDebug.dibujarRectanguloContorno(g, armaduraEquipada2, Color.gray);
                        DibujoDebug.dibujarString(g, "E", armaduraEquipada2.x + 1, armaduraEquipada2.y + 7);
                    }
                    break;
                case 4:
                    if (objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getGuante()) {
                        Rectangle armaduraEquipada3 = objeto.getPosicionMenu();
                        DibujoDebug.dibujarRectanguloContorno(g, armaduraEquipada3, Color.gray);
                        DibujoDebug.dibujarString(g, "E", armaduraEquipada3.x + 1, armaduraEquipada3.y + 7);
                    }
                    break;
                case 5:
                    if (objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getBota()) {
                        Rectangle armaduraEquipada4 = objeto.getPosicionMenu();
                        DibujoDebug.dibujarRectanguloContorno(g, armaduraEquipada4, Color.gray);
                        DibujoDebug.dibujarString(g, "E", armaduraEquipada4.x + 1, armaduraEquipada4.y + 7);
                    }
                    break;
                case 6:
                    if (objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getCollar()) {
                        Rectangle collarEquipado = objeto.getPosicionMenu();
                        DibujoDebug.dibujarRectanguloContorno(g, collarEquipado, Color.gray);
                        DibujoDebug.dibujarString(g, "E", collarEquipado.x + 1, collarEquipado.y + 7);
                    }
                    if (objeto == ElementosPrincipales.jugador.getAlmacenEquipo().getAccesorio()) {
                        Rectangle accesorioEquipado = objeto.getPosicionMenu();
                        DibujoDebug.dibujarRectanguloContorno(g, accesorioEquipado, Color.gray);
                        DibujoDebug.dibujarString(g, "E", accesorioEquipado.x + 1, accesorioEquipado.y + 7);
                    }
                    break;

            }

        }

        int xEtiquetaArma = etiquetaArma.x + etiquetaArma.width / 2 - MedidorString.medirAnchoPixeles(g, "ARMAS") / 2;
        int yEtiquetaArma = etiquetaArma.y + etiquetaArma.height / 2 - 2 + MedidorString.medirAltoPixeles(g, "ARMAS") / 2;
        DibujoDebug.dibujarString(g, "ARMAS", new Point(xEtiquetaArma, yEtiquetaArma), Color.white);

        int xEtiquetaArmadura = etiquetaArmaduras.x + etiquetaArmaduras.width / 2
                - MedidorString.medirAnchoPixeles(g, "ARMADURA") / 2;
        int yEtiquetaArmadura = etiquetaArmaduras.y + etiquetaArmaduras.height / 2
                - 2 + MedidorString.medirAltoPixeles(g, "ARMADURA") / 2;
        DibujoDebug.dibujarString(g, "ARMADURA", new Point(xEtiquetaArmadura, yEtiquetaArmadura), Color.white);

        int xEtiquetaJoyeria = etiquetaArmaduras.x + etiquetaArmaduras.width / 2
                - MedidorString.medirAnchoPixeles(g, "ARMADURA") / 2;
        int yEtiquetaJoyeria = etiquetaJoyas.y + etiquetaJoyas.height / 2
                - 2 + MedidorString.medirAltoPixeles(g, "JOYERIA") / 2;
        DibujoDebug.dibujarString(g, "JOYERIA", new Point(xEtiquetaJoyeria, yEtiquetaJoyeria), Color.white);
    }

    private void dibujarPanelAtributos(Graphics g, final Rectangle panel, final Rectangle titularPanel,
                                       final String nombrePanel) {
        dibujarPanel(g, panel, titularPanel, nombrePanel);
        g.setColor(Color.black);

        int xAtributos = panelAtributos.x + margenGeneral;
        int yAtributos = panelAtributos.y + titularPanelAtributos.height + margenGeneral;

        double eva = (ElementosPrincipales.jugador.getGestorAt().getDestreza() * 1.2
                - ElementosPrincipales.jugador.getGestorAt().getPesoActual() * 0.3);
        double crit = (ElementosPrincipales.jugador.getGestorAt().getSuerte() * 0.5);

        String[] bloque1 = {
                "Nivel: " + ElementosPrincipales.jugador.getGestorAt().getNivel(),
                "Vida: " + ElementosPrincipales.jugador.gestorAtributos.getVidaActual() + " / " +
                        ElementosPrincipales.jugador.gestorAtributos.getVidaMaxima(),
                "Mana: " + ElementosPrincipales.jugador.gestorAtributos.getMana() + " / " +
                        ElementosPrincipales.jugador.getGestorAt().getManaMaximo(),
                "Experiencia: " + ElementosPrincipales.jugador.getGestorAt().getExperiencia() + " / "
                        + ElementosPrincipales.jugador.getGestorAt().getExperienciaMaxima(),
                "Resistencia: " + ElementosPrincipales.jugador.getGestorAt().getResistencia() + " / "
                        + ElementosPrincipales.jugador.getGestorAt().getResistenciaMaxima()
        };

        String[] bloque2 = {
                "Ataque: " + (ElementosPrincipales.jugador.getGestorAt().getFuerza() + ElementosPrincipales.jugador.getGestorAt().getNivel())
                        + " + " + getAtaqueFisicoJugador(),
                "Magia: " + (ElementosPrincipales.jugador.gestorAtributos.getInteligencia() +
                        ElementosPrincipales.jugador.getGestorAt().getNivel())
                        + " + " + (int) (getMagiaJugador()),
                "Defensa F: " + ((int) (ElementosPrincipales.jugador.gestorAtributos.getVidaMaxima() * 0.01
                        + ElementosPrincipales.jugador.getGestorAt().getConstitucion() * 1.1)) + " + " + getDFJugador(),
                "Defensa M: " + ((int) (ElementosPrincipales.jugador.getGestorAt().getManaMaximo() * 0.01
                        + ElementosPrincipales.jugador.gestorAtributos.getInteligencia() * 1.1)) + " + " + getDMJugador(),
                String.format("Evasion: %.1f ", eva) + "+ " + String.format("%.1f %%", getEvasionJugador()),
                String.format("Critico: %.1f ", crit) + "+ " + String.format("%.1f %%", getCritJugador()),
                String.format("Resistencia F: %.1f%% ", getResFJugador()),
                String.format("Resistencia M: %.1f%% ", getResMJugador())
        };

        String[] bloque3 = {
                "Fuerza: " + ElementosPrincipales.jugador.getGestorAt().getFuerza(),
                "Constitucion: " + ElementosPrincipales.jugador.getGestorAt().getConstitucion(),
                "Inteligencia: " + ElementosPrincipales.jugador.getGestorAt().getInteligencia(),
                "Destreza: " + ElementosPrincipales.jugador.getGestorAt().getDestreza(),
                "Suerte: " + ElementosPrincipales.jugador.getGestorAt().getSuerte(),};

        dibujarBloque(g, xAtributos, yAtributos, bloque1);
        yAtributos += 60;

        dibujarBloque(g, xAtributos, yAtributos, bloque2);
        yAtributos += 90;

        dibujarBloque(g, xAtributos, yAtributos, bloque3);
    }

    private void dibujarBloque(Graphics g, int x, int y, String[] bloque) {
        for (String atributo : bloque) {
            DibujoDebug.dibujarString(g, atributo, new Point(x, y));
            y += 10;
        }
    }

    private void dibujarPanel(final Graphics g, final Rectangle panel, final Rectangle titularPanel,
                              final String nombrePanel) {
        g.setColor(Color.DARK_GRAY);
        DibujoDebug.dibujarRectanguloContorno(g, panel);
        DibujoDebug.dibujarRectanguloRelleno(g, titularPanel);
        g.setColor(Color.white);
        DibujoDebug.dibujarString(g, nombrePanel, new Point(
                panel.x + titularPanel.width / 2 - MedidorString.medirAnchoPixeles(g, nombrePanel) / 2,
                panel.y + titularPanel.height - MedidorString.medirAltoPixeles(g, nombrePanel) - 4));
    }

    private int getDFJugador() {
        int defensaFisica = ElementosPrincipales.jugador.calcularDefensaF();

        return defensaFisica;
    }

    private int getAtaqueFisicoJugador() {
        int ataque = ElementosPrincipales.jugador.calcularAtqFisico();
        return ataque;
    }

    private int getMagiaJugador() {
        int magia = ElementosPrincipales.jugador.calcularAtqMagico();

        return magia;
    }

    private int getDMJugador() {
        int defensaMagica = ElementosPrincipales.jugador.calcularDefensaM();

        return defensaMagica;
    }

    private double getEvasionJugador() {
        double eva = ElementosPrincipales.jugador.calcularEvasion();

        return eva;
    }

    private double getCritJugador() {
        double crit = ElementosPrincipales.jugador.calcularCrit();

        return crit;
    }

    private double getResFJugador() {
        double resF = ElementosPrincipales.jugador.calcularResF();

        return resF;
    }

    private double getResMJugador() {
        double resM = ElementosPrincipales.jugador.calcularResM();

        return resM;
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

    private void dibujarPaginador(final Graphics g) {
        final int anchoBoton = 24;
        final int altoBoton = 12;

        int anteriorX = x + width - Constantes.LADO_SPRITE * 2 + 4;

        int anteriorY = y + height - (Constantes.LADO_SPRITE / 2);

        final Rectangle anterior = new Rectangle(anteriorX - 4, anteriorY, anchoBoton, altoBoton);
        final Rectangle siguiente = new Rectangle(anterior.x + anterior.width + margenGeneral, anterior.y,
                anchoBoton, altoBoton);
        g.setColor(Color.blue);

        DibujoDebug.dibujarRectanguloContorno(g, anterior);
        DibujoDebug.dibujarRectanguloContorno(g, siguiente);
        DibujoDebug.dibujarString(g, "<<", anterior.x + anterior.width - 18, anterior.y + anterior.height - 5);
        DibujoDebug.dibujarString(g, ">>", siguiente.x + siguiente.width - 18, siguiente.y + siguiente.height - 5);
    }

    public TipoObjeto getTipoObjetoSeleccionado() {
        return tipoObjetoSeleccionado;
    }

    public Objeto getObjetoSeleccionado() {
        return objetoSeleccionado;
    }

    public void eliminarObjetoSeleccionado() {
        objetoSeleccionado = null;
    }

}
