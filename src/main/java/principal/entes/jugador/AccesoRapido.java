/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package principal.entes.jugador;

import principal.ElementosPrincipales;
import principal.habilidades.Habilidad;
import principal.inventario.Objeto;
import principal.inventario.RegistroObjetos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa un conjunto de accesos rápidos para equipar objetos. Proporciona un conjunto de accesos rápidos
 * que permiten equipar objetos en el juego. Permite establecer y obtener objetos en índices específicos dentro del
 * conjunto, verificando si los índices son válidos y manejando casos de índices fuera de rango.
 */
public class AccesoRapido {

    public Object[] accesosEquipados; // Array para almacenar los objetos equipados

    // Constructor de la clase AccesoRapido
    public AccesoRapido() {
        // Inicializar el array con tamaño 10
        accesosEquipados = new Object[10];
    }

    // Método para obtener todos los objetos equipados
    public Object[] getAccesosEquipados() {
        return accesosEquipados;
    }

    // Método para establecer un objeto en un índice específico de los accesos rápidos
    public void setAccesosEquipados(Object objetoEquipar, int indice) {
        // Verificar si el índice es válido
        if (indice >= 0 && indice < accesosEquipados.length) {
            accesosEquipados[indice] = objetoEquipar;
        } else {
            // Manejar el caso cuando el índice no es válido (por ejemplo, lanzar una excepción)
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    public ArrayList<Integer[]> obtenerIndiceYObjeto() {
        ArrayList<Integer[]> accesos = new ArrayList<>();

        for (int i = 0; i < accesosEquipados.length; i++) {
            Object acceso = accesosEquipados[i];

            if (acceso instanceof Objeto objeto) {
                // Asumiendo que Objeto tiene un método getId()
                Integer[] par = {i, objeto.getId(), 0};
                accesos.add(par); // Añadir el par a la lista
            } else if (acceso instanceof Habilidad habilidad) {
                Integer[] par = {i, habilidad.getId(), 1}; // Asignar cantidad 1 por defecto para habilidades
                accesos.add(par); // Añadir el par a la lista
            }
        }

        return accesos;
    }

    public void actualizarAccesos(ArrayList<Integer[]> listaAccesos) {
        if (listaAccesos == null || listaAccesos.isEmpty()) {
            return; // No hay accesos que actualizar
        }

        for (Integer[] indiceConId : listaAccesos) {
            // Validar que el array tenga 3 elementos
            if (indiceConId.length != 3) {
                throw new IllegalArgumentException("Cada elemento debe contener índice, ID y tipo de acceso");
            }

            int indice = indiceConId[0];
            int id = indiceConId[1];
            int tipoAcceso = indiceConId[2]; // 0: Objeto, 1: Habilidad

            switch (tipoAcceso) {
                case 0:
                    // Buscar y asignar un Objeto
                    ElementosPrincipales.inventario.objetos
                            .stream()
                            .filter(objeto -> objeto.getId() == id)
                            .findFirst().ifPresent(objetoEncontrado -> setAccesosEquipados(objetoEncontrado, indice));

                    break;

                case 1:
                    // Buscar y asignar una Habilidad
                    ElementosPrincipales.inventario.habilidades
                            .stream()
                            .filter(habilidad -> habilidad.getId() == id)
                            .findFirst().ifPresent(habilidadEncontrada -> setAccesosEquipados(habilidadEncontrada, indice));

                    break;

                default:
                    throw new IllegalArgumentException("Tipo de acceso no válido: " + tipoAcceso);
            }
        }
    }



    // Método para obtener un objeto equipado en un índice específico
    public Object getAccesoEquipado(int indice) {
        // Verificar si el índice es válido
        if (indice >= 0 && indice < accesosEquipados.length) {
            return accesosEquipados[indice];
        } else {
            // Manejar el caso cuando el índice no es válido (por ejemplo, lanzar una excepción)
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

}
