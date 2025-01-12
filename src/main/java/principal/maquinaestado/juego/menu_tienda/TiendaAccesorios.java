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
import principal.inventario.joyas.Joya;
import principal.maquinaestado.menujuego.MenuEquipo;

/**
 * @author GAMER ARRAX
 */
public class TiendaAccesorios extends SeccionTienda {

    // Constructor
    public TiendaAccesorios(String nombreSeccion, Rectangle etiquetaMenu, EstructuraTienda et) {
        super(nombreSeccion, etiquetaMenu, et);
    }

    // Método para actualizar la lógica de la tienda
    @Override
    public void actualizar() {
        if (ElementosPrincipales.mapa.tiendaActiva.getTipo() == 3) {
            objetosTienda = ElementosPrincipales.mapa.getObjetosTiendaActual();
            super.actualizarPosicionesMenu(actualizaListaJoyas(objetosTienda), ElementosPrincipales.inventario.getJoyas(0));
            super.actualizarPosicionesCompraVenta();
            super.actualizarSeleccionRaton(actualizaListaJoyas(objetosTienda), ElementosPrincipales.inventario.getJoyas(0));
            super.actualizarObjetoSeleccionadoCompra();
            super.actualizarObjetoSeleccionadoVenta();
            super.calcularPesoFuturo();
            super.actualizarCanastaCompra(ElementosPrincipales.inventario.getJoyas(0));
            super.actualizarCanastaVenta();
        }
    }

    // Método para dibujar la interfaz de la tienda
    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd) {
        // Dibujar el límite de peso
        super.dibujarLimitePeso(g);
        super.dibujarPaneles(g);

        // Dibujar los paneles de la tienda
        if (ElementosPrincipales.mapa.tiendaActiva.getTipo() == 3) {

            super.dibujarVentanaParaCompra(g, GestorPrincipal.sd);
            super.dibujarVentanaParaVenta(g, GestorPrincipal.sd);
            dibujarElementos(g, objetosTienda, ElementosPrincipales.inventario.getJoyas(0));

            // Si se muestra el tooltip, dibujar tooltips de peso y paneles
            if (MenuEquipo.mostrarTooltip) {
                super.dibujarTooltipPeso(g, sd);
                dibujarTooltipPaneles(g, sd, actualizaListaJoyas(objetosTienda), ElementosPrincipales.inventario.getJoyas(0));
            }
        }

    }

    private ArrayList<Objeto> actualizaListaJoyas(ArrayList<Objeto> objetosTienda) {
        ArrayList<Objeto> objetos = new ArrayList<>();
        for (Objeto objeto : objetosTienda) {
            if (objeto instanceof Joya) {
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
// Obtiene la posición del ratón
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();
        // Variable para almacenar el precio del objeto en texto
        String textoPrecio = "";

        // Verifica si el ratón está sobre el panel de compra
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelComprar))) {
            // Si el objeto es una joya
            if (objeto instanceof Joya accesorio) {
                // Obtiene el precio de compra de la joya
                textoPrecio += accesorio.getPrecioCompra();
                // Dibuja un tooltip detallado con información sobre la joya
                GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nPRECIO COMPRA: $" + textoPrecio
                        + "\nDEFENSA FISICA: " + accesorio.getDefensaF() + "\nDEFENSA MAGICA: " + accesorio.getDefensaM()
                        + "\nPESO: " + accesorio.getPeso() + "oz.");
            }
        }
        // Verifica si el ratón está sobre el panel de objetos comprados
        else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosComprados))) {
            // Dibuja un tooltip con el total gastado en los objetos comprados
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nTOTAL: $"
                    + objeto.getPrecioCompra() * objeto.getCantidadCompra());
        }
        // Verifica si el ratón está sobre el panel de venta
        else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelVender))) {
            // Si el objeto es una joya
            if (objeto instanceof Joya accesorio) {
                // Obtiene el precio de venta de la joya
                textoPrecio += accesorio.getPrecioVenta();
                // Dibuja un tooltip detallado con información sobre la joya
                GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nPRECIO COMPRA: $" + textoPrecio
                        + "\nDEFENSA FISICA: " + accesorio.getDefensaF() + "\nDEFENSA MAGICA: " + accesorio.getDefensaM()
                        + "\nPESO: " + accesorio.getPeso() + "oz.");
            }
        }
        // Verifica si el ratón está sobre el panel de objetos vendidos
        else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosVendidos))) {
            // Dibuja un tooltip con el total ganado por los objetos vendidos
            GeneradorTooltip.dibujarTooltipMejorado(g, sd, objeto.getNombre() + "\nTOTAL: $"
                    + objeto.getPrecioVenta() * objeto.getCantidadVenta());
        }
    }
}
