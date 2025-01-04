package principal.habilidades;

import principal.ElementosPrincipales;
import principal.entes.Entidad;
import principal.entes.enemigo.Enemigo;
import principal.entes.jugador.Jugador;
import principal.inventario.TipoObjeto;
import principal.sonido.ReproductorSonido;
import principal.sonido.SoundThread;

import java.io.Serializable;

/**
 * Clase que representa la habilidad de curación.
 */
public class Curacion extends Habilidad implements Serializable {

    // Atributos específicos de la habilidad de curación
    private final int cantidadCuracionBase; // Cantidad base de curación
    private final double montoAdicionalPorInteligencia; // Monto adicional de curación basado en la inteligencia
    private final int tiempoCarga; // Tiempo de carga de la habilidad

    /**
     * Constructor de la habilidad de curación.
     *
     * @param nombre                        El nombre de la habilidad.
     * @param duracion                      La duración de la habilidad.
     * @param tiempoCarga                   El tiempo de carga de la habilidad.
     * @param objetivo                      El objetivo de la habilidad.
     * @param manaUtilizado                 La cantidad de maná utilizada por la habilidad.
     * @param vidaUtilizada                 La cantidad de vida utilizada por la habilidad.
     * @param cantidadCuracionBase          La cantidad base de curación.
     * @param montoAdicionalPorInteligencia El monto adicional de curación por inteligencia.
     * @param indiceSprite                  El índice del sprite de la habilidad.
     * @param activaPasiva                  El tipo de activación de la habilidad.
     * @param tipoHabilidad                 El tipo de habilidad.
     */
    public Curacion(int id, String nombre, int duracion, int tiempoCarga,
                    Object objetivo, int manaUtilizado, int vidaUtilizada,
                    int cantidadCuracionBase, int montoAdicionalPorInteligencia, int indiceSprite, TipoObjeto activaPasiva,
                    TipoObjeto tipoHabilidad, double alcance, int tiempoReutilizacion) {
        super(id, nombre, duracion, manaUtilizado, vidaUtilizada, indiceSprite, activaPasiva, tipoHabilidad, 0, tiempoReutilizacion);
        this.cantidadCuracionBase = cantidadCuracionBase;
        this.montoAdicionalPorInteligencia = montoAdicionalPorInteligencia;
        super.setTiempoReutilizacion(0);
        this.tiempoCarga = tiempoCarga;
        super.setDescripcion("Restaura 30 pts de VIT + \nun adicional basado en\nla INT del conjurador");

    }

    /**
     * Método para aplicar el efecto de la habilidad de curación.
     *
     * @param tipoCuracion El tipo de curación (no utilizado en este caso).
     */
    @Override
    public void aplicarEfecto(Entidad atacante, Entidad objetivo, TipoObjeto tipoCuracion) {
        curacionAutomatica(objetivo); // Aplicar curación automática
    }

    /**
     * Método para aplicar la curación automáticamente.
     *
     * @param entidad El objeto sobre el cual se aplica la curación.
     */
    private void curacionAutomatica(Entidad entidad) {
        if (cronometro.obtenerTiempoTranscurrido() / 1000 >= getTiempoReutilizacion()) {
            if (entidad.gestorAtributos.getVidaActual() < entidad.gestorAtributos.getVidaMaxima() &&
                    entidad.gestorAtributos.getMana() >= getManaUtilizado()) {
                ElementosPrincipales.jugador.getCronometro().reiniciar();

                int cantidadTotalCuracion = cantidadCuracionBase + calcularMontoAdicionalPorInteligencia(entidad);
                super.setMontoTotal(cantidadTotalCuracion);

                // Verificar si la entidad es curable
                if (entidad instanceof Jugador jugador) {
                    System.out.println("Mana Jugador: " + jugador.gestorAtributos.getMana());
                    System.out.println("Mana Habilidad: " + getManaUtilizado());
                    jugador.curarVida(cantidadTotalCuracion);
                    jugador.gestorAtributos.setMana(jugador.gestorAtributos.getMana() - getManaUtilizado());


                }

                super.setTiempoReutilizacion(tiempoCarga);
                ElementosPrincipales.reproductor.sonidoHeal.reproducir(0.7f);
                cronometro.reiniciar();
            }
        }
    }

    private int calcularMontoAdicionalPorInteligencia(Entidad entidad) {
        return (int) (entidad.gestorAtributos.getInteligencia() * montoAdicionalPorInteligencia);
    }
}
