package models.usuario.Contrasenia;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;

public class ValidadorDeContrasenia {
  private List<PuedeValidar> validadores;

  public ValidadorDeContrasenia() {
    this.validadores = new ArrayList<>();
  }

  public ValidadorDeContrasenia(List<PuedeValidar> validadores) {
    this.validadores = validadores;
  }

  public boolean validarContrasenia(String contrasenia) {
    return validadores.stream().allMatch(validador -> validador.validar(contrasenia));
  }

public void agregarValidador(PuedeValidar ... validador) {
  Collections.addAll(validadores, validador);
  }
}
