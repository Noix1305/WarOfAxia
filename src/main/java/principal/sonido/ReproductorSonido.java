package principal.sonido;

public class ReproductorSonido {
    public static SoundThread musica;
    public static SoundThread sonidoCaminar1;
    public static SoundThread sonidoCaminar2;
    public static SoundThread sonidoHeal;
    public static SoundThread lamentoEnemigo;
    public static SoundThread sonidoEspada;
    public static SoundThread chestSound;
    public static SoundThread sonidoArco;

    public ReproductorSonido() {
        musica = new SoundThread("Final-Fantasy-Main-Theme-_Orchestral_");
        sonidoCaminar1 = new SoundThread("Step_Grass");
        sonidoCaminar2 = new SoundThread("Step_Grass_2");
        sonidoHeal = new SoundThread("Heal");
        lamentoEnemigo = new SoundThread("Zombie");
        sonidoEspada = new SoundThread("sword_clash.1");
        chestSound = new SoundThread("chestSound");
        sonidoArco = new SoundThread("arrow");
    }
}
