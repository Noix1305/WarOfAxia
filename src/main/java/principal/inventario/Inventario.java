/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.inventario;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import principal.ElementosPrincipales;
import principal.entes.enemigo.Enemigo;
import principal.entes.enemigo.RegistroEnemigos;
import principal.habilidades.GestorHabilidades;
import principal.habilidades.Habilidad;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armas.Arma;
import principal.inventario.armas.ArmaDosManos;
import principal.inventario.armas.ArmaUnaMano;
import principal.inventario.consumibles.Claves;
import principal.inventario.consumibles.Consumible;
import principal.inventario.joyas.Joya;

/**
 * @author GAMER ARRAX
 */
public class Inventario implements Serializable {

    @Serial
    private static final long serialVersionUID = 123456789L;
    public ArrayList<Objeto> objetos;
    public ArrayList<Habilidad> habilidades;
    public ArrayList<Objeto> objetosTienda;
    public ArrayList<Enemigo> enemigosEliminados;

    public int dinero;

    public Inventario() {

        objetos = new ArrayList<>();
        habilidades = new ArrayList<>();
        objetosTienda = ElementosPrincipales.mapa.objetosTienda;
        dinero = 20000;
        Enemigo enemigo1 = RegistroEnemigos.obtenerEnemigo(1);
        Enemigo enemigo2 = RegistroEnemigos.obtenerEnemigo(2);
        Enemigo enemigo3 = RegistroEnemigos.obtenerEnemigo(3);
        Enemigo enemigo4 = RegistroEnemigos.obtenerEnemigo(4);
        Enemigo enemigo5 = RegistroEnemigos.obtenerEnemigo(5);
        Enemigo enemigo6 = RegistroEnemigos.obtenerEnemigo(6);
        Enemigo enemigo7 = RegistroEnemigos.obtenerEnemigo(7);
        Enemigo enemigo8 = RegistroEnemigos.obtenerEnemigo(8);
        Enemigo enemigo9 = RegistroEnemigos.obtenerEnemigo(9);
        Enemigo enemigo10 = RegistroEnemigos.obtenerEnemigo(10);

        enemigosEliminados = new ArrayList<>(List.of(enemigo1, enemigo2, enemigo3, enemigo4, enemigo5, enemigo6,
                enemigo7, enemigo8, enemigo9, enemigo10));

    }

    public boolean incrementarObjeto(final Objeto objeto, final int cantidad) {
        boolean incrementado = false;

        for (Objeto objetoActual : objetos) {
            if (objetoActual.getId() == objeto.getId()) {
                objetoActual.incrementarCantidad(cantidad);
                incrementado = true;
                break;
            }
        }
        return incrementado;
    }

    public ArrayList<Integer> obtenerIndiceEnemigosBestiario() {
        ArrayList<Integer> listaIndices = new ArrayList<>();
        for (Enemigo enemigo : enemigosEliminados) {
            int indice = enemigo.gestorAtributos.getIdEnemigo();
            listaIndices.add(indice);
        }
        System.out.println(listaIndices);
        return listaIndices;
    }

    public ArrayList<Enemigo> actualizarListaEnemigos(ArrayList<Integer> listaIndices) {
        ArrayList<Enemigo> listaEnemigosCreados = new ArrayList<>();
        for (int i : listaIndices) {
            Enemigo enemigo = RegistroEnemigos.obtenerEnemigo(i);
            listaEnemigosCreados.add(enemigo);
            System.out.println("Enemigo creado: " + enemigo.gestorAtributos.getNombre());
        }

        return listaEnemigosCreados;
    }

    public ArrayList<Integer[]> obtenerListaIndicesObjetos() {
        ArrayList<Integer[]> listaIndices = new ArrayList<>();
        for (Objeto objeto : objetos) {
            int indice = objeto.getId();
            int cantidadObjeto = objeto.getCantidad();
            Integer[] par = {indice, cantidadObjeto}; // Crear un arreglo con los dos valores
            listaIndices.add(par); // Añadir el arreglo a la lista
        }
        System.out.println(listaIndices);
        return listaIndices;
    }

    public ArrayList<Integer> obtenerIndicesHabilidades() {
        ArrayList<Integer> listaIndicesHabilidades = new ArrayList<>();
        for (Habilidad habilidad : habilidades) {
            int id = habilidad.getId();
            listaIndicesHabilidades.add(id);
        }

        return listaIndicesHabilidades;
    }

    public ArrayList<Habilidad> actualizarListaHabilidades(ArrayList<Integer> indices) {
        ArrayList<Habilidad> habilidades = new ArrayList<>();
        for (int i : indices) {
            Habilidad habilidadCreada = GestorHabilidades.obtenerHabilidad(i);
            habilidades.add(habilidadCreada);
            System.out.println(habilidadCreada.getNombre());
        }
        return habilidades;
    }


