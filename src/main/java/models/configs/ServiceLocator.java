package models.configs;

import models.comunidades.Rol;
import models.usuario.Contrasenia.DiezMilPeoresContrasenias;
import models.usuario.Contrasenia.ValidadorDeContrasenia;
import lombok.Getter;
import lombok.Setter;
import models.usuario.Contrasenia.ValidarLongitud;


public class ServiceLocator {
  @Getter
  public static final Rol ROL_BASE = new Rol("Miembro", null);
  @Getter
  @Setter
  public static ValidadorDeContrasenia validador = new ValidadorDeContrasenia();

  public static ValidadorDeContrasenia getValidadorCompleto() {
    ValidadorDeContrasenia validadorDeContrasenia = new ValidadorDeContrasenia();
    validadorDeContrasenia.agregarValidador(
        new ValidarLongitud(),
        new DiezMilPeoresContrasenias()
    );
    return validadorDeContrasenia;
  }
}

