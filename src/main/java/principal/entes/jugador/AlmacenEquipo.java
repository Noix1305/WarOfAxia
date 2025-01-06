/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package principal.entes.jugador;

import java.io.Serializable;
import java.util.ArrayList;

import principal.ElementosPrincipales;
import principal.inventario.Objeto;
import principal.inventario.armaduras.*;
import principal.inventario.joyas.Accesorio;
import principal.inventario.joyas.Anillo;
import principal.inventario.joyas.Collar;
import principal.inventario.joyas.Joya;
import principal.inventario.armas.Arma;

public class AlmacenEquipo implements Serializable {

    // Variables para almacenar los objetos equipados
    private Arma arma1;
    private Arma arma2;
    private Armadura armadura;
    private Armadura casco;
    private Armadura guante;
    private Armadura bota;
    private Joya collar;
    private Joya accesorio;
    private Joya anillo1;
    private Joya anillo2;

    // Lista para almacenar el equipo actual
    public ArrayList<Objeto> equipoActual;

    // Constructor que inicializa el equipo con objetos pasados como parámetros
    public AlmacenEquipo(Arma arma1, Arma arma2, Armadura armadura, Armadura casco, Armadura guante, Armadura bota,
                         Joya collar, Joya accesorio, Joya anillo1, Joya anillo2) {
        this.arma1 = arma1;
        this.arma2 = arma2;
        this.armadura = armadura;
        this.casco = casco;
        this.guante = guante;
        this.bota = bota;
        this.collar = collar;
        this.accesorio = accesorio;
        this.anillo1 = anillo1;
        this.anillo2 = anillo2;

        // Inicializar la lista de equipo actual
        equipoActual = new ArrayList<>();
    }

    // Constructor que inicializa el equipo sin objetos
    public AlmacenEquipo() {
        // Inicializar la lista de equipo actual
        equipoActual = new ArrayList<>();
    }

    public ArrayList<Integer> obtenerIndicesEquipo() {
        ArrayList<Integer> listaEquipo = new ArrayList<>();
        for (Objeto objeto : equipoActual) {
            int id = objeto.getId();
            listaEquipo.add(id);
        }
        return listaEquipo;
    }

    public void actualizarEquipoActual(ArrayList<Integer> listaIndices) {

        equipoActual = obtenerEquipoPorIndices(listaIndices);

        for (Objeto equipo : equipoActual) {
            asignarAtributoSegunTipo(equipo);
        }
    }

    private ArrayList<Objeto> obtenerEquipoPorIndices(ArrayList<Integer> listaIndices) {
        ArrayList<Objeto> equipoActualizado = new ArrayList<>();
        for (int i : listaIndices) {
            ElementosPrincipales.inventario.objetos.stream()
                    .filter(objeto -> objeto.getId() == i)
                    .findFirst()
                    .ifPresent(equipoActualizado::add);
        }
        return equipoActualizado;
    }

    private void asignarAtributoSegunTipo(Objeto equipo) {
        if (equipo instanceof Arma) {
            if (arma1 == null) {
                this.arma1 = (Arma) equipo;
            } else if (arma2 == null) {
                this.arma2 = (Arma) equipo;
            }
        } else if (equipo instanceof ProteccionMedia) {
            armadura = (ProteccionMedia) equipo;
        } else if (equipo instanceof ProteccionAlta) {
            casco = (ProteccionAlta) equipo;
        } else if (equipo instanceof ProteccionLateral) {
            guante = (ProteccionLateral) equipo;
        } else if (equipo instanceof ProteccionBaja) {
            bota = (ProteccionBaja) equipo;
        } else if (equipo instanceof Collar) {
            collar = (Collar) equipo;
        } else if (equipo instanceof Accesorio) {
            accesorio = (Accesorio) equipo;
        } else if (equipo instanceof Anillo) {
            if (anillo1 == null) {
                this.anillo1 = (Anillo) equipo;
            } else if (anillo2 == null) {
                this.anillo2 = (Anillo) equipo;
            }
        }
    }

    // Métodos para obtener y cambiar los objetos equipados
    public Arma getArma1() {
        return arma1;
    }

    public void cambiarArma1(Arma arma1) {
        this.arma1 = arma1;
    }

    public Arma getArma2() {
        return arma2;
    }

    public void cambiarArma2(Arma arma2) {
        this.arma2 = arma2;
    }

    public Armadura getArmaduraMedia() {
        return armadura;
    }

    public void setArmaduraMedia(Armadura armadura) {
        this.armadura = armadura;
    }

    public Armadura getCasco() {
        return casco;
    }

    public void setCasco(Armadura casco) {
        this.casco = casco;
    }

    public Armadura getGuante() {
        return guante;
    }

    public void setGuante(Armadura guante) {
        this.guante = guante;
    }

    public Armadura getBota() {
        return bota;
    }

    public void setBota(Armadura bota) {
        this.bota = bota;
    }

    public Joya getCollar() {
        return collar;
    }

    public void setCollar(Joya collar) {
        this.collar = collar;
    }

    public Joya getAccesorio() {
        return accesorio;
    }

    public void setAccesorio(Joya accesorio) {
        this.accesorio = accesorio;
    }

    public Joya getAnillo1() {
        return anillo1;
    }

    public void setAnillo1(Joya anillo1) {
        this.anillo1 = anillo1;

    }

    public Joya getAnillo2() {
        return anillo2;
    }

    public void setAnillo2(Joya anillo2) {
        this.anillo2 = anillo2;
    }

    public ArrayList<Objeto> getEquipoActual() {
        return equipoActual;
    }

    public void setEquipoActual(ArrayList<Objeto> equipoActual) {
        this.equipoActual = equipoActual;
    }

}
