package principal.maquinaestado.juego.menuInicial;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.maquinaestado.EstadoJuego;
import principal.maquinaestado.juego.EstadoJuegoGuardar;
import principal.maquinaestado.juego.GestorGuardado;
import principal.maquinaestado.juego.GestorJuego;
import principal.maquinaestado.juego.JuegoGuardado;
import principal.sprites.HojaSprites;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class MenuInicio implements EstadoJuego {

    private final Rectangle BANNER_LATERAL;
    private final Rectangle etiquetaCargarJuego;
    private final Rectangle etiquetaNuevoJuego;
    private final Rectangle etiquetaOpciones;
    private final Rectangle ventanaCargarJuego;
    private boolean musicaIniciada;
    private HojaSprites fondo;
    private boolean mostrarVentanaCargarJuego;
    private File[] archivosGuardados;
    private ArrayList<SlotCargarJuego> slots;
    private SuperficieDibujo sd;
    private int botonSeleccionado = 0;
    private long tiempoUltimaPulsacion = 0;// Índice del botón seleccionado (0 = Nuevo Juego, 1 = Cargar Partida, 2 = Opciones)


    public MenuInicio() {


        etiquetaNuevoJuego = new Rectangle(Constantes.ANCHO_JUEGO / 2 - 30, Constantes.ALTO_JUEGO / 2 - 20, 70, 15);
        etiquetaCargarJuego = new Rectangle(etiquetaNuevoJuego.x, etiquetaNuevoJuego.y + 20, etiquetaNuevoJuego.width, etiquetaNuevoJuego.height);
        etiquetaOpciones = new Rectangle(etiquetaCargarJuego.x, etiquetaCargarJuego.y + 20, etiquetaNuevoJuego.width, etiquetaNuevoJuego.height);

        BANNER_LATERAL = new Rectangle(0, 0, 140,
                Constantes.ALTO_JUEGO);
        ventanaCargarJuego = new Rectangle(etiquetaNuevoJuego.x + etiquetaNuevoJuego.width + 20, 50, 100, 160);
        musicaIniciada = false;
        fondo = new HojaSprites("/fondos/fondoInicio.png", 360, 360, true);
        mostrarVentanaCargarJuego = false;
        sd = GestorPrincipal.sd;
        cargarListaArchivosGuardados();
        crearSlotsCargarJuego();
    }

    @Override
    public void actualizar() {
        iniciarMusica();
        iniciarNuevaPartida(GestorPrincipal.sd);
        Rectangle posicionRaton = GestorPrincipal.sd.getRaton().getPosicionRectangle();
        long tiempoActual = System.currentTimeMillis();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(etiquetaCargarJuego)) &&
                GestorPrincipal.sd.getRaton().isClick()) {
            mostrarVentanaCargarJuego = true;
        }

        if (mostrarVentanaCargarJuego) {
            gestionarSeleccionArchivo(GestorPrincipal.sd);
        }

        if (GestorControles.teclado.teclaArriba.puedeProcesarse()) {
            botonSeleccionado = (botonSeleccionado - 1 + 3) % 3; // Navegar hacia arriba
            GestorControles.teclado.teclaArriba.marcarComoProcesada();
            System.out.println("boton: " + botonSeleccionado);// Marca la tecla como procesada
        }

        if (GestorControles.teclado.teclaAbajo.puedeProcesarse()) {
            botonSeleccionado = (botonSeleccionado + 1) % 3; // Navegar hacia abajo
            GestorControles.teclado.teclaAbajo.marcarComoProcesada();
            System.out.println("boton: " + botonSeleccionado);// Marca la tecla como procesada
        }

        if (GestorPrincipal.menuInicio && tiempoActual - tiempoUltimaPulsacion > 300) {
            if (GestorControles.teclado.enter.puedeProcesarse()) {
                GestorControles.teclado.enter.marcarComoProcesada();
                GestorControles.teclado.enter.teclaLiberada();
                System.out.println("Enter pulsada en menú inicio");

                switch (botonSeleccionado) {
                    case 0: // Nuevo Juego
                        if (!mostrarVentanaCargarJuego) {
                            musicaIniciada = false;
                            GestorPrincipal.juegoActivo = true;
                            GestorPrincipal.menuInicio = false;
                            GestorJuego.cargarMapa(ElementosPrincipales.mapa.getNombreMapaActual());
                        }
                        break;
                    case 1: // Cargar Partida
                        mostrarVentanaCargarJuego = true;
                        break;
                    case 2: // Opciones
                        System.out.println("Opciones seleccionadas");
                        break;
                }
                tiempoUltimaPulsacion = tiempoActual; // Actualiza el tiempo
            }
        }

    }


    @Override
    public void dibujar(Graphics2D g) {
        dibujarFondo(g);
        dibujarPantalla(g);
        dibujarContornoEtiquetas(g, GestorPrincipal.sd);
        if (mostrarVentanaCargarJuego) {
            dibujarVentanaCargarJuego(g);
        }


    }


    private void iniciarMusica() {
        if (!GestorPrincipal.pantallaTitulo && !musicaIniciada) {
            String rutaMusica = "FF-I-OST-The-prelude";
            if (!ElementosPrincipales.reproductor.musica.getFilename().toUpperCase().equalsIgnoreCase(rutaMusica)) {
                ElementosPrincipales.reproductor.musica.cambiarArchivo(rutaMusica);
                ElementosPrincipales.reproductor.musica.repetir(0.8f);
                musicaIniciada = true;
            }
        }
    }

    private void dibujarPantalla(Graphics g) {

        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaNuevoJuego, Color.white);
        DibujoDebug.dibujarString(g, "Nueva Partida", etiquetaNuevoJuego.x + 5, etiquetaNuevoJuego.y + 10, Color.black);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaCargarJuego, Color.white);
        DibujoDebug.dibujarString(g, "Cargar Partida", etiquetaCargarJuego.x + 5, etiquetaCargarJuego.y + 10, Color.black);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaOpciones, Color.white);
        DibujoDebug.dibujarString(g, "Opciones", etiquetaOpciones.x + 5, etiquetaOpciones.y + 10, Color.black);
        DibujoDebug.dibujarRectanguloRelleno(g, BANNER_LATERAL, Constantes.COLOR_NARANJA);
        DibujoDebug.dibujarRectanguloRelleno(g, Constantes.ANCHO_JUEGO - BANNER_LATERAL.width, 0,
                BANNER_LATERAL.width, BANNER_LATERAL.height, Constantes.COLOR_NARANJA);
    }

    private void iniciarNuevaPartida(SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();

        if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(etiquetaNuevoJuego)) && sd.getRaton().isClick()) {
            musicaIniciada = false;
            GestorPrincipal.juegoActivo = true;
            GestorPrincipal.menuInicio = false;
            GestorJuego.cargarMapa(ElementosPrincipales.mapa.getNombreMapaActual());
        }
    }

    private void cargarJuego(File archivo) {
        GestorGuardado gestorGuardado = new GestorGuardado();
        EstadoJuegoGuardar estadoCargado = JuegoGuardado.cargarEstadoJuego(archivo.getPath());

        if (estadoCargado != null) {
            System.out.println("Juego cargado desde: " + archivo.getName());
            musicaIniciada = false;
            mostrarVentanaCargarJuego = false;
            GestorPrincipal.menuInicio = false;
            GestorPrincipal.juegoActivo = true;
            gestorGuardado.cargarJuego(archivo);
        } else {
            System.out.println("Error al cargar el archivo: " + archivo.getName());
        }
    }

    private void dibujarFondo(Graphics2D g) {
        BufferedImage fondo = this.fondo.getSprites(0).imagen;
        DibujoDebug.dibujarImagen(g, fondo, BANNER_LATERAL.width, 0);
    }

    private void dibujarContornoEtiquetas(Graphics g, SuperficieDibujo sd) {
        // Dibujar contorno según el índice seleccionado
        switch (botonSeleccionado) {
            case 0: // Nuevo Juego
                DibujoDebug.dibujarRectanguloContorno(g, etiquetaNuevoJuego, Color.cyan);
                break;
            case 1: // Cargar Partida
                DibujoDebug.dibujarRectanguloContorno(g, etiquetaCargarJuego, Color.cyan);
                break;
            case 2: // Opciones
                DibujoDebug.dibujarRectanguloContorno(g, etiquetaOpciones, Color.cyan);
                break;
        }
    }


    private void dibujarSlots(Graphics g, SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();

        for (SlotCargarJuego slot : slots) {
            DibujoDebug.dibujarRectanguloRelleno(g, slot.getSlot(), Color.WHITE);
            if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(slot.getSlot()))) {
                DibujoDebug.dibujarRectanguloContorno(g, slot.getSlot(), Color.cyan);
            }
        }
    }

    private void dibujarVentanaCargarJuego(Graphics2D g) {
        if (sd.getRaton().isClick2()) {
            mostrarVentanaCargarJuego = false;
        }
        // Dibujar fondo de la ventana
        DibujoDebug.dibujarRectanguloContorno(g, ventanaCargarJuego, Color.BLACK);
        dibujarSlots(g, GestorPrincipal.sd);

        // Dibujar lista de archivos
        if (archivosGuardados != null) {
            for (SlotCargarJuego slot : slots) {
                String nombreArchivo = getNombreArchivo(slot);
                DibujoDebug.dibujarString(g, nombreArchivo, slot.getSlot().x + 2, slot.getSlot().y + 13, Color.BLACK);
            }
        } else {
            DibujoDebug.dibujarString(g, "No hay archivos guardados", ventanaCargarJuego.x + 10, ventanaCargarJuego.y + 20, Color.RED);
        }

    }

    private static String getNombreArchivo(SlotCargarJuego slot) {
        String nombreArchivo = slot.getArchivo().getName();
        String[] partes = nombreArchivo.split("\\."); // Divide en ["guardado", "07-01-25_17-20-34_1736281234551", "save"]
        if (partes.length > 1) {
            String fechaHora = partes[1]; // Obtener "07-01-25_17-20-34_1736281234551"
            String[] fechaHoraPartes = fechaHora.split("_"); // Divide en ["07-01-25", "17-20-34", "1736281234551"]
            if (fechaHoraPartes.length > 1) {
                String fecha = fechaHoraPartes[0]; // "07-01-25"
                String hora = fechaHoraPartes[1]; // "17-20-34"

                // Reemplazar segundos de hora para ajustarlo al formato deseado
                String horaSinSegundos = hora.substring(0, 5); // "17:20"
                horaSinSegundos = horaSinSegundos.replace("-", ":");

                // Combinar fecha y hora en formato final
                nombreArchivo = fecha + " / " + horaSinSegundos; // "07-01-25:17:20"
            }
        }
        return nombreArchivo;
    }


    private void cargarListaArchivosGuardados() {
        String carpetaGuardados = "juegosGuardados";
        File directorio = new File(carpetaGuardados);
        if (directorio.exists() && directorio.isDirectory()) {
            archivosGuardados = directorio.listFiles((dir, name) -> name.endsWith(".save"));
        } else {
            archivosGuardados = new File[0];
        }
    }

    private void gestionarSeleccionArchivo(SuperficieDibujo sd) {
        Rectangle posicionRaton = sd.getRaton().getPosicionRectangle();

        if (archivosGuardados != null) {
            for (SlotCargarJuego slot : slots) {
                if (posicionRaton.intersects(EscaladorElementos.escalarRectangleArriba(slot.getSlot())) && sd.getRaton().isClick()) {
                    cargarJuego(slot.getArchivo());
                    mostrarVentanaCargarJuego = false;
                    break;
                }
            }
        }
    }

    private void crearSlotsCargarJuego() {
        slots = new ArrayList<>();
        int y = ventanaCargarJuego.y;
        int x = ventanaCargarJuego.x + 5;
        int ancho = ventanaCargarJuego.width - 10;
        int alto = 20;

        for (File archivo : archivosGuardados) {
            Rectangle slot = new Rectangle(x, y + 10, ancho, alto);
            SlotCargarJuego slotNuevo = new SlotCargarJuego(archivo, slot);
            slots.add(slotNuevo);
            y += 30;
        }
    }


}
