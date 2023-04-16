package usuarios.Contrasenia;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface StringABool { //para la lista de funciones
  boolean apply(String x);
}

public class ValidadorDeContrasenia {

  private static List<StringABool> condiciones = Stream.<StringABool>of(
          (String contrasenia) -> DiezMilPeoresContrasenias.estaEnLaLista(contrasenia),
          (String contrasenia) -> contrasenia.length() >= 8,
          (String contrasenia) -> contrasenia.length() < 64
  ).collect(Collectors.toList());



  public static boolean validarContrasenia(String contrasenia){

    return condiciones.stream().allMatch(condicion -> condicion.apply(contrasenia));
  }
}
