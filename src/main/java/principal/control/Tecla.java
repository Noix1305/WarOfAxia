package principal.control;

public class Tecla {

    private boolean pulsada = false; // Indica si la tecla está pulsada o no
    private boolean procesada = false; // Indica si la pulsación ya ha sido procesada
    private long ultimaPulsacion = System.nanoTime(); // Tiempo de la última pulsación

    public void teclaPulsada() {
        if (!pulsada) {
            pulsada = true;
            procesada = false; // Permite procesar la pulsación
            ultimaPulsacion = System.nanoTime();
        }
    }

    public void teclaLiberada() {
        pulsada = false;
        procesada = false; // Resetea el estado para la próxima pulsación
    }

    public boolean estaPulsada() {
        return pulsada;
    }

    public boolean puedeProcesarse() {
        return pulsada && !procesada; // Solo se procesa si no ha sido manejada
    }

    public void marcarComoProcesada() {
        procesada = true;
    }

    public long getUltimaPulsacion() {
        return ultimaPulsacion;
    }
}
