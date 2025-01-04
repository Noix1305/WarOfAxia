/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.sprites;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author GAMER ARRAX
 */
public class Sprite implements Serializable {
    @Serial
    private static final long serialVersionUID = 123456789L;
    public BufferedImage imagen;
    public int ancho;
    public int alto;

    public Sprite(final BufferedImage imagen, int ancho, int alto) {

        this.imagen = imagen;
        this.ancho = imagen.getWidth();
        this.alto = imagen.getHeight();
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }
}
