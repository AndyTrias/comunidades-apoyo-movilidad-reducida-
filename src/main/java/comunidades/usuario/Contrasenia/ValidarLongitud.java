package comunidades.usuario.Contrasenia;

public class ValidarLongitud extends PuedeValidar {

    private static final int MINIMO = 8;
    private static final int MAXIMO = 64;

    public ValidarLongitud(ValidadorDeContrasenia elValidador) {
        super(elValidador);
    }

    public boolean validar(String contrasenia) {
        return contrasenia.length() >= ValidarLongitud.MINIMO && contrasenia.length() <= ValidarLongitud.MAXIMO;
    }
}
