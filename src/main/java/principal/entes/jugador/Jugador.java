/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.entes.jugador;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.entes.Entidad;
import principal.entes.GestorAtributos;
import principal.entes.enemigo.Enemigo;
import principal.habilidades.Curacion;
import principal.habilidades.Habilidad;
import principal.herramientas.Cronometro;
import principal.herramientas.DibujoDebug;
import principal.inventario.Objeto;
import principal.inventario.TipoObjeto;
import principal.inventario.armaduras.Armadura;
import principal.inventario.armas.Arma;
import principal.inventario.armas.ArmaDosManos;
import principal.inventario.armas.SinArma;
import principal.inventario.joyas.Joya;
import principal.sprites.Sprite;

/**
 * @author GAMER ARRAX
 */
public class Jugador extends Entidad {

    public Curacion curacion;
    private Habilidad habilidadActual;
    private final AccionesJugador accionesJugador;
    private AnimacionJugador animacionJugador;
    private final AlmacenEquipo almacenEquipo;
    private final AccesoRapido accesoRapido;
    private final Cronometro cronometro = new Cronometro();

    // Área de colisión del jugador
    public Rectangle areaPosicional;

    private ArrayList<Rectangle> alcanceActual;

    // Constructor del jugador
    public Jugador(GestorAtributos gestorAtributos) {
        super(gestorAtributos);
        this.accionesJugador = new AccionesJugador();
        this.animacionJugador = new AnimacionJugador(this.accionesJugador);
        this.almacenEquipo = new AlmacenEquipo();
        this.accesoRapido = new AccesoRapido();
        this.alcanceActual = new ArrayList<>();
        this.areaPosicional = new Rectangle(Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2,
                Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2, 32, 32);

        // Actualización inicial de atributos
        actualizarAtributos();
        super.gestorAtributos.setVida(super.gestorAtributos.getVidaMaxima());
        super.gestorAtributos.setMana(super.gestorAtributos.getManaMaximo());
        super.gestorAtributos.setResistencia(super.gestorAtributos.getResistenciaMaxima());
        this.habilidadActual = null;
    }

    public Jugador(GestorAtributos gestorAtributos, AlmacenEquipo almacenEquipo, AccesoRapido accesoRapido, AccionesJugador accionesJugador, Rectangle areaPosicional) {
        super(gestorAtributos);
        this.almacenEquipo = almacenEquipo;
        this.accesoRapido = accesoRapido;
        this.areaPosicional = areaPosicional;
        this.accionesJugador = accionesJugador;
        this.alcanceActual = new ArrayList<>();
    }

    // Método para aumentar la experiencia del jugador
    public void ganarExperiencia(int puntos) {
        super.gestorAtributos.setExperiencia(+puntos);
    }

    // Método para subir de nivel al jugador
    public void subirNivel() {
        if (super.gestorAtributos.getExperiencia() >= super.gestorAtributos.getExperienciaMaxima()) {
            super.gestorAtributos.setNivel(super.gestorAtributos.getNivel() + 1);
            super.gestorAtributos.setVida(super.gestorAtributos.getVidaMaxima());
            super.gestorAtributos.setMana(super.gestorAtributos.getManaMaximo());
            super.gestorAtributos.setExperiencia(super.gestorAtributos.getExperiencia() -
                    super.gestorAtributos.getExperienciaMaxima());
            super.gestorAtributos.setExperienciaMaxima((int) (super.gestorAtributos.getExperienciaMaxima() * 1.1));
            super.gestorAtributos.setPuntosAtributos(super.gestorAtributos.getPuntosAtributos() + 3);
            this.accionesJugador.setSubirNivel(true);
        }
    }

