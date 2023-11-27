package models.configs;

import models.usuario.Rol;
import models.usuario.Contrasenia.DiezMilPeoresContrasenias;
import models.usuario.Contrasenia.ValidadorDeContrasenia;
import lombok.Getter;
import lombok.Setter;
import models.usuario.Contrasenia.ValidarLongitud;

import java.util.List;


public class ServiceLocator {
  @Getter
  public static final Rol ROL_BASE = new Rol("Miembro", null);
  @Getter
  @Setter
  public static ValidadorDeContrasenia validador = new ValidadorDeContrasenia(List.of(
      new ValidarLongitud(),
      new DiezMilPeoresContrasenias()
  ));
}

