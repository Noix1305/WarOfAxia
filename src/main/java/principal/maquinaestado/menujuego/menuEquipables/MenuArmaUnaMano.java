package principal.maquinaestado.menujuego.menuEquipables;

import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DibujoDebug;
import principal.herramientas.MedidorString;
import principal.inventario.Objeto;
import principal.inventario.armas.ArmaUnaMano;

import java.awt.*;
import java.util.ArrayList;

public class MenuArmaUnaMano extends SeccionMenuEquipable {

    public MenuArmaUnaMano(String nombreSeccion, Rectangle etiquetaMenu, EstructuraMenuEquipable estructuraMenu,int numeroSeccion) {
        super(nombreSeccion, etiquetaMenu, estructuraMenu,numeroSeccion);

    }

    @Override
    public void actualizar() {
        super.actualizarObjetoSeleccionado();
        actualizarPosicionMenu();
    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);
    }

    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objetoActual : ElementosPrincipales.inventario.getUnaMano()) {
            super.dibujarObjetoPosicionMenu(g, objetoActual);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (ArmaUnaMano arma : ElementosPrincipales.inventario.getUnaMano()) {
            super.actualizarPosicionMenuObjeto(arma, contador);
            contador++;
        }
    }


}