    // Método para actualizar los atributos del jugador
    private void actualizarAtributos() {
        super.gestorAtributos.setVidaMaxima(120 + super.gestorAtributos.getNivel() + (int) (super.gestorAtributos.getConstitucion() * 1.15));
        super.gestorAtributos.setManaMaximo((int) (50 + (double) super.gestorAtributos.getNivel() / 2 + (super.gestorAtributos.getInteligencia() * 1.15)));
        super.gestorAtributos.setAtaque(super.gestorAtributos.getFuerza() + super.gestorAtributos.getNivel() + calcularAtqFisico());
        super.gestorAtributos.setDefensaFisica((int) (super.gestorAtributos.getVidaMaxima() * 0.01 + super.gestorAtributos.getConstitucion() * 1.1) + calcularDefensaF());
        super.gestorAtributos.setMagia(super.gestorAtributos.getInteligencia() + super.gestorAtributos.getNivel() + calcularAtqMagico());
        super.gestorAtributos.setDefensaMagica((int) (super.gestorAtributos.getManaMaximo() * 0.01 + super.gestorAtributos.getInteligencia() * 1.1) + calcularDefensaM());
        super.gestorAtributos.setEvasion(super.gestorAtributos.getDestreza() * 1.2 - super.gestorAtributos.getPesoActual() * 0.3);
        setVelocidad(super.gestorAtributos.getDestreza() * 0.2 - super.gestorAtributos.getPesoActual() * 0.30);
        super.gestorAtributos.setCritico(super.gestorAtributos.getSuerte() * 0.5);
        super.gestorAtributos.setResistenciaMaxima(600 + (int) (super.gestorAtributos.getConstitucion() * 0.1 + super.gestorAtributos.getDestreza() * 0.1));
        super.gestorAtributos.setLimitePeso(super.gestorAtributos.getFuerza() * 2 + super.gestorAtributos.getConstitucion() * 2);
    }

    // Método para dibujar el efecto de subir de nivel del jugador

    // Implementa la lógica para reiniciar la experiencia, si es necesario
    private void gestionarVelocidadResistencia() {
        if (GestorControles.teclado.corriendo && super.gestorAtributos.getResistencia() > 0) {
            accionesJugador.setVelocidadCaminar(accionesJugador.getVelocidadCorrer());
            accionesJugador.setRecuperado(false);
            accionesJugador.setRecuperacion(0);

        } else if (accionesJugador.isSobrepeso()) {
            accionesJugador.setVelocidadCaminar(0.5);
        } else {
            accionesJugador.setVelocidadCaminar(1);
            if (!accionesJugador.isRecuperado() && accionesJugador.getRecuperacion() < 120) {
                accionesJugador.setRecuperacion(accionesJugador.getRecuperacion() + 1);

            }

            if (accionesJugador.getRecuperacion() == 120 && super.gestorAtributos.getResistencia() <
                    super.gestorAtributos.getResistenciaMaxima()) {
                super.gestorAtributos.setResistencia(super.gestorAtributos.getResistencia() + 3);
                if (super.gestorAtributos.getResistencia() > super.gestorAtributos.getResistenciaMaxima()) {
                    super.gestorAtributos.setResistencia(super.gestorAtributos.getResistenciaMaxima());
                }
            }
        }
    }

    // Método para actualizar las defensas del jugador
    public void actualizarDefensas() {
        int def = (int) (super.gestorAtributos.getVidaMaxima() * 0.01 + super.gestorAtributos.getConstitucion() * 1.1) + calcularDefensaF();
        int defM = (int) (super.gestorAtributos.getManaMaximo() * 0.01 + super.gestorAtributos.getInteligencia() * 1.1) + calcularDefensaM();
        double eva = (super.gestorAtributos.getDestreza() * 1.2 - super.gestorAtributos.getPesoActual() * 0.3) + calcularEvasion();

        super.gestorAtributos.setDefensaFisica(def);
        super.gestorAtributos.setDefensaMagica(defM);
        super.gestorAtributos.setEvasion(eva);
        super.gestorAtributos.setResFisica(calcularResF());
        super.gestorAtributos.setResMagica(calcularResM());
    }

