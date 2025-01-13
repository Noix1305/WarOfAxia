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

    public static boolean escribirTextoLetraPorLetra(Graphics g, String textoCompleto, int xInicial, int yInicial, int anchoMaximo, int velocidadEscritura, long tiempoInicio) {
        // Obtener las métricas de la fuente utilizada para medir el texto
        FontMetrics metrics = g.getFontMetrics();

        // Calcular el tiempo actual
        long tiempoActual = System.currentTimeMillis();

        // Determinar cuántas letras deben mostrarse según el tiempo transcurrido y la velocidad de escritura
        int letrasMostradas = (int) ((tiempoActual - tiempoInicio) / velocidadEscritura);

        // Usar un StringBuilder para construir líneas de texto que se ajusten al ancho máximo permitido
        StringBuilder lineaActual = new StringBuilder();
        int letrasProcesadas = 0;

        // Dividir el texto completo en palabras para procesarlas línea por línea
        String[] palabras = textoCompleto.split(" ");

        for (String palabra : palabras) {
            // Comprobar si agregar la siguiente palabra excede el ancho máximo permitido
            if (metrics.stringWidth(lineaActual.toString() + palabra) > anchoMaximo - 5) {
                // Si se excede, dibujar la línea acumulada
                String textoADibujar = lineaActual.toString();
                int letrasParaEstaLinea = Math.min(
                        textoADibujar.length(),
                        Math.max(0, letrasMostradas - letrasProcesadas) // Calcular cuántas letras de esta línea se pueden mostrar
                );

                // Dibujar la parte visible de la línea
                if (letrasParaEstaLinea > 0) {
                    DibujoDebug.dibujarString(g, textoADibujar.substring(0, letrasParaEstaLinea), xInicial, yInicial, Color.BLACK);
                }

                // Actualizar el conteo de letras procesadas y avanzar la posición vertical
                letrasProcesadas += textoADibujar.length();
                yInicial += metrics.getHeight();

                // Comenzar una nueva línea con la palabra actual
                lineaActual = new StringBuilder(palabra + " ");
            } else {
                // Si no se excede, agregar la palabra a la línea actual
                lineaActual.append(palabra).append(" ");
            }
        }

        // Dibujar la última línea acumulada (si no se excedió el ancho)
        String textoADibujar = lineaActual.toString();
        int letrasParaEstaLinea = Math.min(
                textoADibujar.length(),
                Math.max(0, letrasMostradas - letrasProcesadas)
        );

        // Dibujar la parte visible de la última línea
        if (letrasParaEstaLinea > 0) {
            DibujoDebug.dibujarString(g, textoADibujar.substring(0, letrasParaEstaLinea), xInicial, yInicial, Color.BLACK);
        }

        // Verificar si todas las letras del texto ya se han mostrado
        return letrasMostradas >= textoCompleto.length();
    }





    public static void reiniciarContadorObjetos() {
        objetosDibujados = 0;
    }

    public static int getObjetosDibujados() {
        return objetosDibujados;
    }

}
