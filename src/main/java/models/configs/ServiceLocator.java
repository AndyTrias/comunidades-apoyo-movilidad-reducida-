package models.configs;

import models.comunidades.Rol;
import models.usuario.Contrasenia.ValidadorDeContrasenia;
import lombok.Getter;
import lombok.Setter;


public class ServiceLocator {
  @Getter public static final Rol ROL_BASE = new Rol("Miembro", null);
  @Getter @Setter public static ValidadorDeContrasenia validador = new ValidadorDeContrasenia();
}

