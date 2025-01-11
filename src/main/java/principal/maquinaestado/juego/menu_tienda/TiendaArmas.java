/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.juego.menu_tienda;

import java.awt.*;
import java.util.ArrayList;

import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.inventario.Objeto;
import principal.inventario.armas.Arma;
import principal.maquinaestado.menujuego.MenuEquipo;

/**
 * @author GAMER ARRAX
 */
public class TiendaArmas extends SeccionTienda {

    public TiendaArmas(String nombreSeccion, Rectangle etiquetaMenu, EstructuraTienda et) {
        super(nombreSeccion, etiquetaMenu, et);
    }

    private boolean abrirTienda(int idTienda) {
        return idTienda == 0;
    }

    @Override

    public void dibujar(Graphics g, SuperficieDibujo sd) {

        super.dibujarLimitePeso(g);
        super.dibujarPaneles(g);

        if (ElementosPrincipales.mapa.tiendaActiva.getTipo() == 2) {
            dibujarElementos(g, actualizaListaArmas(objetosTienda), ElementosPrincipales.inventario.getArmas());
            super.dibujarVentanaParaCompra(g, GestorPrincipal.sd);
            super.dibujarVentanaParaVenta(g, GestorPrincipal.sd);

            if (MenuEquipo.mostrarTooltip) {
                super.dibujarTooltipPeso(g, sd);
                dibujarTooltipPaneles(g, sd, actualizaListaArmas(objetosTienda), ElementosPrincipales.inventario.getArmas());
            }
        }
    }

    @Override
    public void actualizar() {
        if (ElementosPrincipales.mapa.tiendaActiva.getTipo() == 2) {
            objetosTienda = ElementosPrincipales.mapa.getObjetosTiendaActual();
            super.actualizarPosicionesMenu(actualizaListaArmas(objetosTienda), ElementosPrincipales.inventario.getArmas());
            super.actualizarPosicionesCompraVenta();
            super.actualizarSeleccionRaton(actualizaListaArmas(objetosTienda), ElementosPrincipales.inventario.getArmas());
            super.actualizarObjetoSeleccionadoCompra();
            super.actualizarObjetoSeleccionadoVenta();
            super.calcularPesoFuturo();
            super.actualizarCanastaCompra(ElementosPrincipales.inventario.getArmas());
            super.actualizarCanastaVenta();
        }
    }

    private ArrayList<Objeto> actualizaListaArmas(ArrayList<Objeto> objetosTienda) {
        ArrayList<Objeto> objetos = new ArrayList<>();
        for (Objeto objeto : objetosTienda) {
            if (objeto instanceof Arma) {
                objetos.add(objeto);
            }
        }
        return objetos;
    }

    @Override
    protected void dibujarTooltipPaneles(Graphics g, SuperficieDibujo sd, ArrayList<Objeto> objetosTienda, ArrayList<Objeto> objetosInventario) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelComprar))) {
            for (Objeto objeto : objetosTienda) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionTienda()))) {
                    if (objetoSeleccionadoCompra == null) {
                        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionTienda(), Color.BLUE);
                        // Dibuja el tooltip solo si objetoSeleccionado no es null
                        dibujarTooltipObjeto(g, sd, objeto);
                    }
                }
            }
        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosComprados))) {
            for (Objeto objeto : canastaCompra) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionCompra()))) {
                    if (objetoSeleccionadoCompra == null) {
                        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionCompra(), Color.BLUE);
                        // Dibuja el tooltip solo si objetoSeleccionado no es null
                        dibujarTooltipObjeto(g, sd, objeto);

                    }
                }
            }
        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelVender))) {
            for (Objeto objeto : objetosInventario) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMochila()))) {
                    if (objetoSeleccionadoVenta == null) {
                        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionMochila(), Color.BLUE);
                        // Dibuja el tooltip solo si objetoSeleccionado no es null
                        dibujarTooltipObjeto(g, sd, objeto);

                    }
                }
            }
        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosVendidos))) {
            for (Objeto objeto : canastaVenta) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionVenta()))) {
                    if (objetoSeleccionadoVenta == null) {
                        DibujoDebug.dibujarRectanguloContorno(g, objeto.getPosicionVenta(), Color.BLUE);
                        // Dibuja el tooltip solo si objetoSeleccionado no es null
                        dibujarTooltipObjeto(g, sd, objeto);
                    }
                }
            }
        }
    }

    @Override
    protected void dibujarTooltipObjeto(Graphics g, SuperficieDibujo sd, Objeto objeto) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
        String textoPerforacion = "";
        String textoPrecio = "";

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelComprar))) {
            Arma armaCast = (Arma) objeto;
            textoPrecio += armaCast.getPrecioCompra();
            if (armaCast.isPenetrante()) {
                textoPerforacion = "Si";
            } else {
                textoPerforacion = "No";
            }

            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nPRECIO COMPRA: $" + textoPrecio
                    + "\nATAQUE MIN: " + armaCast.getAtaqueMin() + "\nATAQUE MAX: " + armaCast.getAtaqueMax() + "\nRANGO: " + armaCast.getAlcanceInt()
                    + "\nPERFORACION: " + textoPerforacion);


        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosComprados))) {

            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nTOTAL: $"
                    + objeto.getPrecioCompra() * objeto.getCantidadCompra());

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelVender))) {

            Arma arma = (Arma) objeto;

            textoPrecio += arma.getPrecioVenta();
            if (arma.isPenetrante()) {
                textoPerforacion = "Si";
            } else {
                textoPerforacion = "No";
            }

            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nPRECIO VENTA: $" + textoPrecio
                    + "\nATAQUE MIN: " + arma.getAtaqueMin() + "\nATAQUE MAX: " + arma.getAtaqueMax() + "\nRANGO: " + arma.getAlcanceInt()
                    + "\nPERFORACION: " + textoPerforacion);

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosVendidos))) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nTOTAL: $"
                    + objeto.getPrecioVenta() * objeto.getCantidadVenta());
        }

    }
}