    // Método para actualizar el ataque del jugador
    public void actualizarAtaque() {
        double crit = (super.gestorAtributos.getSuerte() * 0.5) + calcularCrit();

        super.gestorAtributos.setAtaque(super.gestorAtributos.getFuerza() + super.gestorAtributos.getNivel() + calcularAtqFisico());
        super.gestorAtributos.setMagia(super.gestorAtributos.getInteligencia() + super.gestorAtributos.getNivel() + calcularAtqMagico());
        super.gestorAtributos.setCritico(crit);

    }

    // Método principal de actualización del jugador
    public void actualizar() {

        accionesJugador.setEnMovimiento(false);
        gestionarVelocidadResistencia();
        this.accionesJugador.determinarDireccion(super.gestorAtributos, animacionJugador);
        actualizarAnimacion();
        //transparentar();
        actualizarArmas();
        subirNivel();
        morir();
        isDead();
        actualizarDefensas();
        calcularPesoActual();
        actualizarAtaque();
        cambiarHojaSprites();
    }


    public void dibujar(Graphics g) {
        final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
        final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;

        if (!this.accionesJugador.isPreparado()) {
            DibujoDebug.dibujarImagen(g, this.animacionJugador.getImagenActual(), centroX, centroY);
        }
        this.animacionJugador.dibujarVestimenta(g, centroX, centroY, this.accionesJugador);

        /*DibujoDebug.dibujarRectanguloContorno(g, LIMITE_ARRIBA);
        DibujoDebug.dibujarRectanguloContorno(g, LIMITE_ABAJO);
        DibujoDebug.dibujarRectanguloContorno(g, LIMITE_IZQUIERDA);
        DibujoDebug.dibujarRectanguloContorno(g, LIMITE_DERECHA);*/
//        DibujoDebug.dibujarRectanguloContorno(g, this.areaPosicional, Color.BLUE);
//        if (!this.alcanceActual.isEmpty()) {
//            dibujarAlcance(g);
//        }

        animacionJugador.dibujarDanhoRecibido(g, centroX, centroY + 20);

        this.animacionJugador.dibujarCuracionRecibida(g, centroX, centroY + 20);

        this.animacionJugador.dibujarSubirNivel(g, centroX, centroY, this.accionesJugador);

        DibujoDebug.dibujarString(g, "AtkF: " + super.gestorAtributos.getAtaque(), 10, 90);
        DibujoDebug.dibujarString(g, "Def: " + super.gestorAtributos.getDefensaFisica(), 10, 100);
        DibujoDebug.dibujarString(g, "AtkM: " + super.gestorAtributos.getMagia(), 10, 110);
        DibujoDebug.dibujarString(g, "DefM: " + super.gestorAtributos.getDefensaMagica(), 10, 120);
        String evasionTexto = String.format("%.1f %%", super.gestorAtributos.getEvasion());
        DibujoDebug.dibujarString(g, "Eva: " + evasionTexto, 10, 130);
        String critTexto = String.format("%.1f %%", super.gestorAtributos.getCritico());
        DibujoDebug.dibujarString(g, "Crit: " + critTexto, 10, 140);

    }

    public void habilidadSlot(int indice) {
        Objeto objeto = null;
        try {
            try {
                this.habilidadActual = (Habilidad) getAccesoRapido().getAccesoEquipado(indice);
                System.out.println("HABILIDAD: " + habilidadActual.getNombre());
                this.accionesJugador.setUsandoSkill(true);
                System.out.println("Usando Habilidad: " + this.accionesJugador.isUsandoSkill());
            } catch (ClassCastException c) {
                objeto = (Objeto) getAccesoRapido().getAccesoEquipado(indice);
            }
        } catch (NullPointerException n) {
            System.out.println("No hay Objeto u Habilidad en indice " + indice);
        }
    }

    public Enemigo obtenerPrimerEnemigoEnAlcance(ArrayList<Rectangle> alcance, List<Enemigo> listaEnemigos) {
        for (Enemigo enemigo : listaEnemigos) {
            Rectangle posicionEnemigo = new Rectangle(
                    (int) enemigo.getPosicionX(),
                    (int) enemigo.getPosicionY(),
                    Constantes.LADO_SPRITE,
                    Constantes.LADO_SPRITE
            );

            for (Rectangle area : alcance) {
                if (area.intersects(posicionEnemigo)) {
                    return enemigo;
                }
            }
        }
        return null; // No se encontró ningún enemigo dentro del alcance
    }


