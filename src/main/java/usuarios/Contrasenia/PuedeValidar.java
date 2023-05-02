package usuarios.Contrasenia;

public abstract class PuedeValidar {
    // public boolean validar(String contrasenia);
    public static ValidadorDeContrasenia unValidador;
    public PuedeValidar(ValidadorDeContrasenia elValidador) {

        this.unValidador = elValidador;
        this.unValidador.inicializarse(this);
    }

}

