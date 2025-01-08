/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.juego.menu_tienda;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import principal.graficos.SuperficieDibujo;
import principal.inventario.Objeto;

/**
 *
 * @author GAMER ARRAX
 */
public class TiendaHabilidades extends SeccionTienda{

    public TiendaHabilidades(String nombreSeccion, Rectangle etiquetaMenu, EstructuraTienda et) {
        super(nombreSeccion, etiquetaMenu, et);
    }
    
    

    @Override
    public void actualizar() {
    }

    @Override
    public void dibujar(Graphics g, SuperficieDibujo sd) {
        super.dibujarPaneles(g);
        super.dibujarLimitePeso(g);

    }

    @Override
    protected void dibujarTooltipPaneles(Graphics g, SuperficieDibujo sd, ArrayList<Objeto> objetosTienda, ArrayList<Objeto> objetos) {

    }

    @Override
    protected void dibujarTooltipObjeto(Graphics g, SuperficieDibujo sd, Objeto objeto) {

    }

}
