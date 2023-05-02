package usuarios.Contrasenia;

public class ValidarLongitud extends PuedeValidar {

    private int MINIMO = 8;
    private int MAXIMO = 64;

    public ValidarLongitud(ValidadorDeContrasenia elValidador) {
        super(elValidador);
    }

    public boolean validar(String contrasenia) {
        return contrasenia.length() >= ValidarLongitud.MINIMO && contrasenia.length() <= ValidarLongitud.MAXIMO;
    }
}
