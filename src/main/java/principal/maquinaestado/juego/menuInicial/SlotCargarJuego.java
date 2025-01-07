package principal.maquinaestado.juego.menuInicial;

import java.awt.*;
import java.io.File;

public class SlotCargarJuego {
    private File archivo;
    private Rectangle slot;

    public SlotCargarJuego(File archivo, Rectangle slot) {
        this.archivo = archivo;
        this.slot = slot;
    }

    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    public Rectangle getSlot() {
        return slot;
    }

    public void setSlot(Rectangle slot) {
        this.slot = slot;
    }
}
