package principal.maquinaestado.menujuego.menuEquipables;

import principal.Constantes;
import principal.herramientas.DibujoDebug;
import principal.herramientas.MedidorString;

import java.awt.*;

public class EstructuraMenuEquipable {

    private int x = 156;
    private int y = 44;
    private int width = 172;
    private int height = (Constantes.ALTO_JUEGO - 36) - 8 * 2;
    private Rectangle margen = new Rectangle(x, y, width, height);
    private Rectangle titularPanelEquipo = new Rectangle(x, y, width, 24);
    private Rectangle subPanel = new Rectangle(x - 105, y + 58, 104, 240);
    public final int MARGEN_HORIZONTAL_ETIQUETAS;
    public final int MARGEN_VERTICAL_ETIQUETAS;
    public final int ALTO_ETIQUETAS;
    public final int ANCHO_ETIQUETAS;

    public EstructuraMenuEquipable() {
        MARGEN_HORIZONTAL_ETIQUETAS = 10;
        MARGEN_VERTICAL_ETIQUETAS = 10;
        ALTO_ETIQUETAS = 20;
        ANCHO_ETIQUETAS = 85;
    }

    public void dibujar(Graphics g) {
        DibujoDebug.dibujarRectanguloContorno(g, margen, Color.darkGray);
        DibujoDebug.dibujarRectanguloRelleno(g, titularPanelEquipo, Color.darkGray);
        DibujoDebug.dibujarRectanguloRelleno(g, subPanel, Color.DARK_GRAY);
        DibujoDebug.dibujarString(g, "EQUIPABLES", new Point(
                margen.x + titularPanelEquipo.width / 2 - MedidorString.medirAnchoPixeles(g, "EQUIPABLES") / 2,
                margen.y + titularPanelEquipo.height - MedidorString.medirAltoPixeles(g, "EQUIPABLES") - 4),Color.white);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getMargen() {
        return margen;
    }

    public void setMargen(Rectangle margen) {
        this.margen = margen;
    }

    public Rectangle getTitularPanelEquipo() {
        return titularPanelEquipo;
    }

    public void setTitularPanelEquipo(Rectangle titularPanelEquipo) {
        this.titularPanelEquipo = titularPanelEquipo;
    }

    public Rectangle getSubPanel() {
        return subPanel;
    }

    public void setSubPanel(Rectangle subPanel) {
        this.subPanel = subPanel;
    }
}
