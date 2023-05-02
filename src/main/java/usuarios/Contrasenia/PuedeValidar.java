package usuarios.Contrasenia;

public abstract class PuedeValidar {
    // public boolean validar(String contrasenia);
    private final ValidadorDeContrasenia validador;
    public PuedeValidar(ValidadorDeContrasenia elValidador) {
        this.validador = elValidador;
        this.validador.inicializarse(this);
    }

    public boolean validar(String contrasenia) {
        return this.validador.validarContrasenia(contrasenia);
    }

}

