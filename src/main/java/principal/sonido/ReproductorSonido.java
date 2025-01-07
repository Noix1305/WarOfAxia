package principal.sonido;

public class ReproductorSonido {
    public SoundThread musica;
    public SoundThread sonidoCaminar1;
    public SoundThread sonidoCaminar2;
    public SoundThread sonidoHeal;
    public SoundThread lamentoEnemigo;
    public SoundThread sonidoArma;

    public ReproductorSonido() {
        musica = new SoundThread("Final-Fantasy-Main-Theme-_Orchestral_");
        sonidoCaminar1 = new SoundThread("Step_Grass");
        sonidoCaminar2 = new SoundThread("Step_Grass_2");
        sonidoHeal = new SoundThread("Heal");
        lamentoEnemigo = new SoundThread("Zombie");
        sonidoArma = new SoundThread("sword_clash.1");
    }
}
