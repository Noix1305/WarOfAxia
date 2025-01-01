package principal.entes;

import principal.inventario.TipoObjeto;

import java.io.Serializable;

public abstract class Entidad implements Serializable {
    public GestorAtributos gestorAtributos;

    public Entidad (GestorAtributos gestorAtributos){
        this.gestorAtributos = gestorAtributos;
    }

    public void recibirDanho(int danho, TipoObjeto tipoDeHabilidad) {
        // Verificar el tipo de habilidad que inflige el daño
        if (tipoDeHabilidad == TipoObjeto.FISICO) {
            // Calcular el daño recibido considerando la resistencia física y la defensa física del personaje
            danho = (int) (danho * (100 - gestorAtributos.getResFisica()) / 100.0 - gestorAtributos.getDefensaFisica());
        } else if (tipoDeHabilidad == TipoObjeto.MAGIA) {
            // Calcular el daño recibido considerando la resistencia mágica y la defensa mágica del personaje
            danho = (int) (danho * (100 - gestorAtributos.getResMagica()) / 100.0 - gestorAtributos.getDefensaMagica());
        }
        // Actualizar la vida actual del personaje restando el daño recibido
        this.gestorAtributos.setVida(this.gestorAtributos.getVidaActual()- danho);
    }

    protected abstract void curarVida(int montoCuracion); {

    }


}