    private void actualizarArmas() {
        calcularAlcanceAtaque();
        if (this.almacenEquipo.getArma1() != null) {
            this.almacenEquipo.getArma1().actualizar();
        }

        if (this.almacenEquipo.getArma2() != null) {
            this.almacenEquipo.getArma2().actualizar();
        }

    }

    public boolean enCombate() {
        Arma arma1 = getAlmacenEquipo().getArma1();
        Arma arma2 = getAlmacenEquipo().getArma2();

        if (arma1 != null || arma2 != null) {
            accionesJugador.setPreparado(true);
        }
        return accionesJugador.isPreparado();
    }

    private void dibujarAlcance(Graphics g) {
        DibujoDebug.dibujarRectanguloRelleno(g, alcanceActual.get(0), Color.red);
    }

    private void calcularAlcanceAtaque() {
        if (almacenEquipo.getArma1() != null) {
            alcanceActual = almacenEquipo.getArma1().getAlcance(this);
        } else if (almacenEquipo.getArma1() == null && almacenEquipo.getArma2() != null && !(almacenEquipo.getArma2() instanceof SinArma)) {
            alcanceActual = almacenEquipo.getArma2().getAlcance(this);
        }
    }

    public void cambiarHojaSprites() {
        if (getAlmacenEquipo().getArma1() != null) {
            this.animacionJugador.setHojaPersonaje(getAlmacenEquipo().getArma1().getHojaArma());
        }
    }

    @Override
    public void curarVida(int montoCuracion) {
        // Registra el tiempo en que se mostró la curación
        this.animacionJugador.setTiempoInicioMostrarCuracion(System.currentTimeMillis());

        // Verifica si la vida actual es menor que la vida máxima
        if (super.gestorAtributos.getVida() < super.gestorAtributos.getVidaMaxima()) {
            // Incrementa la vida actual con el monto de curación
            super.gestorAtributos.setVida(super.gestorAtributos.getVida() + montoCuracion);
            // Asegura que la vida no supere el máximo
            if (super.gestorAtributos.getVida() > super.gestorAtributos.getVidaMaxima()) {
                super.gestorAtributos.setVida(super.gestorAtributos.getVidaMaxima());
            }
            // Registra el monto de vida recuperado y muestra la curación
            this.animacionJugador.setMontoRecuperado(montoCuracion);
            this.animacionJugador.setMostrarCuracion(true);
        }
    }

    public void perderVida(int ataqueEnemigo) {
        // Genera un número aleatorio para determinar si el jugador evade el ataque
        double numeroAleatorio = new Random().nextDouble() * 100 + 1;
        int danho = 0;

        // Verifica si el jugador evade el ataque
        this.animacionJugador.setMostrarEvadido(numeroAleatorio <= super.gestorAtributos.getEvasion());
        if (this.animacionJugador.isMostrarEvadido()) {
            // Registra el tiempo en que se mostró la evasión
            this.animacionJugador.setTiempoInicioMostrarDanho(System.currentTimeMillis());
        } else {
            // Calcula el daño recibido por el jugador
            this.animacionJugador.setTiempoInicioMostrarDanho(System.currentTimeMillis());
            danho = (int) ((ataqueEnemigo - (ataqueEnemigo * super.gestorAtributos.getResFisica() / 100.0)) - super.gestorAtributos.getDefensaFisica());
            danho = Math.max(danho, 0); // Asegura que el daño no sea negativo

            // Reduce la vida del jugador según el daño recibido
            super.gestorAtributos.setVida(super.gestorAtributos.getVida() - danho);
            this.animacionJugador.setDanhoPorGolpe(danho);
        }

        // Verifica si el ataque fue bloqueado
        this.animacionJugador.setMostrarBloqueado(danho <= 0);
        if (this.animacionJugador.isMostrarBloqueado()) {
            // Registra el tiempo en que se mostró el bloqueo
            this.animacionJugador.setTiempoInicioMostrarDanho(System.currentTimeMillis());
        }

        // Registra el daño recibido y muestra la información de daño
        this.animacionJugador.setMostrarDanho(true);
        this.animacionJugador.setDanhoRecibido(danho);
    }

