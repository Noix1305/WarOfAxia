/*
 * SeccionTienda.java
 * Clase abstracta que representa una sección de la tienda en el juego.
 */
package principal.maquinaestado.juego.menu_tienda;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.herramientas.GeneradorTooltip;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;

public abstract class SeccionTienda {

    protected int margenGeneral = 8;
    protected String nombreSeccion;
    protected Rectangle etiquetaMenu;
    protected Rectangle barraPeso;
    protected EstructuraTienda et;
    protected Objeto objetoSeleccionado;

    protected final int anchoPaneles = 110;
    protected final int altoPaneles = 308;

    // Rectángulos para los paneles de compra y objetos comprados
    protected Rectangle panelComprar;
    protected Rectangle panelObjetosComprados;

    // Variables de control para la interfaz de compra
    protected boolean dibujarVentanaCompra = false;
    protected boolean sinDinero = false;
    protected boolean comprando = false;
    protected boolean vendiendo = false;
    protected boolean excederiaPeso = false;

    // Rectángulos para los paneles de venta y objetos vendidos
    protected Rectangle panelVender;
    protected Rectangle panelObjetosVendidos;

    // Titulares de los paneles
    protected Rectangle titularPanelComprar;
    protected Rectangle titularPanelVender;
    protected Rectangle titularPanelComprados;
    protected Rectangle titularPanelVenta;

    // Objeto seleccionado para compra y venta
    protected Objeto objetoSeleccionadoCompra;
    protected Objeto objetoSeleccionadoVenta;

    // Listas para los objetos en la canasta de compra y venta
    protected ArrayList<Objeto> canastaCompra;
    protected ArrayList<Objeto> canastaVenta;
    protected ArrayList<Objeto> objetosTienda;

    // Posiciones anteriores para botones de venta y compra
    protected int anteriorXVenta;
    protected int anteriorYVenta;
    protected int anteriorXCompra;
    protected int anteriorYCompra;

    // Rectángulos para botones de operaciones y cantidad de objetos
    protected Rectangle vender;
    protected Rectangle comprar;
    protected Rectangle cancelarVenta;
    protected Rectangle cancelarCompra;
    protected Rectangle ventanaCantidad;
    protected Rectangle subirUnidad;
    protected Rectangle bajarUnidad;
    protected Rectangle subirDecena;
    protected Rectangle bajarDecena;
    protected Rectangle aceptarOperacion;

    // Contadores y variables relacionadas con la cantidad de objetos y transacciones
    protected int cantidadObjetos;
    protected int totalTransaccionCompra;
    protected int totalTransaccionVenta;

    // Variables de tiempo para evitar rebotes en la interfaz
    protected long tiempoUltimaAccion = 0;
    protected long tiempoDebouncing = 100;

    // Constructor
    public SeccionTienda() {

    }

    public SeccionTienda(final String nombreSeccion, final Rectangle etiquetaMenu, final EstructuraTienda et) {
        this.nombreSeccion = nombreSeccion;
        this.etiquetaMenu = etiquetaMenu;
        int anchoBarra = 100;
        this.et = et;
        barraPeso = new Rectangle(Constantes.ANCHO_JUEGO - anchoBarra + margenGeneral - 20, et.BANNER_SUPERIOR.height + margenGeneral,
                100, 8);
        this.panelComprar = new Rectangle(et.FONDO.x + margenGeneral * 2,
                barraPeso.y + barraPeso.height + margenGeneral,
                anchoPaneles, altoPaneles);
        panelObjetosComprados = new Rectangle(panelComprar.x + panelComprar.width + margenGeneral,
                panelComprar.y, anchoPaneles, altoPaneles);
        panelVender = new Rectangle(panelObjetosComprados.x + panelObjetosComprados.width + margenGeneral,
                panelObjetosComprados.y, anchoPaneles, altoPaneles);
        panelObjetosVendidos = new Rectangle(panelVender.x + panelVender.width + margenGeneral,
                panelVender.y, anchoPaneles, altoPaneles);

        // Inicialización de los rectángulos titulares de los paneles
        titularPanelComprar = new Rectangle(panelComprar.x, panelComprar.y, panelComprar.width, 24);
        titularPanelVender = new Rectangle(panelVender.x, panelVender.y, panelVender.width, 24);
        titularPanelComprados = new Rectangle(panelObjetosComprados.x, panelObjetosComprados.y, panelObjetosComprados.width, 24);
        titularPanelVenta = new Rectangle(panelObjetosVendidos.x, panelObjetosVendidos.y, panelObjetosVendidos.width, 24);
        canastaCompra = new ArrayList<>();
        canastaVenta = new ArrayList<>();
        objetoSeleccionadoCompra = null;
        objetoSeleccionadoVenta = null;
        anteriorXVenta = panelObjetosVendidos.x + Constantes.LADO_SPRITE / 2;
        anteriorYVenta = panelObjetosVendidos.y + panelObjetosVendidos.height - (Constantes.LADO_SPRITE / 2);
        anteriorXCompra = panelObjetosComprados.x + Constantes.LADO_SPRITE / 2;
        anteriorYCompra = panelObjetosComprados.y + panelObjetosComprados.height - (Constantes.LADO_SPRITE / 2);
        vender = new Rectangle(anteriorXVenta - 2, anteriorYVenta, 40, 12);
        comprar = new Rectangle(anteriorXCompra - 2, anteriorYCompra, 40, 12);
        cancelarVenta = new Rectangle(vender.x + vender.width + margenGeneral, vender.y, 40, 12);
        cancelarCompra = new Rectangle(comprar.x + comprar.width + margenGeneral, comprar.y, 40, 12);

        cantidadObjetos = 0;
        totalTransaccionCompra = 0;
        totalTransaccionVenta = 0;
    }

