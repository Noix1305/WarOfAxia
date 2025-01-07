package principal.maquinaestado.juego.menuInicial;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.EscaladorElementos;
import principal.maquinaestado.EstadoJuego;
import principal.maquinaestado.juego.GestorJuego;
import principal.sprites.HojaSprites;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuInicio implements EstadoJuego {

    private final Rectangle BANNER_LATERAL;
    private final Rectangle etiquetaCargarJuego;
    private final Rectangle etiquetaNuevoJuego;
    private final Rectangle etiquetaOpciones;
    private boolean musicaIniciada;
    private int anchoPantallaInicio;
    private HojaSprites fondo;

    public MenuInicio() {

        etiquetaNuevoJuego = new Rectangle(Constantes.ANCHO_JUEGO / 2 - 30, Constantes.ALTO_JUEGO / 2 - 20, 70, 15);
        etiquetaCargarJuego = new Rectangle(etiquetaNuevoJuego.x, etiquetaNuevoJuego.y + 20, etiquetaNuevoJuego.width, etiquetaNuevoJuego.height);
        etiquetaOpciones = new Rectangle(etiquetaCargarJuego.x, etiquetaCargarJuego.y + 20, etiquetaNuevoJuego.width, etiquetaNuevoJuego.height);
        BANNER_LATERAL = new Rectangle(0, 0, 140,
                Constantes.ALTO_JUEGO);
        musicaIniciada = false;
        anchoPantallaInicio = Constantes.ANCHO_JUEGO - (BANNER_LATERAL.width * 2);
        fondo = new HojaSprites("/fondos/fondoInicio.png", 360, 360, true);

    }

    @Override
    public void actualizar() {
        GestorPrincipal.pantallaTitulo = false;
        iniciarMusica();
        iniciarNuevaPartida(GestorPrincipal.sd);
    }

    @Override
    public void dibujar(Graphics2D g) {
        dibujarFondo(g);
        dibujarPantalla(g);
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
        DibujoDebug.dibujarRectanguloContorno(g, etiquetaNuevoJuego, Color.cyan);
        DibujoDebug.dibujarString(g, "Nueva Partida", etiquetaNuevoJuego.x + 5, etiquetaNuevoJuego.y + 10, Color.black);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaCargarJuego, Color.white);
        DibujoDebug.dibujarRectanguloContorno(g, etiquetaCargarJuego, Color.cyan);
        DibujoDebug.dibujarString(g, "Cargar Partida", etiquetaCargarJuego.x + 5, etiquetaCargarJuego.y + 10, Color.black);
        DibujoDebug.dibujarRectanguloRelleno(g, etiquetaOpciones, Color.white);
        DibujoDebug.dibujarRectanguloContorno(g, etiquetaOpciones, Color.cyan);
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
            GestorJuego.cargarMapa(ElementosPrincipales.mapa.getNombreMapaActual());
        }
    }

    private void dibujarFondo(Graphics2D g) {
        BufferedImage fondo = this.fondo.getSprites(0).imagen;
        DibujoDebug.dibujarImagen(g, fondo, BANNER_LATERAL.width, 0);
    }
}
