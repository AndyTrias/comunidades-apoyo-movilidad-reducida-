package usuarios.Contrasenia;
import java.util.List;
import java.util.stream.Stream;

public class ValidadorDeContrasenia {

  private static final List<StringABool> condiciones = Stream.<StringABool>of(
          (String contrasenia) -> !DiezMilPeoresContrasenias.estaEnLaLista(contrasenia),
          (String contrasenia) -> contrasenia.length() >= 8,
          (String contrasenia) -> contrasenia.length() < 64
  ).toList();



  public static boolean validarContrasenia(String contrasenia){

    return condiciones.stream().allMatch(condicion -> condicion.apply(contrasenia));
  }
}