    // Métodos abstractos que deben ser implementados por las clases hijas
    public abstract void actualizar();

    public abstract void dibujar(final Graphics g, final SuperficieDibujo sd);

    protected void actualizarPosicionesMenu(ArrayList<Objeto> objetos, ArrayList<Objeto> objetosInventario) {

        final Point piObjetosTienda = new Point(et.FONDO.x + 10,
                titularPanelComprar.y + margenGeneral * 3);
        final Point piObjetosInventario = new Point(panelVender.x - 6,
                titularPanelVender.y + margenGeneral * 3);

        final int lado = Constantes.LADO_SPRITE;
        int contadorObjetosTienda = 0;
        int contadorObjetosInventario = 0;
        int margenX = 8; // Nuevo margen desde el borde del panel

        if (!objetos.isEmpty()) {

            for (Objeto objetoActual : objetos) {

                // Cálculo de la posición X ajustado para el margen desde el borde del panel
                int posX = piObjetosTienda.x + margenX + (contadorObjetosTienda % 3) * (lado + margenGeneral / 2);
                int posY = piObjetosTienda.y + contadorObjetosTienda / 3 * (lado + margenGeneral / 2);
                Rectangle nuevaPosicionTienda = new Rectangle(posX, posY, lado, lado);
                objetoActual.setPosicionTienda(nuevaPosicionTienda);
                contadorObjetosTienda++;
            }
        }

        if (!objetosInventario.isEmpty()) {

            for (Objeto objetoActual : objetosInventario) {
                if (objetoNoVendible(objetoActual.getId())) {
                    objetoActual.setPosicionMochila(new Rectangle(0, 0, lado, lado));
                    continue;
                }

                // Cálculo de la posición X ajustado para el margen desde el borde del panel
                int posX = piObjetosInventario.x + margenX + (contadorObjetosInventario % 3) * (lado + margenGeneral / 2);
                int posY = piObjetosInventario.y + contadorObjetosInventario / 3 * (lado + margenGeneral / 2);
                Rectangle nuevaPosicionInventario = new Rectangle(posX, posY, lado, lado);
                objetoActual.setPosicionMochila(nuevaPosicionInventario);
                contadorObjetosInventario++;

            }
        }
    }

    protected void actualizarPosicionesCompraVenta() {

        final Point piObjetosCompra = new Point(panelObjetosComprados.x - 6,
                titularPanelComprados.y + margenGeneral * 3);
        final Point piObjetosVenta = new Point(panelObjetosVendidos.x - 6,
                titularPanelVenta.y + margenGeneral * 3);

        final int lado = Constantes.LADO_SPRITE;
        int contadorObjetosComprados = 0;
        int contadorObjetosVendidos = 0;
        int margenX = 8; // Nuevo margen desde el borde del panel

        if (!canastaCompra.isEmpty()) {

            for (Objeto objetoActual : canastaCompra) {
                // Cálculo de la posición X ajustado para el margen desde el borde del panel
                int posX = piObjetosCompra.x + margenX + (contadorObjetosComprados % 3) * (lado + margenGeneral / 2);
                int posY = piObjetosCompra.y + contadorObjetosComprados / 3 * (lado + margenGeneral / 2);
                Rectangle nuevaPosicionCompra = new Rectangle(posX, posY, lado, lado);
                objetoActual.setPosicionCompra(nuevaPosicionCompra);
                contadorObjetosComprados++;
            }
        }

        if (!canastaVenta.isEmpty()) {

            for (Objeto objetoActual : canastaVenta) {
                // Cálculo de la posición X ajustado para el margen desde el borde del panel
                int posX = piObjetosVenta.x + margenX + (contadorObjetosVendidos % 3) * (lado + margenGeneral / 2);
                int posY = piObjetosVenta.y + contadorObjetosVendidos / 3 * (lado + margenGeneral / 2);
                Rectangle nuevaPosicionVenta = new Rectangle(posX, posY, lado, lado);
                objetoActual.setPosicionVenta(nuevaPosicionVenta);
                contadorObjetosVendidos++;
            }
        }
    }

