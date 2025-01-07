package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.inventario.Objeto;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionMedia;
import principal.inventario.armas.Arma;
import principal.inventario.armas.ArmaDosManos;
import principal.inventario.armas.ArmaUnaMano;

import java.awt.*;
import java.util.Iterator;

public class MenuArmaDosManos extends SeccionMenuEquipable {
    private final Rectangle contenedorArma2;

    public MenuArmaDosManos(String equipo, Rectangle etiquetaEquipo, EstructuraMenuEquipable estructuraMenu,
                            int numeroSeccion, Rectangle contenedor) {
        super(equipo, etiquetaEquipo, estructuraMenu, numeroSeccion);
        this.contenedorArma2 = contenedor;
    }

    @Override
    public void actualizar() {
        actualizarSeleccionRaton();
        actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
        actualizarSeleccionArma2();
    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
        dibujarTooltip(g, GestorPrincipal.sd);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objetoActual : ElementosPrincipales.inventario.getDosManos()) {
            super.dibujarObjetoPosicionMenu(g, objetoActual);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    private void dibujarTooltip(Graphics g, SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
            for (ArmaDosManos objeto : ElementosPrincipales.inventario.getDosManos()) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                    dibujarTooltipArma(g, GestorPrincipal.sd, objeto);
                }
            }
        }
    }

    private void dibujarTooltipArma(Graphics g, SuperficieDibujo sd, ArmaDosManos arma) {
        DibujoDebug.dibujarRectanguloContorno(g, arma.getPosicionMenu(), Color.DARK_GRAY);
        GeneradorTooltip.dibujarTooltipMejorado(g, sd, arma.getNombre() + "\nATAQUE: " + arma.getAtaque()
                + "\nALCANCE: " + arma.getAlcanceInt() + "\nPESO: " + arma.getPeso() + " oz.");
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getDosManos()) {
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

            for (Objeto objeto : ElementosPrincipales.inventario.getDosManos()) {
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
                if (ElementosPrincipales.inventario.getDosManos().isEmpty()) {
                    return;
                }
                for (Objeto objeto : ElementosPrincipales.inventario.getDosManos()) {
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

    public void actualizarSeleccionArma2() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionado instanceof Arma) {
            if (GestorPrincipal.sd.getRaton().isClick()
                    && posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(contenedorArma2))) {
                seleccionArma2();
            } else if (GestorPrincipal.sd.getRaton().isDobleClick()) {
                seleccionArma2();
                GestorPrincipal.sd.getRaton().setDobleClick(false);
            }
        }
    }


    public void seleccionArma2() {
        if (!sonMismasArmas((Arma) objetoSeleccionado, ElementosPrincipales.jugador.getAlmacenEquipo().getArma1())) {
            // Verifica si el contenedor de arma2 tiene un arma a distancia
            if (ElementosPrincipales.jugador.getAlmacenEquipo().getArma2() instanceof ArmaDosManos
                    || ElementosPrincipales.jugador.getAlmacenEquipo().getArma2() == null) {
                // Remueve el arma a distancia del contenedor 2 y de la lista de equipo actual
                Arma armaEnContenedor2 = ElementosPrincipales.jugador.getAlmacenEquipo().getArma2();
                ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma2(null);
                ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(armaEnContenedor2);
            }
            Objeto arma2 = ElementosPrincipales.jugador.getAlmacenEquipo().getArma1();
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.remove(arma2);
            ElementosPrincipales.jugador.getAlmacenEquipo().cambiarArma1((Arma) objetoSeleccionado);

            // Actualiza la lista de equipo actual
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }

    }


    private boolean sonMismasArmas(Arma arma1, Arma arma2) {
        return arma1 != null && arma1.equals(arma2);
    }
}
