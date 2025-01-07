package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.inventario.Objeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionBaja;
import principal.inventario.armaduras.ProteccionMedia;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

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
        dibujarTooltip(g, GestorPrincipal.sd);
    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : obtenerArmaduras()) {
            super.dibujarObjetoPosicionMenu(g, objeto);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    private void dibujarTooltip(Graphics g, SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
            for (Objeto objeto : obtenerArmaduras()) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                    dibujarTooltipArmadura(g, GestorPrincipal.sd, objeto);
                }
            }
        }
    }

    private void dibujarTooltipArmadura(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu(), Color.DARK_GRAY);
        ProteccionMedia armadura = (ProteccionMedia) objeto;
        GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nDEF FISICA: "
                + armadura.getDefensaF() + "\nDEF MAGICA: " + armadura.getDefensaM() + "\nPESO: " + objeto.getPeso() + " oz.");
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : obtenerArmaduras()) {
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

            for (Objeto objeto : obtenerArmaduras()) {
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
                if (obtenerArmaduras().isEmpty()) {
                    return;
                }
                for (Objeto objeto : obtenerArmaduras()) {
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
            ElementosPrincipales.jugador.getAlmacenEquipo().setArmaduraMedia((ProteccionMedia) objetoSeleccionado);
            ElementosPrincipales.jugador.getAlmacenEquipo().equipoActual.add(objetoSeleccionado);
            objetoSeleccionado = null;
        }

    }

    private ArrayList<Objeto> obtenerArmaduras() {
        ArrayList<Objeto> listaArmaduras = new ArrayList<>();
        for (Objeto objeto : ElementosPrincipales.inventario.objetos) {
            if (objeto instanceof ProteccionMedia) {
                listaArmaduras.add(objeto);
            }
        }
        return listaArmaduras;
    }
}
