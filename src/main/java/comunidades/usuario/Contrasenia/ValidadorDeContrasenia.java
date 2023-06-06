package comunidades.usuario.Contrasenia;
import java.util.Collections;

import java.util.ArrayList;

public class ValidadorDeContrasenia {
  private final ArrayList<PuedeValidar2> disponibles;
  private final ArrayList<PuedeValidar2> activos;

  public ValidadorDeContrasenia() {
    this.disponibles = new ArrayList<>();
    this.activos = new ArrayList<>();
  }

  public boolean validarContrasenia(String contrasenia) {
    return activos.stream().allMatch(validador -> validador.validar(contrasenia));
  }

  public void activarValidador(PuedeValidar2 ... validador) {
    Collections.addAll(activos, validador);
  }

  public void desactivarValidador(PuedeValidar2 validador) {
    activos.remove(validador);
  }

  public void agregarADisponible(PuedeValidar2 validador) { //el validador lo llama cuando esta listo
    disponibles.add(validador);
  }
}
