package usuarios.Contrasenia;
import java.util.List;
import java.util.stream.Stream;

import java.util.ArrayList;

public class ValidadorDeContrasenia {
  private ArrayList<PuedeValidar> disponibles;
  private ArrayList<PuedeValidar> activos;

  public ValidadorDeContrasenia() {
    this.disponibles = new ArrayList<>(); //los que existen
    this.activos = new ArrayList<>();
  }

  public boolean validarContrasenia(String contrasenia) {
    return activos.stream()
            .allMatch(validador -> validador.validar(contrasenia));
  }

  /*
  public List<String> mostrarActivos() { //implementar despues si es necesario

  }
*/
  public void agregarValidador(PuedeValidar validador) {
    activos.add(validador);
  }

  public void sacarValidador(PuedeValidar validador) {
    activos.remove(validador);
  }

  public void inicializarse(PuedeValidar validador) { //el validador lo llama cuando esta listo
    disponibles.add(validador);
  }
}
