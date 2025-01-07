/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

import principal.entes.GestorAtributos;
import principal.entes.jugador.Jugador; // Importa la clase Jugador del paquete principal.entes
import principal.inventario.Inventario; // Importa la clase Inventario del paquete principal.inventario
import principal.mapas.MapaTiled;
import principal.maquinaestado.juego.GestorGuardado;
import principal.sonido.ReproductorSonido;


/**
 * Clase que contiene las instancias principales de los elementos del juego.
 */
public class ElementosPrincipales {

    // Instancias principales del juego
    // La instancia de MapaTiled está comentada, se utiliza MapaTiled2 en su lugar
    public static MapaTiled mapa = new MapaTiled(Constantes.RUTA_MAPA_TILED); // Instancia del mapa del juego
    public static Jugador jugador = new Jugador(new GestorAtributos(1, 6, 6, 6, 6, 6, 80, 100, 0)); // Instancia del jugador del juego
    public static Inventario inventario = new Inventario(); // Instancia del inventario del juego
    public static ReproductorSonido reproductor = new ReproductorSonido();
    public static GestorGuardado gestorGuardado = new GestorGuardado();

}
