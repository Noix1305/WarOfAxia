package principal.mapas;

import principal.Constantes;
import principal.entes.enemigo.Enemigo;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

import java.awt.*;
import java.util.ArrayList;

public class HudEnemigos {

    private final int anchoVentana;
    private final Point posicionVentana;
    private final HojaSprites hudEnemigos;
    private final ArrayList<Enemigo> listaEnemigos;

    public HudEnemigos(ArrayList<Enemigo> listaenemigos) {

        this.anchoVentana = 100;
        this.posicionVentana = new Point(Constantes.ANCHO_JUEGO - 100, Constantes.ALTO_JUEGO - 300);
        this.listaEnemigos = listaenemigos;
        this.hudEnemigos = new HojaSprites(Constantes.RUTA_HUD_ENEMIGOS, 100, 230, true);
    }

    public void dibujar(Graphics g) {
        if (!listaEnemigos.isEmpty()) {
            DibujoDebug.dibujarImagen(g, hudEnemigos.getSprites(0).getImagen(), posicionVentana);
            dibujarEnemigosEnHub(g, this.listaEnemigos);
        }
    }

    private void dibujarEnemigosEnHub(Graphics g, ArrayList<Enemigo> listaEnemigos) {
        int x = this.posicionVentana.x + 5;
        int y = this.posicionVentana.y + 30;
        int i = 1;
        for (Enemigo enemigo : listaEnemigos) {
            if (i < 13) {
                DibujoDebug.dibujarImagen(g, enemigo.getHojaHud().getSprites(0).getImagen(), x, y);
                enemigo.dibujarBarraVida(g, x + 16, y + 8, 6, anchoVentana - 24);
                y += 18;
                i++;
            } else {
                return;
            }
        }
    }
}
