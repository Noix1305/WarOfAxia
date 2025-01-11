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
import principal.inventario.consumibles.Consumible;
import principal.maquinaestado.menujuego.MenuEquipo;

/**
 * @author GAMER ARRAX
 */
public class TiendaComida extends SeccionTienda {
    public TiendaComida(String nombreSeccion, Rectangle etiquetaMenu, EstructuraTienda et) {
        super(nombreSeccion, etiquetaMenu, et);
    }

    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd) {
        super.dibujarLimitePeso(g);
        super.dibujarPaneles(g);
        if (ElementosPrincipales.mapa.tiendaActiva.getTipo() == 4) {
            dibujarElementos(g, objetosTienda, ElementosPrincipales.inventario.getConsumibles());
            super.dibujarVentanaParaCompra(g, GestorPrincipal.sd);
            super.dibujarVentanaParaVenta(g, GestorPrincipal.sd);

            if (MenuEquipo.mostrarTooltip) {
                super.dibujarTooltipPeso(g, sd);
                dibujarTooltipPaneles(g, sd, actualizarListaConsumibles(objetosTienda), ElementosPrincipales.inventario.getConsumibles());
            }
        }
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
        String textoPrecio = "";
        Consumible consumible = (Consumible) objeto;

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelComprar))) {
            textoPrecio += consumible.getPrecioCompra();
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nPRECIO COMPRA: $" + textoPrecio
                    + "\nPESO: " + consumible.getPeso() + "oz.");

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosComprados))) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nTOTAL: $"
                    + objeto.getPrecioCompra() * objeto.getCantidadCompra());

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelVender))) {

            textoPrecio += consumible.getPrecioVenta();

            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nPRECIO COMPRA: $" + textoPrecio
                    + "\nPESO: " + consumible.getPeso() + "oz.");

        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosVendidos))) {
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nTOTAL: $"
                    + objeto.getPrecioVenta() * objeto.getCantidadVenta());
        }
    }

    @Override
    public void actualizar() {
        if (ElementosPrincipales.mapa.tiendaActiva.getTipo() == 4) {
            objetosTienda = ElementosPrincipales.mapa.getObjetosTiendaActual();
            super.actualizarPosicionesMenu(actualizarListaConsumibles(objetosTienda), ElementosPrincipales.inventario.getConsumibles());
            super.actualizarPosicionesCompraVenta();
            super.actualizarSeleccionRaton(actualizarListaConsumibles(objetosTienda), ElementosPrincipales.inventario.getConsumibles());
            super.actualizarObjetoSeleccionadoCompra();
            super.actualizarObjetoSeleccionadoVenta();
            super.calcularPesoFuturo();
            super.actualizarCanastaCompra(ElementosPrincipales.inventario.getConsumibles());
            super.actualizarCanastaVenta();
        }
    }

    private ArrayList<Objeto> actualizarListaConsumibles(ArrayList<Objeto> objetosTienda) {
        ArrayList<Objeto> objetos = new ArrayList<>();
        for (Objeto objeto : objetosTienda) {
            if (objeto instanceof Consumible) {
                objetos.add(objeto);
            }
        }
        return objetos;
    }
}
