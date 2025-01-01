/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.juego.menuInicial;

import java.awt.*;

import principal.maquinaestado.EstadoJuego;
import principal.maquinaestado.menujuego.EstructuraMenu;
import principal.maquinaestado.menujuego.MenuInventario;
import principal.maquinaestado.menujuego.SeccionMenu;

/**
 * @author GAMER ARRAX
 */
public class PantallaTitulo implements EstadoJuego {

    private static boolean esperaEnter = true;

    //    private transient static BufferedImage titulo;
//    private final HojaSprites ht;
//    private final HojaSprites s1;
//    private final HojaSprites s2;
//    private transient static BufferedImage start;
//    private final Timer timer;
//    private final SeccionMenu[] secciones;
    private final SeccionMenu[] secciones;
    private SeccionMenu seccionActual;

    private final EstructuraMenu estructuraMenu;


    public PantallaTitulo() {


        estructuraMenu = new EstructuraMenu();
        secciones = new SeccionMenu[5];

        final Rectangle etiquetaInventario = new Rectangle(estructuraMenu.BANNER_LATERAL.x
                + estructuraMenu.MARGEN_HORIZONTAL_ETIQUETAS, estructuraMenu.BANNER_LATERAL.y
                + estructuraMenu.MARGEN_VERTICAL_ETIQUETAS, estructuraMenu.ANCHO_ETIQUETAS,
                estructuraMenu.ALTO_ETIQUETAS);

        secciones[0] = new MenuInventario("INVENTARIO", etiquetaInventario, estructuraMenu);



    }

//    private void cambiarImagen() {
//        // Determinar qué imagen mostrar basándose en la imagen actual
//        if (start == s1.getSprites(0).imagen()) {
//            start = s2.getSprites(0).imagen();
//        }
//        else {
//            start = s1.getSprites(0).imagen();
//        }
//    }

    public void actualizar() {
        if (!esperaEnter) {
            // Aquí podrías agregar lógica de animación o actualización
        }
    }

    public void dibujar(Graphics2D g) {
        estructuraMenu.dibujar(g);

        if (esperaEnter) {
            // Dibujar algún indicador para indicar al usuario que presione Enter
        }
    }

    public static void setEsperaEnter(boolean espera) {
        esperaEnter = espera;
    }
}
