/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.habilidades;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.entes.Entidad;
import principal.entes.EntidadCurable;
import principal.entes.enemigo.Enemigo;
import principal.entes.jugador.Jugador;
import principal.inventario.TipoObjeto;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author GAMER ARRAX
 */
public class Danho extends Habilidad implements Serializable {

    private final double montoAdicionalPorInteligencia;
    private int danhoBase;
    private int alcanceLateral;
    private int alcanceFrontal;

    public Danho(int id, String nombre, int duracion, int tiempoReutilizacion, int manaUtilizado, int vidaUtilizada,
                 double montoAdicionalPorInt, int danhoBase, int indiceSprite, TipoObjeto activaPasiva, TipoObjeto tipoHabilidad, double alcance) {
        super(id, nombre, duracion, manaUtilizado, vidaUtilizada, indiceSprite, activaPasiva, tipoHabilidad, alcance, tiempoReutilizacion);

        montoAdicionalPorInteligencia = montoAdicionalPorInt;
        this.danhoBase = danhoBase;

    }


    /*public void aplicarEfecto(Object object, TipoObjeto tipoHabilidad) {
        if(ElementosPrincipales.jugador.getArea()))
        danhar(object, tipoHabilidad);
    }*/

    private void danhar(Entidad atacante, Entidad objetivo, TipoObjeto tipoHabilidad) {

        if (cronometro.obtenerTiempoTranscurrido() / 1000 >= getTiempoReutilizacion()) {
            if (objetivo instanceof Enemigo enemigo && atacante instanceof Jugador jugador) {
                System.out.println("Vida enemigo en Dañar: " + enemigo.gestorAtributos.getVidaEnemigo());

                if (enemigo.gestorAtributos.getVidaEnemigo() > 0 &&
                        atacante.gestorAtributos.getMana() >= getManaUtilizado()) {

                    //ElementosPrincipales.jugador.dibujarHabilidad = true;
                    // Calcular la cantidad total de curación (base + adicional por inteligencia)
                    int cantidadTotalDanho = danhoBase + calcularMontoAdicionalPorInteligencia(jugador);
                    super.setMontoTotal(cantidadTotalDanho);
                    System.out.println("Daño realizado: " + super.getMontoTotal());

                    enemigo.recibirDanho(cantidadTotalDanho, tipoHabilidad);

                    ElementosPrincipales.jugador.gestorAtributos.setMana(jugador.gestorAtributos.getMana() - getManaUtilizado());
                    setTiempoReutilizacion(super.getTiempoReutilizacion());

                    cronometro.reiniciar();
                }

            }

        }
    }

    private int calcularMontoAdicionalPorInteligencia(Entidad entidad) {
        if (entidad instanceof Jugador jugador) {
            return (int) (entidad.gestorAtributos.getInteligencia() * montoAdicionalPorInteligencia);
        }
        return 0;
    }


    @Override
    public void aplicarEfecto(Entidad atacante, Entidad objetivo, TipoObjeto tipoHabilidad) {
        danhar(atacante, objetivo, tipoHabilidad);
    }

    public double getMontoAdicionalPorInteligencia() {
        return montoAdicionalPorInteligencia;
    }

    public int getDanhoBase() {
        return danhoBase;
    }

    public void setDanhoBase(int danhoBase) {
        this.danhoBase = danhoBase;
    }
}
