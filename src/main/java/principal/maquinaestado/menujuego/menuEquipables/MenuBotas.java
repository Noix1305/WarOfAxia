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
import principal.inventario.armaduras.ProteccionLateral;
import principal.maquinaestado.menujuego.MenuEquipo;

import java.awt.*;
import java.util.ArrayList;


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
        dibujarTooltip(g, GestorPrincipal.sd);
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

    private void dibujarTooltip(Graphics g, SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(em.getMargen()))) {
            for (Objeto objeto : obtenerBotas()) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMenu()))) {
                    dibujarTooltipBotas(g, GestorPrincipal.sd, objeto);
                }
            }
        }

    }

    private void dibujarTooltipBotas(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMenu(), Color.DARK_GRAY);
        ProteccionBaja bota = (ProteccionBaja) objeto;
        GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nDEF FISICA: "
                + bota.getDefensaF() + "\nDEF MAGICA: " + bota.getDefensaM() + "\nPESO: " + objeto.getPeso() + " oz.");
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : obtenerBotas()) {
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

            for (Objeto objeto : obtenerBotas()) {
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
                for (Objeto objeto : obtenerBotas()) {
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

    private ArrayList<Objeto> obtenerBotas() {
        ArrayList<Objeto> listaBotas = new ArrayList<>();
        for (Objeto objeto : ElementosPrincipales.inventario.objetos) {
            if (objeto instanceof ProteccionBaja) {
                listaBotas.add(objeto);
            }
        }
        return listaBotas;
    }
}
