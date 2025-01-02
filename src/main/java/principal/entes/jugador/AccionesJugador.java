package principal.entes.jugador;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.entes.GestorAtributos;

import java.awt.*;
import java.io.Serializable;

import static principal.ElementosPrincipales.reproductor;

public class AccionesJugador implements Serializable{
    private double posicionX;
    private double posicionY;
    private double velocidadCaminar = 1;
    private final double velocidadCorrer = velocidadCaminar * 2;
    private boolean enMovimiento = false;
    private boolean atacando = false;
    private boolean usandoSkill = false;
    private boolean sobrepeso = false;
    private boolean preparado = false;
    private int animacion;
    private int estado;
    // Estado de subida de nivel del jugador
    private boolean subirNivel = false;
    private int recuperacion = 60;
    private int recuperacionVida = 60;
    private boolean recuperado = true;

    // Dimensiones del jugador
    private final int ANCHO_JUGADOR = 16;
    private final int ALTO_JUGADOR = 16;

    // Límites del área de juego
    private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2,
            Constantes.CENTRO_VENTANA_Y, ANCHO_JUGADOR, 1);
    private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2,
            Constantes.CENTRO_VENTANA_Y + ALTO_JUGADOR, ANCHO_JUGADOR, 1);
    private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_JUGADOR / 2,
            Constantes.CENTRO_VENTANA_Y, 4, ALTO_JUGADOR);
    private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_JUGADOR / 2,
            Constantes.CENTRO_VENTANA_Y, 4, ALTO_JUGADOR);

    public AccionesJugador() {
        this.setPosicionX(ElementosPrincipales.mapa.getPuntoInicial().getX());
        this.setPosicionY(ElementosPrincipales.mapa.getPuntoInicial().getY());
        this.setAnimacion(0);
        this.setEstado(1);

    }

    private void mover(int velocidadX, int velocidadY, GestorAtributos gestorAtributos, AnimacionJugador animacionJugador) {
        // Indicar que el personaje está en movimiento
        setEnMovimiento(true);

        // Cambiar la dirección del personaje según la velocidad en los ejes X e Y
        cambiarDireccion(velocidadX, velocidadY, animacionJugador);

        // Verificar si el movimiento está dentro del mapa
        if (!fueraMapa(velocidadX, velocidadY)) {
            // Mover el personaje si no hay colisión en la dirección indicada
            if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
                this.setPosicionX(this.getPosicionX() + velocidadX * this.getVelocidadCaminar());
                // Restar resistencia si el personaje está corriendo
                restarResistencia(gestorAtributos);
                return;
            }
            if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
                this.setPosicionX(this.getPosicionX() + velocidadX * this.getVelocidadCaminar());
                // Restar resistencia si el personaje está corriendo
                restarResistencia(gestorAtributos);
                return;
            }

            if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
                this.setPosicionY(this.getPosicionY() + velocidadY * this.getVelocidadCaminar());
                // Restar resistencia si el personaje está corriendo
                restarResistencia(gestorAtributos);
                return;
            }
            if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
                this.setPosicionY(this.getPosicionY() + velocidadY * this.getVelocidadCaminar());
                // Restar resistencia si el personaje está corriendo
                restarResistencia(gestorAtributos);
            }
        }
    }


    private void cambiarDireccion(int velocidadX, int velocidadY, AnimacionJugador animacionJugador) {
        //mov derecha
        if (velocidadX == 1) {
            animacionJugador.setDireccion(2);
        }
        //mov izquierda
        else if (velocidadX == -1) {
            animacionJugador.setDireccion(1);
        }
        //mov abajo
        else if (velocidadY == 1) {
            animacionJugador.setDireccion(0);
        }
        //mov arriba
        else if (velocidadY == -1) {
            animacionJugador.setDireccion(3);
        }

        reproductor.sonidoCaminar1.reproducir(0.7f);
    }


    public void determinarDireccion(GestorAtributos gestorAtributos, AnimacionJugador animacionJugador) {
        // Evaluar la velocidad en los ejes X e Y
        final int velocidadX = evaluarVelocidadX();
        final int velocidadY = evaluarVelocidadY();

        // Si no hay movimiento en ningún eje, salir del método
        if (velocidadX == 0 && velocidadY == 0) {
            return;
        }

        // Si hay movimiento solo en un eje, mover en esa dirección
        if (velocidadX == 0 || velocidadY == 0) {
            mover(velocidadX, velocidadY, gestorAtributos, animacionJugador);
        } else {
            // Si hay movimiento en ambos ejes (diagonal), determinar la dirección basándose en la última tecla presionada

            // Diagonal izquierda arriba
            if (velocidadX == -1 && velocidadY == -1) {
                if (GestorControles.teclado.izquierda.getUltimaPulsacion()
                        > GestorControles.teclado.arriba.getUltimaPulsacion()) {
                    mover(velocidadX, 0, gestorAtributos, animacionJugador);
                } else {
                    mover(0, velocidadY, gestorAtributos, animacionJugador);
                }
            }
            // Diagonal izquierda abajo
            if (velocidadX == -1 && velocidadY == 1) {
                if (GestorControles.teclado.izquierda.getUltimaPulsacion()
                        > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velocidadX, 0, gestorAtributos, animacionJugador);
                } else {
                    mover(0, velocidadY, gestorAtributos, animacionJugador);
                }
            }
            // Diagonal derecha arriba
            if (velocidadX == 1 && velocidadY == -1) {
                if (GestorControles.teclado.derecha.getUltimaPulsacion()
                        > GestorControles.teclado.arriba.getUltimaPulsacion()) {
                    mover(velocidadX, 0, gestorAtributos, animacionJugador);
                } else {
                    mover(0, velocidadY, gestorAtributos, animacionJugador);
                }
            }
            // Diagonal derecha abajo
            if (velocidadX == 1 && velocidadY == 1) {
                if (GestorControles.teclado.derecha.getUltimaPulsacion()
                        > GestorControles.teclado.abajo.getUltimaPulsacion()) {
                    mover(velocidadX, 0, gestorAtributos, animacionJugador);
                } else {
                    mover(0, velocidadY, gestorAtributos, animacionJugador);
                }
            }
        }
    }

    private void restarResistencia(GestorAtributos gestorAtributos) {
        // Restar resistencia si el personaje está corriendo y aún tiene resistencia disponible
        if (GestorControles.teclado.corriendo && gestorAtributos.getResistencia() > 0) {
            gestorAtributos.setResistencia(gestorAtributos.getResistencia() - 1);
        }
    }

    private boolean fueraMapa(final int velocidadX, final int velocidadY) {
        // Calcular la posición futura del personaje después de aplicar la velocidad
        int posicionFuturaX = (int) this.getPosicionX() + velocidadX * (int) this.getVelocidadCaminar();
        int posicionFuturaY = (int) this.getPosicionY() + velocidadY * (int) this.getVelocidadCaminar();

        // Obtener los bordes del mapa en la posición futura
        final Rectangle bordesMapa = ElementosPrincipales.mapa.getBordes(posicionFuturaX, posicionFuturaY);

        return !LIMITE_ARRIBA.intersects(bordesMapa) && !LIMITE_ABAJO.intersects(bordesMapa)
                && !LIMITE_DERECHA.intersects(bordesMapa) && !LIMITE_IZQUIERDA.intersects(bordesMapa);
    }

    private boolean enColisionArriba(int velocidadY) {
        // Iterar sobre las áreas de colisión del mapa
        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionActualizadas.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColisionActualizadas.get(r);

            // Calcular la posición futura en el eje Y
            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) this.getVelocidadCaminar() + 3 * (int) this.getVelocidadCaminar();

            // Crear un rectángulo que representa la posición futura
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            // Verificar si hay colisión con el límite superior del personaje
            if (LIMITE_ARRIBA.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private boolean enColisionAbajo(int velocidadY) {
        // Iterar sobre las áreas de colisión del mapa
        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionActualizadas.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColisionActualizadas.get(r);

            // Calcular la posición futura en el eje Y
            int origenX = area.x;
            int origenY = area.y + velocidadY * (int) this.getVelocidadCaminar() - 3 * (int) this.getVelocidadCaminar();

            // Crear un rectángulo que representa la posición futura
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            // Verificar si hay colisión con el límite inferior del personaje
            if (LIMITE_ABAJO.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private boolean enColisionIzquierda(int velocidadX) {
        // Iterar sobre las áreas de colisión del mapa
        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionActualizadas.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColisionActualizadas.get(r);

            // Calcular la posición futura en el eje X
            int origenX = area.x + velocidadX * (int) this.getVelocidadCaminar() + 3 * (int) this.getVelocidadCaminar();
            int origenY = area.y;

            // Crear un rectángulo que representa la posición futura
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            // Verificar si hay colisión con el límite izquierdo del personaje
            if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private boolean enColisionDerecha(int velocidadX) {
        // Iterar sobre las áreas de colisión del mapa
        for (int r = 0; r < ElementosPrincipales.mapa.areasColisionActualizadas.size(); r++) {
            final Rectangle area = ElementosPrincipales.mapa.areasColisionActualizadas.get(r);

            // Calcular la posición futura en el eje X
            int origenX = area.x + velocidadX * (int) this.getVelocidadCaminar() - 3 * (int) this.getVelocidadCaminar();
            int origenY = area.y;

            // Crear un rectángulo que representa la posición futura
            final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

            // Verificar si hay colisión con el límite derecho del personaje
            if (LIMITE_DERECHA.intersects(areaFutura)) {
                return true;
            }
        }
        return false;
    }

    private int evaluarVelocidadX() {
        int velocidadX = 0;

        // Si se presiona la tecla de movimiento hacia la izquierda y no hacia la derecha
        if (GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
            velocidadX = -1; // Se establece la velocidad en -1 para mover hacia la izquierda
        }
        // Si se presiona la tecla de movimiento hacia la derecha y no hacia la izquierda
        else if (!GestorControles.teclado.izquierda.estaPulsada() && GestorControles.teclado.derecha.estaPulsada()) {
            velocidadX = 1; // Se establece la velocidad en 1 para mover hacia la derecha
        }
        return velocidadX;
    }

    private int evaluarVelocidadY() {
        int velocidadY = 0;

        // Si se presiona la tecla de movimiento hacia arriba y no hacia abajo
        if (GestorControles.teclado.arriba.estaPulsada() && !GestorControles.teclado.abajo.estaPulsada()) {
            velocidadY = -1; // Se establece la velocidad en -1 para mover hacia arriba
        }
        // Si se presiona la tecla de movimiento hacia abajo y no hacia arriba
        else if (!GestorControles.teclado.arriba.estaPulsada() && GestorControles.teclado.abajo.estaPulsada()) {
            velocidadY = 1; // Se establece la velocidad en 1 para mover hacia abajo
        }
        return velocidadY;
    }

    public Rectangle getArea() {
        final int puntoX =  this.getPosicionXInt();

        final int puntoY =  this.getPosicionYInt();

        return new Rectangle(puntoX + 8, puntoY + 8, Constantes.LADO_SPRITE / 2 + 8, Constantes.LADO_SPRITE / 2 + 8);
    }


    public double getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    public int getPosicionXInt() {
        return (int) posicionX;
    }

    public int getPosicionYInt() {
        return (int) posicionY;
    }

    public double getVelocidadCaminar() {
        return velocidadCaminar;
    }

    public void setVelocidadCaminar(double velocidadCaminar) {
        this.velocidadCaminar = velocidadCaminar;
    }

    public double getVelocidadCorrer() {
        return velocidadCorrer;
    }

    public boolean isEnMovimiento() {
        return enMovimiento;
    }

    public void setEnMovimiento(boolean enMovimiento) {
        this.enMovimiento = enMovimiento;
    }

    public boolean isAtacando() {
        return atacando;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public boolean isSobrepeso() {
        return sobrepeso;
    }

    public void setSobrepeso(boolean sobrepeso) {
        this.sobrepeso = sobrepeso;
    }

    public boolean isPreparado() {
        return preparado;
    }

    public void setPreparado(boolean preparado) {
        this.preparado = preparado;
    }

    public int getAnimacion() {
        return animacion;
    }

    public void setAnimacion(int animacion) {
        this.animacion = animacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public boolean isSubirNivel() {
        return subirNivel;
    }

    public void setSubirNivel(boolean subirNivel) {
        this.subirNivel = subirNivel;
    }

    public int getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(int recuperacion) {
        this.recuperacion = recuperacion;
    }

    public int getRecuperacionVida() {
        return recuperacionVida;
    }

    public void setRecuperacionVida(int recuperacionVida) {
        this.recuperacionVida = recuperacionVida;
    }

    public boolean isRecuperado() {
        return recuperado;
    }

    public void setRecuperado(boolean recuperado) {
        this.recuperado = recuperado;
    }

    public int getANCHO_JUGADOR() {
        return ANCHO_JUGADOR;
    }

    public int getALTO_JUGADOR() {
        return ALTO_JUGADOR;
    }

    public Rectangle getLIMITE_ARRIBA() {
        return LIMITE_ARRIBA;
    }

    public Rectangle getLIMITE_ABAJO() {
        return LIMITE_ABAJO;
    }

    public Rectangle getLIMITE_IZQUIERDA() {
        return LIMITE_IZQUIERDA;
    }

    public Rectangle getLIMITE_DERECHA() {
        return LIMITE_DERECHA;
    }

    public boolean isUsandoSkill() {
        return usandoSkill;
    }

    public void setUsandoSkill(boolean usandoSkill) {
        this.usandoSkill = usandoSkill;
    }
}
