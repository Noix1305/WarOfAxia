package principal.maquinaestado.menujuego.menuEquipables;

import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.inventario.Objeto;
import principal.inventario.armaduras.ProteccionAlta;
import principal.inventario.armaduras.ProteccionLateral;

import java.awt.*;

public class MenuGuantes extends SeccionMenuEquipable {
    public MenuGuantes(String crecimiento, Rectangle etiquetaCrecimiento, EstructuraMenuEquipable estructuraMenu,int numeroSeccion) {
        super(crecimiento, etiquetaCrecimiento, estructuraMenu,numeroSeccion);
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
            if (objeto instanceof ProteccionLateral) {
                super.dibujarObjetoPosicionMenu(g, objeto);
            }
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {
        int contador = 0;
        for (Objeto objeto : ElementosPrincipales.inventario.getArmaduras()) {
            if (objeto instanceof ProteccionLateral) {
                super.actualizarPosicionMenuObjeto(objeto, contador);
                contador++;
            }
        }
    }
}