    protected void actualizarSeleccionRaton(ArrayList<Objeto> objetosTienda, ArrayList<Objeto> objetosInventario) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        if (objetoSeleccionadoCompra == null) {
            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelComprar))) {
                if (objetosTienda.isEmpty()) {
                    return;
                }
                for (Objeto objeto : objetosTienda) {
                    if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton
                            .intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionTienda()))) {
                        objetoSeleccionadoCompra = objeto;
                        objetoSeleccionadoVenta = null;
                    }
                }
            }

        }
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelVender))) {
            if (objetosInventario.isEmpty()) {
                return;
            }
            for (Objeto objeto : objetosInventario) {
                if (GestorPrincipal.sd.getRaton().isClick() && posicionRaton
                        .intersects(EscaladorElementos.escalarRectangleArriba(objeto.getPosicionMochila()))) {
                    objetoSeleccionadoVenta = objeto;
                    objetoSeleccionadoCompra = null;
                }
            }
        }

    }

    protected void actualizarObjetoSeleccionadoCompra() {
        if (objetoSeleccionadoCompra != null) {
            if (GestorPrincipal.sd.getRaton().isClick2()) {
                objetoSeleccionadoCompra = null;
                return;
            }
            Point pfc = EscaladorElementos.escalarAbajo(GestorPrincipal.sd.getRaton().getPosicion());
            objetoSeleccionadoCompra.setPosicionFlotante(
                    new Rectangle(pfc.x, pfc.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));
        }

    }

    protected void actualizarObjetoSeleccionadoVenta() {

        if (objetoSeleccionadoVenta != null) {
            if (GestorPrincipal.sd.getRaton().isClick2()) {
                objetoSeleccionadoVenta = null;
                return;
            }

            Point pfv = EscaladorElementos.escalarAbajo(GestorPrincipal.sd.getRaton().getPosicion());
            objetoSeleccionadoVenta.setPosicionFlotante(
                    new Rectangle(pfv.x, pfv.y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));
        }

    }

    protected void calcularPesoFuturo() {

        int pesoFuturo = 0;

        for (Objeto objetoInventario : ElementosPrincipales.inventario.getListaObjetos()) {
            pesoFuturo += (int) (objetoInventario.getPeso() * objetoInventario.getCantidad());
        }

        for (Objeto objetoCanasta : canastaCompra) {

            pesoFuturo += (int) (objetoCanasta.getPeso() * objetoCanasta.getCantidadCompra());
        }

        if (pesoFuturo > ElementosPrincipales.jugador.getGestorAt().getLimitePeso() && !canastaCompra.isEmpty()) {
            excederiaPeso = true;
        } else if (pesoFuturo < ElementosPrincipales.jugador.getGestorAt().getLimitePeso()) {
            excederiaPeso = false;
        }
    }

    protected void actualizarCanastaCompra(ArrayList<Objeto> objetos) {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        long tiempoActual = System.currentTimeMillis();

        // Verifica si ha pasado suficiente tiempo desde la última recogidass

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosComprados))
                && objetoSeleccionadoCompra != null && GestorPrincipal.sd.getRaton().isClick()) {
            System.out.println("Objeto en canasta compra");
            if (ElementosPrincipales.jugador.getAccionesJugador().isSobrepeso()) {
                return;
            }
            ventanaCantidad = new Rectangle(panelObjetosComprados.x, panelObjetosComprados.height / 2, 42, 40);
            subirUnidad = new Rectangle(ventanaCantidad.x + 2, ventanaCantidad.y + 2, 18, 17);
            bajarUnidad = new Rectangle(ventanaCantidad.x + 2, subirUnidad.y + subirUnidad.height + 2, 18, 17);
            subirDecena = new Rectangle(subirUnidad.x + 2 + subirUnidad.width, subirUnidad.y, 18, 17);
            bajarDecena = new Rectangle(subirDecena.x, subirDecena.y + bajarUnidad.height + 2, 18, 17);
            aceptarOperacion = new Rectangle(ventanaCantidad.x + ventanaCantidad.width + 2, ventanaCantidad.y, 42, 20);
            dibujarVentanaCompra = true;
            comprando = true;

            if (tiempoActual - tiempoUltimaAccion < tiempoDebouncing) {
                return; // Ignora la recogida si está dentro del tiempo de debouncing
            }

            // Actualiza el tiempo de la última recogida
            tiempoUltimaAccion = tiempoActual;

            if (canastaCompra.contains(objetoSeleccionadoCompra)) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos++;
                    System.out.println(cantidadObjetos);

                }
                else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos += 10;
                }
                else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos--;

                }
                else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos -= 10;

                }

                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(aceptarOperacion))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    for (Objeto objetoCanasta : canastaCompra) {
                        if (objetoCanasta.equals(objetoSeleccionadoCompra)) {

                            objetoCanasta.setCantidadCompra(objetoCanasta.getCantidadCompra() + cantidadObjetos);
                            if (objetoCanasta.getCantidadCompra() < 0) {
                                objetoCanasta.setCantidadCompra(0);
                            }
                        }
                    }
                    totalTransaccionCompra += cantidadObjetos * objetoSeleccionadoCompra.getPrecioCompra();
                    dibujarVentanaCompra = false;
                    objetoSeleccionadoCompra = null;
                    cantidadObjetos = 0;
                }
            }
            else {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos++;
                }
                else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos += 10;
                }
                else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos--;
                    if (cantidadObjetos < 0) {
                        cantidadObjetos = 0;
                    }
                }
                else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos -= 10;
                    if (cantidadObjetos < 0) {
                        cantidadObjetos = 0;
                    }

                }
                objetoSeleccionadoCompra.setCantidadCompra(cantidadObjetos);
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(aceptarOperacion))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    if (cantidadObjetos == 0) {
                        dibujarVentanaCompra = false;
                        objetoSeleccionadoCompra = null;
                        return;
                    }
                    canastaCompra.add(objetoSeleccionadoCompra);
                    totalTransaccionCompra += objetoSeleccionadoCompra.getCantidadCompra() * objetoSeleccionadoCompra.getPrecioCompra();
                    dibujarVentanaCompra = false;
                    objetoSeleccionadoCompra = null;
                    cantidadObjetos = 0;
                }
            }

        }
        else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(comprar))
                && !canastaCompra.isEmpty() && GestorPrincipal.sd.getRaton().isClick()) {

            if (excederiaPeso) {
                return;
            }

            if (ElementosPrincipales.inventario.dinero < totalTransaccionCompra) {
                System.out.println("Dinero Insuficiente");
                sinDinero = true;
                return;
            }
            else {
                sinDinero = false;
            }

            boolean objetoExiste = false;
            for (Objeto objetoComprado : canastaCompra) {

                for (Objeto objetoInventario : objetos) {
                    if (objetoInventario.getId() == objetoComprado.getId()) {
                        objetoInventario.setCantidad(objetoInventario.getCantidad() + objetoComprado.getCantidadCompra());
                        objetoExiste = true;
                    }
                }
                if (!objetoExiste) {
                    objetoComprado.setCantidad(objetoComprado.getCantidadCompra());
                    ElementosPrincipales.inventario.getListaObjetos().add(objetoComprado);

                }
            }

            ElementosPrincipales.inventario.dinero -= totalTransaccionCompra;
            System.out.println("Dinero: " + ElementosPrincipales.inventario.dinero);

            comprando = false;
            totalTransaccionCompra = 0;
            canastaCompra.clear();
        }
        else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(cancelarCompra))
                && !canastaCompra.isEmpty() && GestorPrincipal.sd.getRaton().isClick()) {
            comprando = false;
            sinDinero = false;
            totalTransaccionCompra = 0;
            canastaCompra.clear();
        }
        else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosComprados))
                && !canastaCompra.isEmpty()) {

            if (tiempoActual - tiempoUltimaAccion < tiempoDebouncing) {
                return; // Ignora la recogida si está dentro del tiempo de debouncing
            }

            // Actualiza el tiempo de la última recogida
            tiempoUltimaAccion = tiempoActual;

            Iterator<Objeto> iterador = canastaCompra.iterator();

            while (iterador.hasNext()) {
                Objeto objetoActual = iterador.next();

                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objetoActual.getPosicionCompra()))
                        && GestorPrincipal.sd.getRaton().isClick2()) {
                    totalTransaccionCompra -= objetoActual.getCantidadCompra() * objetoActual.getPrecioCompra();
                    iterador.remove();
                    if (totalTransaccionCompra <= ElementosPrincipales.inventario.dinero) {
                        sinDinero = false;
                    }
                    break; // Salir del bucle después de recoger un objeto
                }
            }
        }
    }

    protected void actualizarCanastaVenta() {
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();

        long tiempoActual = System.currentTimeMillis();

        // Verifica si ha pasado suficiente tiempo desde la última recogida
        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosVendidos))
                && objetoSeleccionadoVenta != null && GestorPrincipal.sd.getRaton().isClick()) {
            ventanaCantidad = new Rectangle(panelObjetosVendidos.x, panelObjetosVendidos.height / 2, 42, 40);
            subirUnidad = new Rectangle(ventanaCantidad.x + 2, ventanaCantidad.y + 2, 18, 17);
            bajarUnidad = new Rectangle(ventanaCantidad.x + 2, subirUnidad.y + subirUnidad.height + 2, 18, 17);
            subirDecena = new Rectangle(subirUnidad.x + 2 + subirUnidad.width, subirUnidad.y, 18, 17);
            bajarDecena = new Rectangle(subirDecena.x, subirDecena.y + bajarUnidad.height + 2, 18, 17);
            aceptarOperacion = new Rectangle(ventanaCantidad.x + ventanaCantidad.width + 2, ventanaCantidad.y, 42, 20);
            dibujarVentanaCompra = true;
            vendiendo = true;

            if (tiempoActual - tiempoUltimaAccion < tiempoDebouncing) {
                return; // Ignora la recogida si está dentro del tiempo de debouncing
            }

            // Actualiza el tiempo de la última recogida
            tiempoUltimaAccion = tiempoActual;
            if (canastaVenta.contains(objetoSeleccionadoVenta)) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos++;
                    if (cantidadObjetos > objetoSeleccionadoVenta.getCantidad()) {
                        cantidadObjetos = objetoSeleccionadoVenta.getCantidad();
                    }

                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos += 10;
                    if (cantidadObjetos > objetoSeleccionadoVenta.getCantidad()) {
                        cantidadObjetos = objetoSeleccionadoVenta.getCantidad();
                    }

                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos--;

                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos -= 10;

                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(aceptarOperacion))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    for (Objeto objetoCanasta : canastaVenta) {
                        if (objetoCanasta.getId() == objetoSeleccionadoVenta.getId()) {

                            objetoCanasta.setCantidadVenta(objetoCanasta.getCantidadVenta() + cantidadObjetos);
                            totalTransaccionVenta += cantidadObjetos * objetoCanasta.getPrecioVenta();
                            objetoSeleccionadoVenta.setCantidad(objetoSeleccionadoVenta.getCantidad() - cantidadObjetos);
                            if (objetoCanasta.getCantidadVenta() < 0) {
                                objetoCanasta.setCantidadVenta(0);
                            }
                        }
                    }

                    dibujarVentanaCompra = false;
                    objetoSeleccionadoVenta = null;
                    cantidadObjetos = 0;
                }
            } else {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos++;
                    if (cantidadObjetos > objetoSeleccionadoVenta.getCantidad()) {
                        cantidadObjetos = objetoSeleccionadoVenta.getCantidad();
                    }

                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(subirDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos += 10;
                    if (cantidadObjetos > objetoSeleccionadoVenta.getCantidad()) {
                        cantidadObjetos = objetoSeleccionadoVenta.getCantidad();
                    }

                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarUnidad))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos--;
                    if (cantidadObjetos < 0) {
                        cantidadObjetos = 0;
                    }
                } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(bajarDecena))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    cantidadObjetos -= 10;
                    if (cantidadObjetos < 0) {
                        cantidadObjetos = 0;
                    }

                }

                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(aceptarOperacion))
                        && GestorPrincipal.sd.getRaton().isClick()) {
                    objetoSeleccionadoVenta.setCantidadVenta(cantidadObjetos);
                    objetoSeleccionadoVenta.setCantidad(objetoSeleccionadoVenta.getCantidad() - cantidadObjetos);
                    if (cantidadObjetos == 0) {
                        dibujarVentanaCompra = false;
                        objetoSeleccionadoVenta = null;
                        return;
                    }
                    totalTransaccionVenta += objetoSeleccionadoVenta.getCantidadVenta() * objetoSeleccionadoVenta.getPrecioVenta();
                    canastaVenta.add(objetoSeleccionadoVenta);
                    dibujarVentanaCompra = false;
                    objetoSeleccionadoVenta = null;
                    cantidadObjetos = 0;
                }
            }
        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(vender))
                && !canastaVenta.isEmpty() && GestorPrincipal.sd.getRaton().isClick()) {

            Iterator<Objeto> iterador = ElementosPrincipales.inventario.getListaObjetos().iterator();

            while (iterador.hasNext()) {
                Objeto objetoActual = iterador.next();

                if (objetoActual.getCantidad() <= 0) {
                    iterador.remove();
                    break; // Salir del bucle después de recoger un objeto
                }
            }
            ElementosPrincipales.inventario.dinero += totalTransaccionVenta;
            System.out.println("" + ElementosPrincipales.inventario.dinero);
            vendiendo = false;
            totalTransaccionVenta = 0;
            canastaVenta.clear();
        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(cancelarVenta))
                && !canastaVenta.isEmpty() && GestorPrincipal.sd.getRaton().isClick()) {
            for (Objeto objetoCanasta : canastaVenta) {
                int idObjeto = objetoCanasta.getId();
                for (Objeto objetoInventario : ElementosPrincipales.inventario.getListaObjetos()) {
                    if (idObjeto == objetoInventario.getId()) {
                        objetoInventario.setCantidad(objetoInventario.getCantidad() + objetoCanasta.getCantidadVenta());
                    }
                }
            }
            vendiendo = false;
            totalTransaccionVenta = 0;
            canastaVenta.clear();
        } else if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(panelObjetosVendidos))
                && !canastaVenta.isEmpty()) {

            if (tiempoActual - tiempoUltimaAccion < tiempoDebouncing) {
                return; // Ignora la recogida si está dentro del tiempo de debouncing
            }

            // Actualiza el tiempo de la última recogida
            tiempoUltimaAccion = tiempoActual;

            Iterator<Objeto> iterador = canastaVenta.iterator();

            while (iterador.hasNext()) {
                Objeto objetoActual = iterador.next();

                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(objetoActual.getPosicionVenta()))
                        && GestorPrincipal.sd.getRaton().isClick2()) {
                    int idObjeto = objetoActual.getId();
                    for (Objeto objetoInventario : ElementosPrincipales.inventario.getListaObjetos()) {
                        if (idObjeto == objetoInventario.getId()) {
                            objetoInventario.setCantidad(objetoInventario.getCantidad() + objetoActual.getCantidadVenta());
                            totalTransaccionVenta -= objetoActual.getCantidadVenta() * objetoActual.getPrecioVenta();
                        }
                    }
                    iterador.remove();

                    break; // Salir del bucle después de recoger un objeto
                }
            }
        }

    }

    // Métodos de dibujo de etiquetas de secciones
    public void dibujarEtiquetaInactiva(final Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 16, etiquetaMenu.y + 12, Color.black);
    }

    public void dibujarEtiquetaActiva(Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);

        final Rectangle marcaActiva = new Rectangle(etiquetaMenu.x, etiquetaMenu.y, 5, etiquetaMenu.height);
        DibujoDebug.dibujarRectanguloRelleno(g, marcaActiva, new Color(0xff6700));

        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 16, etiquetaMenu.y + 12, Color.black);
    }

    public void dibujarEtiquetaInactResaltada(final Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaMenu, Color.white);

        final Rectangle etiquetaResaltada = new Rectangle(etiquetaMenu.x + etiquetaMenu.width - 10, etiquetaMenu.y + 5,
                5, etiquetaMenu.height - 10);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaResaltada, new Color(0x2a2a2a));
        DibujoDebug.dibujarString(g, nombreSeccion, etiquetaMenu.x + 15, etiquetaMenu.y + 12, Color.black);
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

    // Método para obtener la etiqueta de menú escalada
    public Rectangle getEtiquetaMenuEscalada() {
        final int x = (int) (etiquetaMenu.x * Constantes.FACTOR_ESCALADO_X);
        final int y = (int) (etiquetaMenu.y * Constantes.FACTOR_ESCALADO_Y);
        final int ancho = (int) (etiquetaMenu.width * Constantes.FACTOR_ESCALADO_X);
        final int alto = (int) (etiquetaMenu.height * Constantes.FACTOR_ESCALADO_Y);

        final Rectangle etiquetaEscalada = new Rectangle(x, y, ancho, alto);

        return etiquetaEscalada;
    }

    protected abstract void dibujarTooltipPaneles(final Graphics g, final SuperficieDibujo sd, ArrayList<Objeto> objetosTienda, ArrayList<Objeto> objetos);


    public void dibujarTooltipPeso(final Graphics g, SuperficieDibujo sd) {
        String textoCarga = String.format("%.1f", ElementosPrincipales.jugador.getGestorAt().getPesoActual());
        String textoCargaTotal = String.format("%.1f", ElementosPrincipales.jugador.getGestorAt().getLimitePeso());
        String textoFinal = textoCarga + "/" + textoCargaTotal;
        if (sd.getRaton().getPosicionRectangle().intersects(EscaladorElementos.escalarRectangleArriba(barraPeso))) {
            GeneradorTooltip.dibujarTooltip(g, sd, textoFinal);
        }

    }

    protected abstract void dibujarTooltipObjeto(Graphics g, SuperficieDibujo sd, Objeto objeto);


    // Método para dibujar la barra de peso
    protected void dibujarLimitePeso(final Graphics g) {
        ElementosPrincipales.jugador.calcularPesoActual();

        Color color = new Color(255, 255, 255);
        String carga = "CARGA";
        int x = barraPeso.x - 35;

        // Calcular el porcentaje de peso actual en relación con el límite de peso
        double porcentajePeso = (ElementosPrincipales.jugador.getGestorAt().getPesoActual() * 100)
                / ElementosPrincipales.jugador.getGestorAt().getLimitePeso();

        // Calcular la longitud de la parte coloreada de la barra
        int longitudColoreada = (int) ((porcentajePeso / 100) * (barraPeso.width - 2));
        if (ElementosPrincipales.jugador.getAccionesJugador().isSobrepeso()) {
            longitudColoreada = 100;
        }

        final Rectangle contenidoBarra = new Rectangle(barraPeso.x + 1, barraPeso.y + 1, longitudColoreada, barraPeso.height - 2);

        if (porcentajePeso < 25) {
            color = new Color(0, 255, 0);
        } else if (porcentajePeso >= 25 && porcentajePeso < 50) {
            color = Color.yellow;
        } else if (porcentajePeso >= 50 && porcentajePeso < 100) {
            color = new Color(255, 100, 50);
        } else {
            color = Color.red;
            carga = "SOBREPESO";
            x = barraPeso.x - 60;
        }
        DibujoDebug.dibujarString(g, carga, x, barraPeso.y + 7, color);
        DibujoDebug.dibujarRectanguloRelleno(g, barraPeso, Color.gray);

        DibujoDebug.dibujarRectanguloRelleno(g, contenidoBarra, color);
    }

    // Método para verificar si un objeto es no vendible
    public boolean objetoNoVendible(int idObjeto) {
        boolean flag = false;
        for (Objeto objetoEquipado : ElementosPrincipales.jugador.getAlmacenEquipo().getEquipoActual()) {
            if (objetoEquipado.getId() == idObjeto) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    protected void dibujarPaneles(Graphics g) {
        dibujarPanelComprar(g, panelComprar, titularPanelComprar, "TIENDA");
        dibujarPanelComprados(g, panelObjetosComprados, titularPanelComprados, "CANASTA COMPRA");
        dibujarPanelVender(g, panelVender, titularPanelVender, "MOCHILA");
        dibujarPanelObjetosVendidos(g, panelObjetosVendidos, titularPanelVenta, "CANASTA VENTA");
    }

    protected void dibujarElementos(Graphics g, ArrayList<Objeto> objetosTienda, ArrayList<Objeto> objetosInventario) {
        dibujarElementosTienda(g, objetosTienda);
        dibujarElementosInventario(g, objetosInventario);
        dibujarElementosCanastaCompra(g, canastaCompra);
        dibujarElementosCanastaVenta(g);
    }

    private void dibujarPanelComprar(Graphics g, Rectangle panelComprar, Rectangle titularPanelComprar, String nombrePanel) {
        dibujarPanel(g, panelComprar, titularPanelComprar, nombrePanel);
    }

    private void dibujarPanelVender(Graphics g, Rectangle panelVender, Rectangle titularPanelVender, String nombrePanel) {
        dibujarPanel(g, panelVender, titularPanelVender, nombrePanel);
    }

    private void dibujarPanelComprados(Graphics g, Rectangle panelComprados, Rectangle titularPanelComprados, String nombrePanel) {
        dibujarPanel(g, panelComprados, titularPanelComprados, nombrePanel);
    }

    private void dibujarPanelObjetosVendidos(Graphics g, Rectangle panelVendidos, Rectangle titularPanelVendidos, String nombrePanel) {
        dibujarPanel(g, panelVendidos, titularPanelVendidos, nombrePanel);
    }

    private void dibujarElementosTienda(final Graphics g, ArrayList<Objeto> objetos) {
        int lado = Constantes.LADO_SPRITE;

        dibujarElementoEnPanelTienda(g, objetos, lado);
        if (objetoSeleccionado != null) {
            DibujoDebug.dibujarImagen(g, objetoSeleccionado.getSprite().getImagen(),
                    new Point(objetoSeleccionado.getPosicionFlotante().x,
                            objetoSeleccionado.getPosicionFlotante().y));
        }
    }

    private void dibujarElementosCanastaVenta(final Graphics g) {
        int lado = Constantes.LADO_SPRITE;
        // Dibujar los botones "VENDER" y "CANCELAR"
        DibujoDebug.dibujarRectanguloContorno(g, vender, Color.blue);
        DibujoDebug.dibujarRectanguloContorno(g, cancelarVenta);
        g.setColor(Color.BLACK);
        DibujoDebug.dibujarString(g, "VENDER", vender.x + 6, vender.y + vender.height - 4);
        DibujoDebug.dibujarString(g, "CANCELAR", cancelarVenta.x + 2, cancelarVenta.y + cancelarVenta.height - 4);

        // Mostrar el total de la transacción de venta si se está vendiendo y la canasta no está vacía
        if (vendiendo && !canastaVenta.isEmpty()) {
            DibujoDebug.dibujarString(g, "$" + totalTransaccionVenta, titularPanelVenta.x + titularPanelVenta.width / 2,
                    titularPanelVenta.y + titularPanelVenta.height - 2, Color.WHITE);
        }
        // Dibujar el objeto seleccionado de la canasta de venta si existe
        if (objetoSeleccionadoVenta != null) {
            DibujoDebug.dibujarImagen(g, objetoSeleccionadoVenta.getSprite().getImagen(),
                    new Point(objetoSeleccionadoVenta.getPosicionFlotante().x,
                            objetoSeleccionadoVenta.getPosicionFlotante().y));
        }
        for (Objeto objeto : canastaVenta) {
            dibujarElementoCanastaVenta(g, objeto, lado);
        }

    }

    private void dibujarElementoCanastaVenta(final Graphics g, Objeto objetoActual, int lado) {

        Rectangle posicionVenta = objetoActual.getPosicionVenta();

        DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(), posicionVenta.x, posicionVenta.y);

        String texto = "";
        if (objetoActual.getCantidadVenta() < 10) {
            texto = "0" + objetoActual.getCantidadVenta();
        } else {
            texto = "" + objetoActual.getCantidadVenta();
        }

        g.setColor(Color.BLACK);
        DibujoDebug.dibujarRectanguloRelleno(g, posicionVenta.x + lado - 12, posicionVenta.y + 32 - 8, 12, 8);

        g.setColor(Color.WHITE);

        // Ajusta la posición del texto de acuerdo con la posición del objeto
        int xTexto = posicionVenta.x + lado - MedidorString.medirAnchoPixeles(g, texto);
        int yTexto = posicionVenta.y + 31;

        DibujoDebug.dibujarString(g, texto, xTexto, yTexto);
    }


    private void dibujarElementoEnPanelTienda(final Graphics g, ArrayList<Objeto> objetos, int lado) {

        for (Objeto objeto : objetos) {
            Rectangle posicionMenu = objeto.getPosicionTienda();
            DibujoDebug.dibujarImagen(g, objeto.getSprite().getImagen(), posicionMenu.x, posicionMenu.y);
            String texto = objeto.getPrecioCompra() < 10 ? "$0" + objeto.getPrecioCompra() : "$" + objeto.getPrecioCompra();
            g.setColor(Color.BLACK);
            DibujoDebug.dibujarRectanguloRelleno(g, posicionMenu.x + 4, posicionMenu.y + 32 - 8, 32, 8);
            g.setColor(Color.WHITE);
            int xTexto = posicionMenu.x + lado - MedidorString.medirAnchoPixeles(g, texto);
            int yTexto = posicionMenu.y + 31;
            Color colorTexto = ElementosPrincipales.inventario.dinero < objeto.getPrecioCompra() ? Color.RED : Color.WHITE;
            DibujoDebug.dibujarString(g, texto, xTexto, yTexto, colorTexto);
        }

    }

    private void dibujarElementosInventario(final Graphics g, ArrayList<Objeto> objetos) {
        int lado = Constantes.LADO_SPRITE;

        dibujarElementosEnPanelInventario(g, objetos, lado);
        if (objetoSeleccionadoVenta != null) {
            DibujoDebug.dibujarImagen(g, objetoSeleccionadoVenta.getSprite().getImagen(),
                    new Point(objetoSeleccionadoVenta.getPosicionFlotante().x,
                            objetoSeleccionadoVenta.getPosicionFlotante().y));
        }
    }

    private void dibujarElementosEnPanelInventario(final Graphics g, ArrayList<Objeto> objetos, int lado) {

        for (Objeto objeto : objetos) {
            Rectangle posicionMenu = objeto.getPosicionMochila();
            DibujoDebug.dibujarImagen(g, objeto.getSprite().getImagen(), posicionMenu.x, posicionMenu.y);
            String texto = objeto.getCantidad() < 10 ? "0" + objeto.getCantidad() : String.valueOf(objeto.getCantidad());
            g.setColor(Color.BLACK);
            DibujoDebug.dibujarRectanguloRelleno(g, posicionMenu.x + lado - 12, posicionMenu.y + 32 - 8, 12, 8);
            g.setColor(Color.WHITE);
            int xTexto = posicionMenu.x + lado - MedidorString.medirAnchoPixeles(g, texto) - 2;
            int yTexto = posicionMenu.y + 31;
            DibujoDebug.dibujarString(g, texto, xTexto, yTexto);
        }
    }

    private void dibujarElementosCanastaCompra(final Graphics g, ArrayList<Objeto> objetos) {
        int lado = Constantes.LADO_SPRITE;

        // Dibujar los botones "COMPRAR" y "CANCELAR"
        DibujoDebug.dibujarRectanguloContorno(g, comprar, Color.blue);
        DibujoDebug.dibujarRectanguloContorno(g, cancelarCompra);
        g.setColor(Color.BLACK);
        DibujoDebug.dibujarString(g, "COMPRAR", comprar.x + 6, comprar.y + comprar.height - 4);
        DibujoDebug.dibujarString(g, "CANCELAR", cancelarCompra.x + 2, cancelarCompra.y + cancelarCompra.height - 4);

        // Determinar el color del texto de acuerdo con las condiciones de compra
        Color colorTexto = Color.WHITE;
        if (ElementosPrincipales.inventario.dinero < totalTransaccionCompra ||
                ElementosPrincipales.jugador.getAccionesJugador().isSobrepeso()
                || excederiaPeso) {
            colorTexto = Color.RED;
        }

        // Mostrar mensajes de error si hay condiciones de compra incorrectas
        if (ElementosPrincipales.jugador.getAccionesJugador().isSobrepeso()) {
            DibujoDebug.dibujarString(g, "No puedes llevar más objetos...", comprar.x, comprar.y - 4, colorTexto);
        }

        if (!canastaCompra.isEmpty()) {
            if (excederiaPeso) {
                DibujoDebug.dibujarString(g, "La compra excedería el peso máximo", comprar.x, comprar.y - 4, colorTexto);
            } else if (comprando) {
                DibujoDebug.dibujarString(g, "$" + totalTransaccionCompra, titularPanelComprados.x + titularPanelComprados.width / 2,
                        titularPanelComprados.y + titularPanelComprados.height - 2, colorTexto);
            } else if (sinDinero) {
                DibujoDebug.dibujarString(g, "Dinero Insuficiente", comprar.x,
                        comprar.y - 4, Color.RED);
            }
        }

        // Dibujar los elementos en la canasta de compra
        dibujarElementosPanelTiendaCompra(g, objetos, lado);
        // Dibujar el objeto seleccionado de la canasta de compra si existe
        if (objetoSeleccionadoCompra != null) {
            DibujoDebug.dibujarImagen(g, objetoSeleccionadoCompra.getSprite().getImagen(),
                    new Point(objetoSeleccionadoCompra.getPosicionFlotante().x,
                            objetoSeleccionadoCompra.getPosicionFlotante().y));
        }
    }

    private void dibujarElementosPanelTiendaCompra(final Graphics g, ArrayList<Objeto> objetos, int lado) {

        for (Objeto objetoActual : objetos) {
            if (objetoActual.getCantidadCompra() > 0) {
                Rectangle posicionTienda = objetoActual.getPosicionCompra();
                DibujoDebug.dibujarImagen(g, objetoActual.getSprite().getImagen(), posicionTienda.x, posicionTienda.y);
                String texto = objetoActual.getCantidadCompra() < 10 ? "0" + objetoActual.getCantidadCompra() : String.valueOf(objetoActual.getCantidadCompra());
                g.setColor(Color.BLACK);
                DibujoDebug.dibujarRectanguloRelleno(g, posicionTienda.x + lado - 12, posicionTienda.y + 32 - 8, 12, 8);
                g.setColor(Color.WHITE);
                int xTexto = posicionTienda.x + lado - MedidorString.medirAnchoPixeles(g, texto);
                int yTexto = posicionTienda.y + 31;
                Color colorTexto = ElementosPrincipales.inventario.dinero < totalTransaccionCompra ? Color.RED : Color.WHITE;
                DibujoDebug.dibujarString(g, texto, xTexto, yTexto, colorTexto);
            }
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
                panel.y + titularPanel.height - MedidorString.medirAltoPixeles(g, nombrePanel) / 2 - 8));
    }

    protected void dibujarVentanaParaCompra(Graphics g, SuperficieDibujo sd) {

        if (dibujarVentanaCompra && objetoSeleccionadoCompra != null) {

            DibujoDebug.dibujarRectanguloRelleno(g, ventanaCantidad, Color.GRAY);
            DibujoDebug.dibujarRectanguloRelleno(g, subirUnidad, Color.BLUE);
            DibujoDebug.dibujarRectanguloRelleno(g, bajarUnidad);
            DibujoDebug.dibujarRectanguloRelleno(g, subirDecena);
            DibujoDebug.dibujarRectanguloRelleno(g, bajarDecena);
            DibujoDebug.dibujarRectanguloRelleno(g, aceptarOperacion, Color.GRAY);
            DibujoDebug.dibujarString(g, "+1", subirUnidad.x + 4, subirUnidad.y + subirUnidad.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "-1", bajarUnidad.x + 4, bajarUnidad.y + bajarUnidad.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "+10", subirDecena.x + 3, subirUnidad.y + subirDecena.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "-10", bajarDecena.x + 3, bajarUnidad.y + bajarDecena.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "ACEPTAR", aceptarOperacion.x + 3, aceptarOperacion.y + aceptarOperacion.height - 6, Color.BLACK);
            DibujoDebug.dibujarString(g, "" + cantidadObjetos, aceptarOperacion.x, aceptarOperacion.y + aceptarOperacion.height + 10);

        }
    }

    protected void dibujarVentanaParaVenta(Graphics g, SuperficieDibujo sd) {
        if (dibujarVentanaCompra && objetoSeleccionadoVenta != null) {

            DibujoDebug.dibujarRectanguloRelleno(g, ventanaCantidad, Color.GRAY);
            DibujoDebug.dibujarRectanguloRelleno(g, subirUnidad, Color.BLUE);
            DibujoDebug.dibujarRectanguloRelleno(g, bajarUnidad);
            DibujoDebug.dibujarRectanguloRelleno(g, subirDecena);
            DibujoDebug.dibujarRectanguloRelleno(g, bajarDecena);
            DibujoDebug.dibujarRectanguloRelleno(g, aceptarOperacion, Color.GRAY);
            DibujoDebug.dibujarString(g, "+1", subirUnidad.x + 4, subirUnidad.y + subirUnidad.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "-1", bajarUnidad.x + 4, bajarUnidad.y + bajarUnidad.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "+10", subirDecena.x + 3, subirUnidad.y + subirDecena.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "-10", bajarDecena.x + 3, bajarUnidad.y + bajarDecena.height - 6, Color.WHITE);
            DibujoDebug.dibujarString(g, "ACEPTAR", aceptarOperacion.x + 3, aceptarOperacion.y + aceptarOperacion.height - 6, Color.BLACK);
            DibujoDebug.dibujarString(g, "" + cantidadObjetos, aceptarOperacion.x, aceptarOperacion.y + aceptarOperacion.height + 10);

        }
    }

    // Getters
    public Rectangle getEtiquetaMenu() {
        return etiquetaMenu;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }
}
