package principal.maquinaestado.menujuego.menuEquipables;

import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.inventario.Objeto;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionMedia;

import java.awt.*;

public class MenuArmaduras extends SeccionMenuEquipable {
    public MenuArmaduras(String habilidades, Rectangle etiquetaHabilidades, EstructuraMenuEquipable estructuraMenu,int numeroSeccion) {
        super(habilidades, etiquetaHabilidades, estructuraMenu,numeroSeccion);
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
            if (objeto instanceof ProteccionMedia) {
                super.dibujarObjetoPosicionMenu(g, objeto);
            }
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionMedia) {
                super.actualizarPosicionMenuObjeto(objeto, contador);
                contador++;
            }
        }
    }
}
