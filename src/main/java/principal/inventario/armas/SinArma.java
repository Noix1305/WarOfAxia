/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.inventario.armas;

import principal.inventario.TipoObjeto;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

/**
 *
 * @author GAMER ARRAX
 */
public class SinArma extends Arma {

    public SinArma(int id, String nombre, String descripcion, int peso, int ataqueMin, int ataqueMax, int alcanceFrontal,
            int alcanceLateral, final TipoObjeto tipoObjeto, boolean automatica, boolean penetrante, double ataquesXSegundo,
            final String rutaSonido, String rutaPersonaje,int precioCompra, int precioVenta) {
        super(id, nombre, descripcion, peso, ataqueMin, ataqueMax, alcanceFrontal, alcanceLateral,
                tipoObjeto, automatica, penetrante, ataquesXSegundo, rutaSonido, rutaPersonaje, precioCompra,  precioVenta);
    }


    public  HojaSprites getHojaArmas() {
        return hojaArmas;
    }

    public int getAtaqueMin() {
        return ataqueMin;
    }

    public void setAtaqueMin(int ataqueMin) {
        this.ataqueMin = ataqueMin;
    }

    public int getAtaqueMax() {
        return ataqueMax;
    }

    public void setAtaqueMax(int ataqueMax) {
        this.ataqueMax = ataqueMax;
    }

    public TipoObjeto getTipoObjeto() {
        return tipoObjeto;
    }

    public void setTipoObjeto(TipoObjeto tipoObjeto) {
        this.tipoObjeto = tipoObjeto;
    }

    public int getAlcanceInt() {
        return alcanceFrontal;
    }

    @Override
    public Sprite getSprite() {
        return null;
    }

}
