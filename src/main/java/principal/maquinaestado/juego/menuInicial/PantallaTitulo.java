/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.maquinaestado.juego.menuInicial;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.GestorPrincipal;
import principal.control.GestorControles;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DibujoDebug;
import principal.maquinaestado.EstadoJuego;
import principal.maquinaestado.juego.GestorJuego;
import principal.maquinaestado.menujuego.EstructuraMenu;
import principal.maquinaestado.menujuego.MenuInventario;
import principal.maquinaestado.menujuego.SeccionMenu;
import principal.sonido.ReproductorSonido;
import principal.sprites.HojaSprites;

/**
 * @author GAMER ARRAX
 */
public class PantallaTitulo implements EstadoJuego {

    private static boolean esperaEnter = true;

    private final BufferedImage inicio;
    private final BufferedImage papiro;
    public static boolean musicaIniciada = false;
    private long tiempoUltimaPulsacion = 0;
    long tiempoInicio = System.currentTimeMillis();
    private final long INTERVALO_CAMBIO = 9000;
    private String[] fragmentosTexto;

    public PantallaTitulo() {
        fragmentosTexto = new String[]{
                "Hace mucho tiempo, en el lejano y mítico mundo de Axia, las naciones coexistían en armonía, compartiendo los vastos recursos que la tierra les ofrecía. ",
                "Las montañas de Gravellor, los bosques de Althera, y los desiertos de Vornar eran ricas en minerales, magia ancestral y criaturas míticas que les daban poder. ",
                "Las naciones vivían en paz, intercambiando conocimientos y bienes, unidas por un pacto que había durado siglos. Pero ese tiempo de paz fue efímero. ",
                "El mundo de Axia se veía marcado por tres grandes imperios...",
                "La Alianza de Lúmina, cuyo reino se extendía en el luminoso archipiélago de Solaryon, una tierra bañada por la luz eterna del sol. ",
                "El Imperio de Ferrogar, gobernado desde la imponente ciudad de Forzeth, situada en lo profundo de las montañas de Gravellor. ",
                "Y, por último, la Confederación de Nyrelth, una nación que habitaba los misteriosos bosques de Althera. ",
                "Sin embargo, todo cambió cuando se descubrió la existencia de una fuente mágica oculta: El Cristal de Eirath, un artefacto legendario que otorgaba poder absoluto...",
                "LA TRAVESíA COMIENZA..."
                // Aquí puedes agregar más fragmentos según lo necesites
        };
        ElementosPrincipales.reproductor.musica.repetir(0.8f);
        HojaSprites hojaInicio = new HojaSprites("/fondos/Inicio4.png", 640, 360, true);
        HojaSprites hojaPapiro = new HojaSprites("/fondos/papiro.png", 183, 257, true);
        this.inicio = hojaInicio.getSprites(0).imagen;
        this.papiro = hojaPapiro.getSprites(0).imagen;
    }

//    private void cambiarImagen() {
//        // Determinar qué imagen mostrar basándose en la imagen actual
//        if (start == s1.getSprites(0).imagen()) {
//            start = s2.getSprites(0).imagen();
//        }
//        else {
//            start = s1.getSprites(0).imagen();
//        }
//    }

    public void actualizar() {
        if (GestorPrincipal.pantallaTitulo && !musicaIniciada) {
            String musicaInicio = "Final-Fantasy-Main-Theme-_Orchestral_";
            if (!ReproductorSonido.musica.getFilename().toUpperCase().equalsIgnoreCase(musicaInicio)) {
                ReproductorSonido.musica.cambiarArchivo(musicaInicio);
                ReproductorSonido.musica.repetir(0.8f);
                musicaIniciada = true;
            }
        }
        long tiempoActual = System.currentTimeMillis(); // Tiempo actual en milisegundos

        if (GestorPrincipal.pantallaTitulo && tiempoActual - tiempoUltimaPulsacion > 300) {
            if (GestorControles.teclado.enter.puedeProcesarse()) {
                GestorControles.teclado.enter.marcarComoProcesada();
                GestorControles.teclado.enter.teclaLiberada();
                System.out.println("Enter pulsada en pantalla título");
                tiempoUltimaPulsacion = tiempoActual;
                GestorPrincipal.menuInicio = true;
                GestorPrincipal.pantallaTitulo = false;
                // Actualiza el tiempo
            }
        }
    }

    public void dibujar(Graphics2D g) {
        DibujoDebug.dibujarImagen(g, this.inicio, 0, 0);
        dibujarPrologo(g);
    }

    public void dibujarPrologo(Graphics g) {
        // Definir la fuente y establecerla
        g.setFont(new Font("Arial", Font.ITALIC, 10));
        boolean textoFinalizado = false;

        // Área del texto (puedes ajustarla según el tamaño)
        Rectangle rec = new Rectangle(135, 60, 133, 210);

        // Redibujar el fondo antes de cada fragmento de texto (manteniendo la imagen intacta)
        DibujoDebug.dibujarImagen(g, papiro, 100, 20);

        // Determinar el tiempo transcurrido
        long tiempoActual = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoActual - tiempoInicio;

        // Calcular el índice del fragmento a mostrar, basado en el tiempo transcurrido y el intervalo de escritura
        int fragmentoActual = (int) (tiempoTranscurrido / INTERVALO_CAMBIO);

        // Asegurarnos de que no se sobrepasen los límites del array de fragmentos
        if (fragmentoActual >= fragmentosTexto.length) {
            fragmentoActual = fragmentosTexto.length - 1; // Limitar al último fragmento
        }

        int velocidadEscritura;

        // Para cada fragmento, vamos a escribirlo letra por letra
        for (int i = 0; i <= fragmentoActual; i++) {
            if (i == 8) {
                velocidadEscritura = 300;
            } else {
                velocidadEscritura = 50;
            }
            // Obtener el fragmento de texto actual
            String textoCompleto = fragmentosTexto[i];

            // Calcular el tiempo de inicio para cada fragmento (cuando comienza a escribirse)
            long tiempoInicioFragmento = tiempoInicio + i * INTERVALO_CAMBIO;

            // Dibujar el fragmento de texto desde el inicio hasta el índice actualizado
            boolean textoTerminado = DibujoDebug.escribirTextoLetraPorLetra(
                    g, textoCompleto, rec.x, rec.y , rec.width, velocidadEscritura, tiempoInicioFragmento
            );

            if (textoTerminado && i != 8) {
                // Calcular el tiempo que falta para el próximo fragmento (o el fin del intervalo)
                long tiempoSiguienteFragmento = tiempoInicioFragmento + INTERVALO_CAMBIO - System.currentTimeMillis();
                if (tiempoSiguienteFragmento > 0) {
                    try {
                        Thread.sleep(tiempoSiguienteFragmento); // Esperar el tiempo restante
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Dibujar el segundo papiro
                DibujoDebug.dibujarImagen(g, papiro, 100, 20);
            }
        }

        // Si ya se mostró todo el texto, puedes añadir lógica para reiniciar o mostrar algo diferente.
        if (fragmentoActual >= fragmentosTexto.length - 1) {

        }
    }


}
