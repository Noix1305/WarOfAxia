package principal.maquinaestado.menujuego.menuEquipables;

import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.inventario.Objeto;
import principal.inventario.armaduras.ProteccionAlta;

import java.awt.*;

public class MenuCascos extends SeccionMenuEquipable {
    public MenuCascos(String bestiario, Rectangle etiquetaBestiario, EstructuraMenuEquipable estructuraMenu,int numeroSeccion) {
        super(bestiario, etiquetaBestiario, estructuraMenu,numeroSeccion);
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

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionAlta) {
                super.dibujarObjetoPosicionMenu(g, objeto);
            }
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionAlta) {
                super.actualizarPosicionMenuObjeto(objeto, contador);
                contador++;
            }
        }
    }
}
