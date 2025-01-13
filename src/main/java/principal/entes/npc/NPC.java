package principal.entes.npc;

import principal.Constantes;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

import java.awt.*;
import java.util.Random;

public class NPC {
    private final int[] valoresPermitidos = {0, 3, 6};
    private int id;
    private Point posicion;
    private int indiceSprite;
    private final int indiceSpriteOriginal;// Índice inicial
    private long tiempoUltimaActualizacion = 0; // Tiempo de la última actualización
    private static final int INTERVALO_CAMBIO = 2000;// Intervalo en milisegundos
    private final Random random = new Random();
    private boolean activo = false;
    private final String dialogo;
    private long tiempoInicio;
    private final HojaSprites cuadroDialogo;

    private HojaSprites hojaSprites;

    public NPC(int id, Point posicion, String ruta, int indiceSprite, String dialogo) {
        this.id = id;
        this.posicion = posicion;
        this.hojaSprites = new HojaSprites(ruta, Constantes.LADO_SPRITE, true);
        this.indiceSpriteOriginal = indiceSprite;
        this.indiceSprite = indiceSpriteOriginal;
        this.dialogo = dialogo;
        cuadroDialogo = new HojaSprites(Constantes.RUTA_CUADRO_DIALOGO, 64, 38, true);
        iniciarDialogo();

    }

    public void iniciarDialogo() {
        tiempoInicio = System.currentTimeMillis(); // Iniciar el temporizador cuando comience el diálogo
    }

    public void dibujar(Graphics g) {
        if (!activo) {
            long tiempoActual = System.currentTimeMillis(); // Tiempo actual en milisegundos

            // Cambia el sprite si ha pasado el intervalo definido
            if (tiempoActual - tiempoUltimaActualizacion >= INTERVALO_CAMBIO) {
                int indiceAleatorio = random.nextInt(valoresPermitidos.length); // Genera un índice aleatorio entre 0 y la longitud de valoresPermitidos
                indiceSprite = valoresPermitidos[indiceAleatorio]; // Selecciona un valor permitido
                tiempoUltimaActualizacion = tiempoActual; // Actualiza el tiempo de referencia
            }
        } else {
            indiceSprite = indiceSpriteOriginal;
        }

        // Dibuja el sprite actual
        DibujoDebug.dibujarImagen(g, hojaSprites.getSprites(indiceSprite).imagen, posicion);
        dibujarDialogos(g);
    }

    private void dibujarDialogos(Graphics g) {
        g.setColor(Color.white);
        DibujoDebug.dibujarImagen(g,cuadroDialogo.getSprites(0).imagen,posicion.x,posicion.y-28);
        DibujoDebug.escribirTextoLetraPorLetra(g, dialogo, posicion.x+4, posicion.y-18, 64, 50, tiempoInicio);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPosicion() {
        return posicion;
    }

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    public HojaSprites getHojaSprites() {
        return hojaSprites;
    }

    public void setHojaSprites(HojaSprites hojaSprites) {
        this.hojaSprites = hojaSprites;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