    private void actualizarAnimacion() {
        // Lógica para determinar la animación del jugador
        if (!accionesJugador.isEnMovimiento()) {
            accionesJugador.setAnimacion(1);
        } else {
            // Ajusta la velocidad de la animación aquí
            int velocidadAnimacion = 1; // Ajusta este valor según sea necesario

            // Incrementa la animación en cada ciclo
            accionesJugador.setAnimacion(accionesJugador.getAnimacion() + velocidadAnimacion);

            // Ajusta la animación para que esté dentro del rango adecuado
            accionesJugador.setAnimacion(accionesJugador.getAnimacion() % 60);

            // Determina el estado de la animación basado en la animación actual
            if (accionesJugador.getAnimacion() <= 60 && accionesJugador.getAnimacion() > 50) {
                accionesJugador.setEstado(0); // Estado normal
            } else if (accionesJugador.getAnimacion() <= 50 && accionesJugador.getAnimacion() > 40) {
                accionesJugador.setEstado(1); // Estado normal
            } else if (accionesJugador.getAnimacion() <= 40 && accionesJugador.getAnimacion() > 30) {
                accionesJugador.setEstado(2); // Estado normal
            } else if (accionesJugador.getAnimacion() <= 30 && accionesJugador.getAnimacion() > 20) {
                accionesJugador.setEstado(0);
            } else if (accionesJugador.getAnimacion() <= 20 && accionesJugador.getAnimacion() > 10) {
                accionesJugador.setEstado(1);
            } else {
                accionesJugador.setEstado(2);
            }

            // Obtiene el sprite correspondiente basado en el estado y la dirección
            Sprite sprite = this.animacionJugador.getHojaPersonaje().getSprites(accionesJugador.getEstado(),
                    this.animacionJugador.getDireccion()); // Sprite normal

            // Actualiza la imagen actual del jugador
            if (sprite != null) {
                this.animacionJugador.setImagenActual(sprite.imagen());
            } else {
                // Manejo de caso en el que sprite es null
                // Puedes asignar una imagen por defecto, lanzar una excepción, etc.
                // En este ejemplo, asignaremos una imagen nula
                this.animacionJugador.setImagenActual(null);
            }
        }
    }


    /*private void cambiarAnimacionEstado() {
        if (animacion < 60) {
            animacion++;
        }
        else {
            animacion = 0;
        }

        if (animacion <= 45 && animacion >= 30) {
            estado = 1; // Estado normal
        }
        else if (animacion < 30 && animacion > 15) {
            estado = 2; // Estado normal
        }
        else if (animacion <= 15) {
            estado = 1; // Estado normal
        }
        else {
            estado = 0; // Estado normal
        }

    }*/

//    private void transparentar() {
//        // Iterar sobre las áreas de transparencia del mapa
//        for (Rectangle rectangulo : ElementosPrincipales.mapa.areasTransparenciaActualizadas) {
//            // Verificar si alguna de las áreas de transparencia intersecta con los límites del personaje
//            if (LIMITE_ARRIBA.intersects(rectangulo) || LIMITE_ABAJO.intersects(rectangulo)
//                    || LIMITE_IZQUIERDA.intersects(rectangulo) || LIMITE_DERECHA.intersects(rectangulo)) {
//                // Sí hay intersección, establecer la imagen actual del personaje
//                this.animacionJugador.setImagenActual(this.animacionJugador.getHojaTransparencia().
//                        getSprites(accionesJugador.getEstado(), this.animacionJugador.getDireccion()).getImagen());
//            }
//        }
//    }

    public void morir() {
        // Verificar si la vida actual es menor o igual a 0
        // Establecer que el personaje está vivo
        animacionJugador.setEstaVivo(gestorAtributos.getVida() > 0); // Establecer que el personaje ha muerto
    }

