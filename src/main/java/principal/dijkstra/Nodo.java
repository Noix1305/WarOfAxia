/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package principal.dijkstra;

import java.awt.Point;
import java.awt.Rectangle;
import principal.Constantes;

/**
 * Clase que representa un nodo en el algoritmo de Dijkstra. Encapsula la posición y la distancia de un nodo en el
 * algoritmo de Dijkstra. Proporciona métodos para acceder y modificar estos atributos, así como para calcular el área
 * del nodo en el mapa en píxeles y en términos de los índices del mapa.
 */
public class Nodo {

    private Point posicion; // Posición del nodo en el mapa
    private double distancia;
    private int ancho;
    private int alto;// Distancia desde el nodo de inicio hasta este nodo

    // Constructor de la clase Nodo
    public Nodo(final Point posicion, final double distancia, int ancho, int alto) {
        this.posicion = posicion;
        this.distancia = distancia;
        this.ancho = ancho;
        this.alto = alto;
    }

    // Método para obtener el área del nodo en píxeles
    public Rectangle getAreaPixeles() {
        return new Rectangle(posicion.x * Constantes.LADO_SPRITE, posicion.y * Constantes.LADO_SPRITE,
                ancho, alto);
    }

    // Método para obtener el área del nodo
    public Rectangle getArea() {
        return new Rectangle(posicion.x, posicion.y,
                ancho, alto);
    }

    // Métodos para obtener y establecer la posición del nodo
    public Point getPosicion() {
        return posicion;
    }

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    // Métodos para obtener y establecer la distancia del nodo
    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

}
