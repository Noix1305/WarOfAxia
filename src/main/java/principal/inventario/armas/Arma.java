/*
 * Esta clase abstracta representa un arma en el juego.
 * Proporciona métodos y atributos comunes a todas las armas.
 */
package principal.inventario.armas;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.entes.enemigo.Enemigo;
import principal.entes.jugador.Jugador;
import principal.inventario.Objeto;
import principal.inventario.TipoObjeto;
import principal.sonido.SoundThread;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public abstract class Arma extends Objeto {

    // Atributos comunes a todas las armas
    protected int alcanceFrontal;
    protected int alcanceLateral;
    protected String rutaDisparo;
    protected HojaSprites hojaArmas;
    protected int ataqueMin;
    protected int ataqueMax;
    protected boolean automatica;
    protected boolean penetrante;
    protected double ataqueXSegundo;
    protected int actualizacionesParaSgteAtaque;
    protected HojaSprites hojaArma;

    // Constructor de la clase Arma
    public Arma(int id, String nombre, String descripcion, int peso, int ataqueMin, int ataqueMax, int alcanceFrontal,
                int alcanceLateral, final TipoObjeto tipoObjeto, final boolean automatica, final boolean penetrante, final double ataquesXSegundo, final String rutaDisparo,
                String rutaPersonaje, int precioCompra, int precioVenta) {
        super(id, nombre, peso, descripcion, tipoObjeto, precioCompra, precioVenta);
        this.ataqueMin = ataqueMin;
        this.ataqueMax = ataqueMax;
        this.alcanceFrontal = alcanceFrontal;
        this.alcanceLateral = alcanceLateral;
        this.automatica = automatica;
        this.penetrante = penetrante;
        this.ataqueXSegundo = ataquesXSegundo;
        this.actualizacionesParaSgteAtaque = 0;
        this.hojaArma = new HojaSprites(rutaPersonaje, 32, false);
        this.rutaDisparo = rutaDisparo;
    }

    // Método abstracto para obtener el alcance del arma
    public ArrayList<Rectangle> getAlcance(final Jugador jugador) {
        final ArrayList<Rectangle> alcance = new ArrayList<>();

        final Rectangle alcance1 = new Rectangle();
        // 0 = abajo, 1 = izquierda, 2 = derecha, 3 = arriba
        if (jugador.getAnimacionJugador().getDireccion() == 3 || jugador.getAnimacionJugador().getDireccion() == 0) {
            alcance1.width = alcanceLateral;
            alcance1.height = alcanceFrontal * Constantes.LADO_SPRITE;

            alcance1.x = Constantes.CENTRO_VENTANA_X;
            if (jugador.getAnimacionJugador().getDireccion() == 0) {
                alcance1.y = Constantes.CENTRO_VENTANA_Y - 9;
            } else {
                alcance1.y = Constantes.CENTRO_VENTANA_Y - 9 - alcance1.height;
            }

        } else {
            alcance1.height = alcanceLateral;
            alcance1.width = alcanceFrontal * Constantes.LADO_SPRITE;

            alcance1.y = Constantes.CENTRO_VENTANA_Y - 3;

            if (jugador.getAnimacionJugador().getDireccion() == 1) {
                alcance1.x = Constantes.CENTRO_VENTANA_X - alcance1.width;
            } else {
                alcance1.x = Constantes.CENTRO_VENTANA_X;
            }
        }

        alcance.add(alcance1);

        return alcance;
    }

    // Método para actualizar el arma
    public void actualizar() {
        if (actualizacionesParaSgteAtaque > 0) {
            actualizacionesParaSgteAtaque--;
        }
    }

    // Método para realizar un ataque con el arma
    public void atacar(final ArrayList<Enemigo> enemigos, int atributo) {
        if (!enemigos.isEmpty()) {
            if (actualizacionesParaSgteAtaque > 0) {
                return;
            }
            actualizacionesParaSgteAtaque = (int) (ataqueXSegundo * 60);
            ElementosPrincipales.reproductor.sonidoArma.cambiarArchivo(rutaDisparo);
            ElementosPrincipales.reproductor.sonidoArma.reproducir(0.7f);

            ElementosPrincipales.jugador.getCronometro().reiniciar();
            ElementosPrincipales.jugador.getAccionesJugador().setPreparado(true);

            double numeroAleatorio = new Random().nextDouble(100) + 1;
            int multiplicadorCritico = 1;
            boolean esCritico = numeroAleatorio <= ElementosPrincipales.jugador.getGestorAt().getCritico();
            int rango = this.getAtaqueMax() - this.getAtaqueMin() + 1;
            int ataqueAleatorio = new Random().nextInt(rango) + this.getAtaqueMin();
            int danioBase = ataqueAleatorio + atributo;

            if (esCritico) {
                multiplicadorCritico = 2;
            }

            float danioTotal = danioBase * multiplicadorCritico;

            for (Enemigo enemigo : enemigos) {
                enemigo.perderVida(danioTotal, esCritico);
            }
        }
    }

    // Métodos getter y setter para los atributos del arma
    public boolean isAutomatica() {
        return automatica;
    }

    public void setAutomatica(boolean automatica) {
        this.automatica = automatica;
    }

    public boolean isPenetrante() {
        return penetrante;
    }

    public void setPenetrante(boolean penetrante) {
        this.penetrante = penetrante;
    }

    public int getAtaqueMedio() {
        return new Random().nextInt(ataqueMax - ataqueMin + 1) + ataqueMin;
    }

    @Override
    public Sprite getSprite() {
        return hojaArmas.getSprites(id - 500);
    }

    public int getAtaque() {
        return (int) (ataqueMin + ataqueMax) / 2;
    }

    public int getAlcanceInt() {
        return alcanceFrontal;
    }

    public HojaSprites getHojaArma() {
        return hojaArma;
    }

    public int getAtaqueMin() {
        return ataqueMin;
    }

    public int getAtaqueMax() {
        return ataqueMax;
    }
}
