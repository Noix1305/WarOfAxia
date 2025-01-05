package principal.maquinaestado.menujuego.menuEquipables;

import principal.ElementosPrincipales;
import principal.graficos.SuperficieDibujo;
import principal.inventario.Objeto;

import java.awt.*;

public class MenuArmaDosManos extends SeccionMenuEquipable {
    public MenuArmaDosManos(String equipo, Rectangle etiquetaEquipo, EstructuraMenuEquipable estructuraMenu, int numeroSeccion) {
        super(equipo, etiquetaEquipo, estructuraMenu, numeroSeccion);
    }

    @Override
    public void actualizar() {

    }

    @Override
    public void dibujar(Graphics g) {
        dibujarObjetosEquipables(g);

    }

    @Override
    public void dibujarObjetosEquipables(Graphics g) {
        for (Objeto objetoActual : ElementosPrincipales.inventario.getDosManos()) {
            super.dibujarObjetoPosicionMenu(g, objetoActual);
        }
        super.dibujarObjetoSeleccionado(g);
    }

    @Override
    public void actualizarPosicionMenu() {

    }
}
