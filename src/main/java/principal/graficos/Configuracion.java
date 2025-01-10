package principal.graficos;

import javax.swing.*;
import java.awt.*;

public class Configuracion extends JFrame {


    private void configuracionVentana(final SuperficieDibujo sd) {
        // Configurar título, cierre, tamaño, icono, diseño y ubicación de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(sd, BorderLayout.CENTER);
        setUndecorated(true); // Sin barra de título
        pack(); // Ajustar tamaño automáticamente
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true); // Hacer visible la ventana
    }
}
