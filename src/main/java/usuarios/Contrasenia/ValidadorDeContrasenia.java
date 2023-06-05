package usuarios.Contrasenia;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import java.util.ArrayList;

public class ValidadorDeContrasenia {
  private final ArrayList<PuedeValidar> disponibles;
  private final ArrayList<PuedeValidar> activos;

  public ValidadorDeContrasenia() {
    this.disponibles = new ArrayList<>();
    this.activos = new ArrayList<>();
  }

  public boolean validarContrasenia(String contrasenia) {
    return activos.stream().allMatch(validador -> validador.validar(contrasenia));
  }
  public void activarValidador(PuedeValidar ... validador) {
    Collections.addAll(activos, validador);
  }

  public void desactivarValidador(PuedeValidar validador) {
    activos.remove(validador);
  }

  public void agregarADisponible(PuedeValidar validador) { //el validador lo llama cuando esta listo
    disponibles.add(validador);
  }
}
