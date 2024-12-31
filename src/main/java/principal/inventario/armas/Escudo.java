/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.inventario.armas;

import principal.inventario.TipoObjeto;

/**
 *
 * @author GAMER ARRAX
 */
public class Escudo extends Arma {
    private int defensa;
    public Escudo(int id, String nombre, String descripcion, int peso, int ataqueMin, int ataqueMax, int alcanceFrontal,
            int alcanceLateral, TipoObjeto tipoObjeto, boolean automatica, boolean penetrante, double ataquesXSegundo,
            String rutaDisparo, String rutaPersonaje, int precioCompra, int precioVenta, int defensa) {
        super(id, nombre, descripcion, peso, ataqueMin, ataqueMax, alcanceFrontal, alcanceLateral, tipoObjeto,
                automatica, penetrante, ataquesXSegundo, rutaDisparo, rutaPersonaje, precioCompra, precioVenta);
        this.defensa = defensa;
    }


    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
    
    

}
