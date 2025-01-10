package principal.mapas;

import principal.Constantes;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

import java.awt.*;

public class PuntoGuardado {
    private int x;
    private int y;
    private Rectangle area;
    private HojaSprites hs;
    private int estadoSprite = 0; // Estado actual del sprite (0, 1, 2, 3)
    private long ultimoCambio = System.currentTimeMillis(); // Tiempo del último cambio
    private long inicioLapso = System.currentTimeMillis(); // Inicio del lapso de 10 segundos
    private boolean procesoActivo = false;
    public PuntoGuardado(int x, int y) {
        this.x = x;
        this.y = y;
        this.area = new Rectangle(x, y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
        this.hs = new HojaSprites(Constantes.RUTA_PUNTO_GUARDADO, Constantes.LADO_SPRITE, true);
    }

    public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
        // Verifica si han pasado 10 segundos desde el inicio del lapso
        if (System.currentTimeMillis() - inicioLapso >= 5000) {
            inicioLapso = System.currentTimeMillis(); // Reinicia el lapso de 10 segundos
            procesoActivo = true; // Activa el proceso de dibujo
            estadoSprite = 0; // Reinicia el estado del sprite
        }

        // Si el proceso está activo, actualiza y dibuja los sprites
        if (procesoActivo) {
            // Cambia el sprite cada 200 ms
            if (System.currentTimeMillis() - ultimoCambio >= 80) {
                estadoSprite = (estadoSprite + 1) % 4; // Ciclo entre 0, 1, 2 y 3
                ultimoCambio = System.currentTimeMillis(); // Actualiza el tiempo del último cambio
            }

            // Dibuja el sprite basado en el estado actual
            DibujoDebug.dibujarImagen(g, hs.getSprites(estadoSprite).imagen, puntoX, puntoY);

            // Si hemos completado un ciclo completo (vuelve a 0 después de 3)
            if (estadoSprite == 0) {
                procesoActivo = false; // Desactiva el proceso hasta el próximo lapso
            }
        }else{
            DibujoDebug.dibujarImagen(g, hs.getSprites(0).imagen, puntoX, puntoY);
        }
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public HojaSprites getHs() {
        return this.hs;
    }
}