    public void isDead() {
        // Verificar si la vida actual es menor o igual a 0
        if (gestorAtributos.getVida() <= 0) {
            animacionJugador.setEstaVivo(false); // Establecer que el personaje ha muerto
            super.gestorAtributos.setVida(0); // Establecer la vida del personaje en 0
        }
    }

    public void calcularPesoActual() {
        double peso = 0;
        // Iterar sobre los objetos del inventario
        for (Objeto objeto : ElementosPrincipales.inventario.getListaObjetos()) {
            if (objeto != null) {
                peso += objeto.getPeso() * objeto.getCantidad(); // Calcular el peso total del inventario
            }
        }
        super.gestorAtributos.setPesoActual(peso); // Establecer el peso actual del personaje
        accionesJugador.setSobrepeso(super.gestorAtributos.getPesoActual() >= super.gestorAtributos.getLimitePeso());  // Verificar si el personaje está en sobrepeso
    }

    public int calcularAtqFisico() {
        int ataqueTotal = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Arma arma1 = ae.getArma1(); // Obtener el arma principal equipada por el personaje

        if (arma1 != null) {
            ataqueTotal = arma1.getAtaque(); // Obtener el valor de ataque del arma principal
        }

        Objeto[] objetosEquipados = {ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                Joya joya = (Joya) objeto;
                ataqueTotal += joya.getAtkF(); // Sumar el valor de ataque físico de las joyas equipadas
            }
        }

