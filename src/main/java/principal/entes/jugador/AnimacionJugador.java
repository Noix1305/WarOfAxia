package principal.entes.jugador;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.Cronometro;
import principal.herramientas.DibujoDebug;
import principal.sprites.HojaSprites;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimacionJugador {
    public boolean estaVivo = true;
    // Estado de animación y dirección del jugador
    private int estadoAnimacion;
    private int direccion;
    public boolean dibujarHabilidad = false;
    private boolean mostrarBloqueado = false;
    private boolean mostrarEvadido = false;
    private boolean mostrarDanho = false;
    public boolean mostrarCuracion = false;
    private int danhoPorGolpe;
    private int montoRecuperado;
    private float danhoRecibido;
    private long tiempoInicioMostrarDanho;
    private long tiempoInicioMostrarCuracion;
    protected static final long DURACION_MOSTRAR_DANHO = 500; // Duración en milisegundos
    protected static final float VELOCIDAD_SUBIDA_DANHO = 0.09f;
    private HojaSprites hojaPersonaje;
    private final HojaSprites hojaTransparencia;
    private BufferedImage habilidad;
    private final HojaSprites hojaCuracion;
    private BufferedImage imagenActual;

    public AnimacionJugador(AccionesJugador accionesJugador) {
        this.setDireccion(0);
        hojaPersonaje = new HojaSprites(Constantes.RUTA_PERSONAJE, 32, 32, false);
        hojaTransparencia = new HojaSprites(Constantes.RUTA_PERSONAJE_TRANSPARENTE, Constantes.LADO_SPRITE, false);
        imagenActual = hojaPersonaje.getSprites(accionesJugador.getEstado(), this.getDireccion()).getImagen();
        hojaCuracion = new HojaSprites(Constantes.RUTA_CURACION, 32, 32, false);
        habilidad = CargadorRecursos.cargarImagenCompatibleTranslucida("/icons/Habilidad1.png");

    }


    public void dibujarSubirNivel(Graphics g, int puntoX, int puntoY, AccionesJugador accionesJugador) {
        if (accionesJugador.isSubirNivel()) {

            // Calcula la opacidad en función del tiempo transcurrido
            long tiempoTranscurrido = System.currentTimeMillis() - this.getTiempoInicioMostrarDanho();
            float opacidad = 1.0f - (float) tiempoTranscurrido / DURACION_MOSTRAR_DANHO;

            // Asegura que la opacidad esté en el rango [0, 1]
            opacidad = Math.max(0.0f, Math.min(1.0f, opacidad));

            // Calcula la posición Y en función de la velocidadCaminar de subida
            int posY = puntoY - (int) (VELOCIDAD_SUBIDA_DANHO * tiempoTranscurrido);

            // Configura el color con la opacidad
            Color colorBlanco = new Color(1.0f, 1.0f, 1.0f, opacidad);
            g.setColor(colorBlanco);

            // Dibuja el texto
            DibujoDebug.dibujarString(g, "LEVEL UP!", puntoX, posY);

            // Si ha pasado el tiempo de duración o el texto ha subido lo suficiente, deja de mostrar el daño
            if (tiempoTranscurrido >= DURACION_MOSTRAR_DANHO || posY <= puntoY - DURACION_MOSTRAR_DANHO * VELOCIDAD_SUBIDA_DANHO - 20) {
                accionesJugador.setSubirNivel(false);
            }
        }
    }

    public void dibujarVestimenta(Graphics g, int centroX, int centroY, AccionesJugador accionesJugador) {

        /*if (getAe().getCasco() != null) {
            ProteccionAlta armadura = (ProteccionAlta) getAe().getCasco();
            DibujoDebug.dibujarImagen(g, armadura.getHojaCasco().getSprites(estado, direccion).getImagen(), centroX, centroY);
        }
        else {
            HojaSprites hojaCabello = new HojaSprites(Constantes.RUTA_PERSONAJE_CABELLO, 32, false);
            DibujoDebug.dibujarImagen(g, hojaCabello.getSprites(estado, direccion).getImagen(), centroX, centroY);
        }*/
        Cronometro cronometro = new Cronometro();

        if (accionesJugador.isPreparado()) {

            int tiempoTranscurrido = (int) cronometro.getTiempoTranscurridoMili();

            // Verificar si han pasado menos de 1 segundo desde el inicio de la animación
            if (tiempoTranscurrido < 300) { // 1000 milisegundos = 1 segundo
                // Determinar qué imagen mostrar basándose en el índice
                if (tiempoTranscurrido <= 100) {
                    accionesJugador.setEstado(3);
                    DibujoDebug.dibujarImagen(g, hojaPersonaje.getSprites(accionesJugador.getEstado(), this.direccion)
                            .getImagen(), centroX, centroY);

                } else if (tiempoTranscurrido < 200) {
                    accionesJugador.setEstado(4);
                    DibujoDebug.dibujarImagen(g, hojaPersonaje.getSprites(accionesJugador.getEstado(), this.direccion).
                            getImagen(), centroX, centroY);
                } else {
                    accionesJugador.setEstado(5);
                    DibujoDebug.dibujarImagen(g, hojaPersonaje.getSprites(accionesJugador.getEstado(), this.direccion).
                            getImagen(), centroX, centroY);

                }

            } else {
                // Si han pasado 3 segundos o más, detener la animación
                accionesJugador.setPreparado(false);

            }

        }

    }

    public void dibujarHabilidad(Graphics g) {
        Cronometro cronometro = new Cronometro();
        // Obtiene el tiempo transcurrido desde el inicio de la animación
        int tiempoTranscurrido = (int) cronometro.getTiempoTranscurridoMili();

        // Verifica si han pasado menos de 0.5 segundos desde el inicio de la animación
        if (tiempoTranscurrido < 500) {
            // Obtiene el índice de la imagen actual basado en el tiempo transcurrido
            int indiceImagen = tiempoTranscurrido / 250;

            // Determina qué imagen mostrar según el índice
            HojaSprites hojaHabilidad;
            if (indiceImagen % 2 == 0) {
                hojaHabilidad = new HojaSprites("/icons/Habilidad1.png", 640, 360, false);
            } else {
                hojaHabilidad = new HojaSprites("/icons/Habilidad2.png", 640, 360, false);
            }

            // Obtiene la imagen correspondiente y la dibuja
            this.setHabilidad(hojaHabilidad.getSprites(0).getImagen());
            DibujoDebug.dibujarImagen(g, this.habilidad, 0, 0);
        } else {
            // Si han pasado más de 0.5 segundos, detiene la animación
            this.setDibujarHabilidad(false);
        }
    }

    public void dibujarDanhoRecibido(Graphics g, int puntoX, int puntoY) {
        long tiempoTranscurrido = System.currentTimeMillis() - this.getTiempoInicioMostrarDanho();

        if (this.getDanhoRecibido() > 0 || this.isMostrarEvadido() || this.isMostrarBloqueado()) {

            // Calcula la posición Y en función de la velocidadCaminar de subida
            int posY = puntoY - (int) (VELOCIDAD_SUBIDA_DANHO * tiempoTranscurrido);

            // Asegura que la posición Y no sea menor que el límite inferior
            posY = (int) Math.max(posY, puntoY - DURACION_MOSTRAR_DANHO * VELOCIDAD_SUBIDA_DANHO - 20);

            // Calcula la opacidad en función del tiempo transcurrido
            float opacidad = 1.0f - (float) tiempoTranscurrido / DURACION_MOSTRAR_DANHO;

            // Asegura que la opacidad esté en el rango [0, 1]
            opacidad = Math.max(0.0f, Math.min(1.0f, opacidad));

            if (this.isMostrarDanho()) {

                Color colorDanho = new Color(1.0f, 0.0f, 0.0f, opacidad);

                if (this.getDanhoPorGolpe() <= 0) {
                    colorDanho = new Color(0.0f, 1.0f, 0.0f, opacidad);
                }
                g.setColor(colorDanho);
                DibujoDebug.dibujarString(g, Float.toString(this.getDanhoPorGolpe()), puntoX, posY);
            }

            // Muestra el texto de "ESQUIVADO" si es necesario
            if (this.isMostrarEvadido()) {
                Color colorEvadido = new Color(0.0f, 1.0f, 0.0f, opacidad);  // Cambiado a verde
                g.setColor(colorEvadido);
                DibujoDebug.dibujarString(g, "ESQUIVADO!", puntoX, posY - 10);
            }

            // Muestra el texto de "BLOQUEADO" si es necesario
            if (this.isMostrarBloqueado() && !this.isMostrarEvadido()) {
                Color colorBloqueado = new Color(0.0f, 1.0f, 0.0f, opacidad);  // Cambiado a verde
                g.setColor(colorBloqueado);
                DibujoDebug.dibujarString(g, "BLOQUEADO!", puntoX, posY - 10);
            }

            // Si ha pasado el tiempo de duración o el texto ha subido lo suficiente, deja de mostrar la información
            if (tiempoTranscurrido >= DURACION_MOSTRAR_DANHO || posY <= puntoY - DURACION_MOSTRAR_DANHO * VELOCIDAD_SUBIDA_DANHO - 20) {
                this.setMostrarDanho(false);
                this.setMostrarEvadido(false);
                this.setMostrarBloqueado(false);
            }
        }
    }

    public void dibujarCuracionRecibida(Graphics g, int puntoX, int puntoY) {
        if (this.isMostrarCuracion()) {
            long tiempoTranscurrido = System.currentTimeMillis() - this.getTiempoInicioMostrarCuracion();

            long indiceImagen = tiempoTranscurrido / 250;

            // Calcula la posición Y en función de la velocidad de subida
            int posY = puntoY - (int) (VELOCIDAD_SUBIDA_DANHO * tiempoTranscurrido);

            // Asegura que la posición Y no sea menor que el límite inferior
            posY = (int) Math.max(posY, puntoY - DURACION_MOSTRAR_DANHO * VELOCIDAD_SUBIDA_DANHO - 20);

            // Calcula la opacidad en función del tiempo transcurrido
            float opacidad = 1.0f - (float) tiempoTranscurrido / DURACION_MOSTRAR_DANHO;

            // Asegura que la opacidad esté en el rango [0, 1]
            opacidad = Math.max(0.0f, Math.min(1.0f, opacidad));

            if (this.isMostrarCuracion()) {
                Color colorCuracion = new Color(0.0f, 1.0f, 0.0f, opacidad); // Cambiado a verde
                g.setColor(colorCuracion);
                DibujoDebug.dibujarString(g, Float.toString(this.getMontoRecuperado()), puntoX, posY);

                if (indiceImagen % 2 == 0) {
                    DibujoDebug.dibujarImagen(g, this.getHojaCuracion().getSprites(0, 0).getImagen(), puntoX, puntoY - 20);
                } else if (indiceImagen % 2 == 1) {
                    DibujoDebug.dibujarImagen(g, this.getHojaCuracion().getSprites(1, 0).getImagen(), puntoX, puntoY - 20);
                } else {
                    DibujoDebug.dibujarImagen(g, this.getHojaCuracion().getSprites(2, 0).getImagen(), puntoX, puntoY - 20);
                }
                // Si ha pasado el tiempo de duración o el texto ha subido lo suficiente, deja de mostrar la información
                if (tiempoTranscurrido >= DURACION_MOSTRAR_DANHO || posY <= puntoY - DURACION_MOSTRAR_DANHO * VELOCIDAD_SUBIDA_DANHO - 20) {
                    this.setMostrarCuracion(false);
                }
            }
        }
    }


    public boolean isEstaVivo() {
        return estaVivo;
    }

    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }

    public int getEstadoAnimacion() {
        return estadoAnimacion;
    }

    public void setEstadoAnimacion(int estadoAnimacion) {
        this.estadoAnimacion = estadoAnimacion;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    public boolean isDibujarHabilidad() {
        return dibujarHabilidad;
    }

    public void setDibujarHabilidad(boolean dibujarHabilidad) {
        this.dibujarHabilidad = dibujarHabilidad;
    }

    public boolean isMostrarBloqueado() {
        return mostrarBloqueado;
    }

    public void setMostrarBloqueado(boolean mostrarBloqueado) {
        this.mostrarBloqueado = mostrarBloqueado;
    }

    public boolean isMostrarEvadido() {
        return mostrarEvadido;
    }

    public void setMostrarEvadido(boolean mostrarEvadido) {
        this.mostrarEvadido = mostrarEvadido;
    }

    public boolean isMostrarDanho() {
        return mostrarDanho;
    }

    public void setMostrarDanho(boolean mostrarDanho) {
        this.mostrarDanho = mostrarDanho;
    }

    public boolean isMostrarCuracion() {
        return mostrarCuracion;
    }

    public void setMostrarCuracion(boolean mostrarCuracion) {
        this.mostrarCuracion = mostrarCuracion;
    }

    public int getDanhoPorGolpe() {
        return danhoPorGolpe;
    }

    public void setDanhoPorGolpe(int danhoPorGolpe) {
        this.danhoPorGolpe = danhoPorGolpe;
    }

    public int getMontoRecuperado() {
        return montoRecuperado;
    }

    public void setMontoRecuperado(int montoRecuperado) {
        this.montoRecuperado = montoRecuperado;
    }

    public float getDanhoRecibido() {
        return danhoRecibido;
    }

    public void setDanhoRecibido(float danhoRecibido) {
        this.danhoRecibido = danhoRecibido;
    }

    public long getTiempoInicioMostrarDanho() {
        return tiempoInicioMostrarDanho;
    }

    public void setTiempoInicioMostrarDanho(long tiempoInicioMostrarDanho) {
        this.tiempoInicioMostrarDanho = tiempoInicioMostrarDanho;
    }

    public long getTiempoInicioMostrarCuracion() {
        return tiempoInicioMostrarCuracion;
    }

    public void setTiempoInicioMostrarCuracion(long tiempoInicioMostrarCuracion) {
        this.tiempoInicioMostrarCuracion = tiempoInicioMostrarCuracion;
    }

    public HojaSprites getHojaPersonaje() {
        return hojaPersonaje;
    }

    public void setHojaPersonaje(HojaSprites hojaPersonaje) {
        this.hojaPersonaje = hojaPersonaje;
    }

    public HojaSprites getHojaTransparencia() {
        return hojaTransparencia;
    }

    public BufferedImage getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(BufferedImage habilidad) {
        this.habilidad = habilidad;
    }

    public HojaSprites getHojaCuracion() {
        return hojaCuracion;
    }

    public BufferedImage getImagenActual() {
        return imagenActual;
    }

    public void setImagenActual(BufferedImage imagenActual) {
        this.imagenActual = imagenActual;
    }
}
