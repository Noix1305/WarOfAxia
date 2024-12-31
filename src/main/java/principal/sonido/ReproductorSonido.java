package principal.sonido;

public class ReproductorSonido {
    public SoundThread musica;
    public SoundThread sonidoCaminar1;
    public SoundThread sonidoCaminar2;

    public ReproductorSonido() {
        musica = new SoundThread("FF-I-OST-The-prelude");
        sonidoCaminar1 = new SoundThread("Step_Grass");
        sonidoCaminar2 = new SoundThread("Step_Grass_2");

    }
}
