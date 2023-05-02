import usuarios.Contrasenia.DiezMilPeoresContrasenias;
import usuarios.Contrasenia.ValidadorDeContrasenia;
import usuarios.Contrasenia.ValidarLongitud;

public class Main {
    public static void main(String[]args) throws Exception {
        ValidadorDeContrasenia validadorDeContrasenia = new ValidadorDeContrasenia();

        ValidarLongitud validarLongitud = new ValidarLongitud(validadorDeContrasenia);
        DiezMilPeoresContrasenias diezMilPeoresContrasenias = new DiezMilPeoresContrasenias(validadorDeContrasenia);
        validadorDeContrasenia.activarValidador(validarLongitud, diezMilPeoresContrasenias);

        System.out.println(validadorDeContrasenia.validarContrasenia("abrakadabra"));
    }
}
