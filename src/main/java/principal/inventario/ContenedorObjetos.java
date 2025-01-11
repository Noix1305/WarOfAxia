/*
 * Esta clase representa un contenedor de objetos en el juego.
 * Puede contener varios objetos y se dibuja en una posición determinada en la pantalla.
 */
package principal.inventario;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.herramientas.DibujoDebug;
import principal.sonido.ReproductorSonido;
import principal.sprites.HojaSprites;

public class ContenedorObjetos {

    private Point posicion; // Posición del contenedor en la pantalla
    private ArrayList<Objeto> objetos; // Lista de objetos contenidos en el contenedor
    private HojaSprites hs; // Hoja de sprites que contiene las imágenes de los contenedores
    private Rectangle area;
    private boolean abierto;// Área del contenedor en la pantalla

    // Constructor por defecto
    public ContenedorObjetos() {
        this.objetos = new ArrayList<>();
    }

    // Constructor con parámetros
    public ContenedorObjetos(Point posicion) {
        // Carga de la hoja de sprites y configuración de parámetros iniciales
        hs = new HojaSprites(Constantes.RUTA_HOJA_CONTENEDORES, 32, false);
        this.posicion = posicion;
        this.objetos = new ArrayList<>();
        this.area = new Rectangle(posicion.x, posicion.y, 32, 32);
        this.abierto = false;
    }

    // Método para dibujar el contenedor en la pantalla
    public void dibujar(final Graphics g, final int puntoX, final int puntoY) {
        if (!abierto) {
            DibujoDebug.dibujarImagen(g, hs.getSprites(0).imagen, puntoX, puntoY);
        } else {
            DibujoDebug.dibujarImagen(g, hs.getSprites(1).imagen, puntoX, puntoY);
        }
    }

    // Getters y setters para los atributos de la clase
    public Point getPosicion() {
        return posicion;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    public void setObjetos(Objeto objeto) {
        this.objetos.add(objeto);
    }


    public HojaSprites getHs() {
        return hs;
    }

    public void setHs(HojaSprites hs) {
        this.hs = hs;
    }


    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    public void setObjetos(ArrayList<Objeto> objetos) {
        this.objetos = objetos;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        ReproductorSonido.chestSound.reproducir(0.8f);
        this.abierto = abierto;
    }
}