        return ataqueTotal; // Devolver el ataque total del personaje
    }

    public int calcularAtqMagico() {
        int ataqueTotal = 0;
        int atqMagico = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Arma arma1 = ae.getArma1(); // Obtener el arma principal equipada por el personaje
        Arma arma2 = ae.getArma2(); // Obtener el arma secundaria equipada por el personaje

        // Verificar si el personaje tiene dos armas equipadas que no sean de dos manos
        if (arma1 != null && arma2 != null && !(arma1 instanceof ArmaDosManos) && !(arma2 instanceof ArmaDosManos)) {
            ataqueTotal = arma1.getAtaque() + arma2.getAtaque(); // Sumar el ataque de ambas armas
        }
        // Si solo tiene un arma equipada y es de dos manos
        else if (arma1 instanceof ArmaDosManos) {
            ataqueTotal = arma1.getAtaque(); // Obtener el ataque del arma de dos manos
        }
        // Si solo tiene un arma secundaria equipada y es de dos manos
        else if (arma2 instanceof ArmaDosManos) {
            ataqueTotal = arma2.getAtaque(); // Obtener el ataque del arma de dos manos
        }

        Objeto[] objetosEquipados = {ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                Joya joya = (Joya) objeto;
                ataqueTotal += joya.getAtkF(); // Sumar el valor de ataque físico de las joyas equipadas
                atqMagico += joya.getAtkM(); // Sumar el valor de ataque mágico de las joyas equipadas
            }
        }

        return atqMagico + (int) (ataqueTotal / 3); // Devolver el ataque mágico total del personaje
    }

    public double calcularEvasion() {
        double eva = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Objeto[] objetosEquipados = {ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                Joya joya = (Joya) objeto;
                if (joya.getEva() > 0) {
                    eva += joya.getEva(); // Sumar la evasión proporcionada por las joyas equipadas
                }
            }
        }
        return eva; // Devolver la evasión total del personaje
    }

    public double calcularResM() {
        double resM = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Objeto[] objetosEquipados = {ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                Joya joya = (Joya) objeto;
                if (joya.getResM() > 0) {
                    resM += joya.getResM(); // Sumar la resistencia mágica proporcionada por las joyas equipadas
                }
            }
        }
        return resM; // Devolver la resistencia mágica total del personaje
    }

    public double calcularResF() {
        double resF = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Objeto[] objetosEquipados = {ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                Joya joya = (Joya) objeto;
                if (joya.getResF() > 0) {
                    resF += joya.getResF(); // Sumar la resistencia física proporcionada por las joyas equipadas
                }
            }
        }
        return resF; // Devolver la resistencia física total del personaje
    }

    public double calcularCrit() {
        double crit = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Objeto[] objetosEquipados = {ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                Joya joya = (Joya) objeto;
                if (joya.getCrit() > 0) {
                    crit += joya.getCrit(); // Sumar la probabilidad de crítico proporcionada por las joyas equipadas
                }
            }
        }
        return crit; // Devolver la probabilidad de crítico total del personaje
    }

    public int calcularDefensaF() {
        int defensaF = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Objeto[] objetosEquipados = {ae.getArmaduraMedia(), ae.getCasco(), ae.getGuante(), ae.getBota(),
                ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                if (objeto instanceof Joya) {
                    defensaF += ((Joya) objeto).getDefensaF(); // Sumar la defensa física proporcionada por las joyas equipadas
                } else if (objeto instanceof Armadura) {
                    defensaF += ((Armadura) objeto).getDefensaF(); // Sumar la defensa física proporcionada por las armaduras equipadas
                }
            }
        }

        return defensaF; // Devolver la defensa física total del personaje
    }

    public int calcularDefensaM() {
        int defensaM = 0;
        AlmacenEquipo ae = getAlmacenEquipo(); // Obtener el almacenamiento de equipo del personaje

        Objeto[] objetosEquipados = {ae.getArmaduraMedia(), ae.getCasco(), ae.getGuante(), ae.getBota(),
                ae.getCollar(), ae.getAccesorio(), ae.getAnillo1(), ae.getAnillo2()};

        // Iterar sobre los objetos equipados por el personaje
        for (Objeto objeto : objetosEquipados) {
            if (objeto != null) {
                if (objeto instanceof Joya) {
                    defensaM += ((Joya) objeto).getDefensaM(); // Sumar la defensa mágica proporcionada por las joyas equipadas
                } else if (objeto instanceof Armadura) {
                    defensaM += ((Armadura) objeto).getDefensaM(); // Sumar la defensa mágica proporcionada por las armaduras equipadas
                }
            }
        }

        return defensaM; // Devolver la defensa mágica total del personaje
    }

    public void setVelocidad(double velocidad) {
        accionesJugador.setVelocidadCaminar(velocidad);
        if (this.accionesJugador.getVelocidadCaminar() <= 0) {
            accionesJugador.setVelocidadCaminar(0.1);
        }
    }

    public AlmacenEquipo getAlmacenEquipo() {
        return almacenEquipo;
    }

    public TipoObjeto getTipoObjetoFromEquipoActual(Objeto objeto) {
        for (Objeto equipo : getAlmacenEquipo().getEquipoActual()) {
            if (equipo.equals(objeto)) {
                return equipo.getTipoObjeto();
            }
        }
        return TipoObjeto.NINGUNO; // Ajusta según tus necesidades
    }

    public ArrayList<Rectangle> getAlcanceActual() {
        return alcanceActual;
    }

    public void setAlcanceActual(ArrayList<Rectangle> alcanceActual) {
        this.alcanceActual = alcanceActual;
    }

    public AccesoRapido getAccesoRapido() {
        return accesoRapido;
    }

    public GestorAtributos getGestorAt() {
        return super.gestorAtributos;
    }

    public Cronometro getCronometro() {
        return cronometro;
    }

    public AccionesJugador getAccionesJugador() {
        return accionesJugador;
    }

    public AnimacionJugador getAnimacionJugador() {
        return animacionJugador;
    }

    public Habilidad getHabilidadActual() {
        return habilidadActual;
    }

    public void setHabilidadActual(Habilidad habilidadActual) {
        this.habilidadActual = habilidadActual;
    }

    public Rectangle getAreaPosicional() {
        return areaPosicional;
    }

    public void setAreaPosicional(Rectangle areaPosicional) {
        this.areaPosicional = areaPosicional;
    }

    public void setAnimacionJugador(AnimacionJugador animacionJugador) {
        this.animacionJugador = animacionJugador;
    }
}