    public ArrayList<Objeto> actualizarInventarioMochila(ArrayList<Integer[]> listaIndices) {
        ArrayList<Objeto> listaObjetos = new ArrayList<>();

        for (Integer[] indiceYCantidad : listaIndices) {
            // Obtener índice y cantidad del arreglo
            int indice = indiceYCantidad[0];
            int cantidad = indiceYCantidad[1];

            // Crear objeto usando el índice
            Objeto objetoCreado = RegistroObjetos.obtenerObjeto(indice);

            // Configurar la cantidad en el objeto
            objetoCreado.setCantidad(cantidad);

            // Agregar el objeto a la lista
            listaObjetos.add(objetoCreado);
        }

        // Imprimir los nombres de los objetos actualizados
        for (Objeto objeto : listaObjetos) {
            System.out.println(objeto.getNombre());
        }

        return listaObjetos;
    }

    public void anadirHabilidad(Habilidad habilidadEntrante) {
        boolean existe = false;

        for (Habilidad habilidad : habilidades) {
            if (habilidad.getId() == habilidadEntrante.getId()) {
                existe = true;
                System.out.println("Habilidad Existe");
                break;
            }
        }

        if (!existe) {
            habilidades.add(habilidadEntrante);
        }
    }


    public void recogerObjetos(final ObjetoUnicoTiled out) {
        if (objetoExiste(out.getObjeto())) {
            incrementarObjeto(out.getObjeto(), out.getObjeto().getCantidad());
        }
        if (!objetoExiste(out.getObjeto())) {
            objetos.add(out.getObjeto());
        }

    }

    /*public void recogerObjetos(final ContenedorObjetos contenedor) {
        for (Objeto objeto : contenedor.getObjetos()) {
            if (objetoExiste(objeto)) {
                incrementarObjeto(objeto, objeto.getCantidad());
            }
            if(!objetoExiste(objeto)){
                objetos.add(objeto);
            }
        }
    }*/
    public boolean objetoExiste(final Objeto objeto) {
        boolean existe = false;

        for (Objeto objetoActual : objetos) {
            if (objetoActual.getId() == objeto.getId()) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    public ArrayList<Objeto> getConsumibles() {
        ArrayList<Objeto> consumibles = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof Consumible) {
                consumibles.add(objeto);
            }
        }
        return consumibles;
    }

    public ArrayList<Habilidad> getHabilidades() {
        return habilidades;
    }

    public ArrayList<Objeto> getClaves() {
        ArrayList<Objeto> claves = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof Claves) {
                claves.add(objeto);
            }
        }
        return claves;
    }

    public void setHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }

    public ArrayList<Objeto> getEquipo() {
        ArrayList<Objeto> equipo = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof Arma) {
                equipo.add(objeto);
            } else if (objeto instanceof Armadura) {
                equipo.add(objeto);
            } else if (objeto instanceof Joya) {
                equipo.add(objeto);
            }
        }
        return equipo;
    }

    public ArrayList<Objeto> getArmas() {
        ArrayList<Objeto> armas = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof Arma) {
                armas.add(objeto);
            }
        }
        return armas;
    }

    public ArrayList<ArmaUnaMano> getUnaMano() {
        ArrayList<ArmaUnaMano> armas = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof ArmaUnaMano) {
                armas.add((ArmaUnaMano) objeto);
            }
        }
        return armas;
    }

    public ArrayList<Objeto> getUnaMano(int i) {
        ArrayList<Objeto> armas = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof ArmaUnaMano) {
                armas.add(objeto);
            }
        }
        return armas;
    }


    public ArrayList<ArmaDosManos> getDosManos() {
        ArrayList<ArmaDosManos> armas = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof ArmaDosManos) {
                armas.add((ArmaDosManos) objeto);
            }
        }
        return armas;
    }

    public ArrayList<Objeto> getDosManos(int i) {
        ArrayList<Objeto> armas = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof ArmaDosManos) {
                armas.add(objeto);
            }
        }
        return armas;
    }

    public ArrayList<Armadura> getArmaduras() {
        ArrayList<Armadura> armaduras = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof Armadura) {
                armaduras.add((Armadura) objeto);
            }
        }
        return armaduras;
    }

    public ArrayList<Joya> getJoyas() {
        ArrayList<Joya> joyas = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof Joya) {
                joyas.add((Joya) objeto);
            }
        }
        return joyas;
    }

    public ArrayList<Objeto> getJoyas(int i) {
        ArrayList<Objeto> joyas = new ArrayList<>();

        for (Objeto objeto : objetos) {
            if (objeto instanceof Joya) {
                joyas.add(objeto);
            }
        }
        return joyas;
    }

    public Objeto getObjeto(final int id) {
        for (Objeto objetoActual : objetos) {
            if (objetoActual.getId() == id) {
                return objetoActual;
            }
        }
        return null;
    }

    public ArrayList<Objeto> getListaObjetos() {
        return this.objetos;
    }

    public ArrayList<Objeto> getObjetosTienda() {
        return objetosTienda;
    }

    public void agregarObjeto(Objeto objeto) {
        this.objetosTienda.add(objeto);
    }

    public int getDinero() {
        return dinero;
    }

    public void setDinero(int dinero) {
        this.dinero = dinero;
    }


}
