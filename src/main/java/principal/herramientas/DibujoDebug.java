/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.herramientas;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author GAMER ARRAX
 */
public class DibujoDebug {

    private static int objetosDibujados = 0;

    public static void dibujarImagen(final Graphics g, final BufferedImage img, final int x, final int y) {
        objetosDibujados++;
        g.drawImage(img, x, y, null);
    }

    public static void dibujarImagen(final Graphics g, final BufferedImage img, Point p) {
        objetosDibujados++;
        g.drawImage(img, p.x, p.y, null);
    }

    public static void dibujarString(final Graphics g, final String s, final Point p) {
        objetosDibujados++;
        g.drawString(s, p.x, p.y);
    }

    public static void dibujarString(final Graphics g, final String s, final int x, final int y, Color c) {
        objetosDibujados++;
        g.setColor(c);
        g.drawString(s, x, y);
    }

    public static void dibujarString(final Graphics g, final String s, final int x, final int y) {
        objetosDibujados++;
        g.drawString(s, x, y);
    }

    public static void dibujarString(final Graphics g, final String s, final Point p, Color c) {
        objetosDibujados++;
        g.setColor(c);
        g.drawString(s, p.x, p.y);
    }

    public static void dibujarRectanguloRelleno(final Graphics g, final Rectangle r) {
        objetosDibujados++;
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    public static void dibujarRectanguloRelleno(final Graphics g, final int x, final int y, final int ancho,
                                                final int alto, final Color c) {
        objetosDibujados++;
        g.setColor(c);
        g.fillRect(x, y, ancho, alto);
    }

    public static void dibujarRectanguloRelleno(final Graphics g, final int x, final int y, final int ancho,
                                                final int alto) {
        objetosDibujados++;
        g.fillRect(x, y, ancho, alto);
    }

    public static void dibujarRectanguloRelleno(final Graphics g, final Rectangle r, final Color c) {
        objetosDibujados++;
        g.setColor(c);
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    public static void dibujarRectanguloContorno(final Graphics g, final Rectangle r, final Color c) {
        objetosDibujados++;
        g.setColor(c);
        g.drawRect(r.x, r.y, r.width, r.height);
    }

    public static void dibujarRectanguloContorno(final Graphics g, final int x, final int y, final int ancho,
                                                 final int alto, final Color c) {
        objetosDibujados++;
        g.setColor(c);
        g.drawRect(x, y, ancho, alto);
    }

    public static void dibujarRectanguloContorno(final Graphics g, final Rectangle r) {
        objetosDibujados++;
        if (r != null) {
            g.drawRect(r.x, r.y, r.width, r.height+1);
            // Realizar alguna operación para cambiar los valores de r.x, r.y, r.width o r.height
        } else {
            return;
        }
    }

    public static void dibujarRectanguloContorno(final Graphics g, final int x, final int y, final int ancho,
                                                 final int alto) {
        objetosDibujados++;
        g.drawRect(x, y, ancho, alto);
    }

    public static void escribirTextoLetraPorLetra(Graphics g, String textoCompleto, int xInicial, int yInicial, int anchoMaximo, int velocidadEscritura, long tiempoInicio) {
        FontMetrics metrics = g.getFontMetrics();
        long tiempoActual = System.currentTimeMillis();
        int letrasMostradas = (int) ((tiempoActual - tiempoInicio) / velocidadEscritura); // Letras a mostrar en total

        StringBuilder lineaActual = new StringBuilder();
        int letrasProcesadas = 0;

        String[] palabras = textoCompleto.split(" ");
        for (String palabra : palabras) {
            if (metrics.stringWidth(lineaActual.toString() + palabra) > anchoMaximo) {
                // Dibujar línea acumulada
                String textoADibujar = lineaActual.toString();
                int letrasParaEstaLinea = Math.min(textoADibujar.length(), Math.max(0, letrasMostradas - letrasProcesadas));
                if (letrasParaEstaLinea > 0) {
                    DibujoDebug.dibujarString(g, textoADibujar.substring(0, letrasParaEstaLinea), xInicial, yInicial, Color.BLACK);
                }

                letrasProcesadas += textoADibujar.length();
                yInicial += metrics.getHeight();
                lineaActual = new StringBuilder(palabra + " ");
            } else {
                lineaActual.append(palabra).append(" ");
            }
        }

        // Dibujar la última línea acumulada
        String textoADibujar = lineaActual.toString();
        int letrasParaEstaLinea = Math.min(textoADibujar.length(), Math.max(0, letrasMostradas - letrasProcesadas));
        if (letrasParaEstaLinea > 0) {
            DibujoDebug.dibujarString(g, textoADibujar.substring(0, letrasParaEstaLinea), xInicial, yInicial, Color.BLACK);
        }
    }


    public static void reiniciarContadorObjetos() {
        objetosDibujados = 0;
    }

    public static int getObjetosDibujados() {
        return objetosDibujados;
    }

}
