/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.sprites;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author GAMER ARRAX
 */
public record Sprite(BufferedImage imagen, int ancho, int alto) implements Serializable {
    public Sprite(final BufferedImage imagen, int ancho, int alto) {
        this.imagen = imagen;
        this.ancho = imagen.getWidth();
        this.alto = imagen.getHeight();
    }
}
