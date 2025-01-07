package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armas.Arma;
import principal.inventario.joyas.Joya;

import java.awt.*;
import java.util.ArrayList;

public abstract class SeccionMenuEquipable {
    protected int margenGeneral = 8;
    protected String nombreSeccion;
    protected Rectangle etiquetaMenu;
    protected EstructuraMenuEquipable em;
    protected Objeto objetoSeleccionado;
    private int numeroSeccion;

    public SeccionMenuEquipable(final String nombreSeccion, final Rectangle etiquetaMenu, final EstructuraMenuEquipable em, int numeroSeccion) {
        this.nombreSeccion = nombreSeccion;
        this.etiquetaMenu = etiquetaMenu;
        this.em = em;
        this.objetoSeleccionado = null;
        this.numeroSeccion = numeroSeccion;
    }

    public SeccionMenuEquipable() {

    }

    public abstract void actualizar();

    public Rectangle getEtiquetaMenuEscalada() {
        final int x = (etiquetaMenu.x);
        final int y = (etiquetaMenu.y);
        final int ancho = (etiquetaMenu.width);
        final int alto = (etiquetaMenu.height);

        return EscaladorElementos.escalarRectangleArriba(new Rectangle(x, y, ancho, alto));
    }

    public abstract void dibujar(final Graphics g);

    public abstract void dibujarObjetosEquipables(Graphics g);

    public abstract void actualizarPosicionMenu();

    public abstract void actualizarObjetoSeleccionado();

    protected abstract void actualizarSeleccionRaton();

    protected void dibujarTooltipEquipo(final Graphics g, final SuperficieDibujo sd, ArrayList<Objeto> listaObjetos) {

    }

    private void dibujarTooltipObjetosEquipados(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        // Aquí puedes personalizar la apariencia del tooltip según tus necesidades

        if ((objeto instanceof Arma arma)) {



        }
    }


    public void dibujarEtiquetaActiva(Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);

        final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
        DibujoDebug.dibujarRectanguloRelleno(g, marcaActiva, new Color(0xff6700));

        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);

    }

    public void dibujarEtiquetaInactiva(final Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);

    }

    public void dibujarEtiquetaInactResaltada(final Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);

        final Rectangle etiquetaResaltada = new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, etiquetaMenu.y + 5,
                5, etiquetaMenu.height - 10);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaResaltada, Color.gray);
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.BLUE);

    }

    public void dibujarEtiquetaActivaResaltada(final Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);

        final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
        DibujoDebug.dibujarRectanguloRelleno(g, marcaActiva, new Color(0xff6700));
        final Rectangle etiquetaResaltada = new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, etiquetaMenu.y + 5,
                5, etiquetaMenu.height - 10);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaResaltada, new Color(0x2a2a2a));

        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
    }

    protected void dibujarObjetoPosicionMenu(Graphics g, Objeto objeto) {
        DibujoDebug.dibujarImagen(g, objeto.getSprite().getImagen(),
                objeto.getPosicionMenu().x, objeto.getPosicionMenu().y);

        DibujoDebug.dibujarRectanguloRelleno(g,
                objeto.getPosicionMenu().x + objeto.getPosicionMenu().width - 12,
                objeto.getPosicionMenu().y + objeto.getPosicionMenu().height - 8,
                12, 8, Color.black);

        g.setColor(Color.white);

        String texto = (objeto.getCantidad() < 10) ? "0" + objeto.getCantidad()
                : String.valueOf(objeto.getCantidad());

        DibujoDebug.dibujarString(g, texto,
                objeto.getPosicionMenu().x + objeto.getPosicionMenu().width
                        - MedidorString.medirAnchoPixeles(g, texto),
                objeto.getPosicionMenu().y + objeto.getPosicionMenu().height - 1);
    }

    protected void dibujarObjetoSeleccionado(Graphics g) {
        if (objetoSeleccionado != null) {
            DibujoDebug.dibujarImagen(g, objetoSeleccionado.getSprite().getImagen(),
                    new Point(objetoSeleccionado.getPosicionFlotante().x,
                            objetoSeleccionado.getPosicionFlotante().y));
        }
    }

    public void actualizarPosicionMenuObjeto(Objeto objeto, int contador) {

        int margenGeneral = 8;
        final int lado = Constantes.LADO_SPRITE;
        final Point pi = new Point(em.getTitularPanelEquipo().x + margenGeneral,
                em.getTitularPanelEquipo().y + em.getTitularPanelEquipo().height + margenGeneral);


        Rectangle posicion = new Rectangle(pi.x + contador % 4 * (lado + margenGeneral),
                pi.y + contador / 4 * (lado + margenGeneral), lado, lado);
        objeto.setPosicionMenu(posicion);
    }



    public Rectangle getEtiquetaMenu() {
        return etiquetaMenu;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public Objeto getObjetoSeleccionado() {
        return objetoSeleccionado;
    }

    public void eliminarObjetoSeleccionado() {
        objetoSeleccionado = null;
    }

    public int getNumeroSeccion() {
        return numeroSeccion;
    }
}
