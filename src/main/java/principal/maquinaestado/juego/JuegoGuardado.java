package principal.maquinaestado.juego;

import java.io.*;

public class JuegoGuardado {

    public static void guardarEstadoJuego(EstadoJuegoGuardar estadoJuego, String directorio, String prefijoArchivo) {
        String nombreArchivo = directorio + "/" + prefijoArchivo + "_" + System.currentTimeMillis() + ".save";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(estadoJuego); // Guardar el objeto EstadoJuego
            System.out.println("Juego guardado correctamente en: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el juego: " + e.getMessage());
        }
    }

    public static EstadoJuegoGuardar cargarEstadoJuego(String nombreArchivo) {
        EstadoJuegoGuardar estadoJuego = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            estadoJuego = (EstadoJuegoGuardar) ois.readObject();  // Cargar el objeto EstadoJuego
            System.out.println("Juego cargado correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el juego: " + e.getMessage());
        }
        return estadoJuego;
    }
}
