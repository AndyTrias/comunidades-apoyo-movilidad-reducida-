package configs;

import comunidades.Rol;
import comunidades.usuario.Contrasenia.ValidadorDeContrasenia;
import lombok.Getter;
import lombok.Setter;


public class ServiceLocator {
  @Getter public static final Rol ROL_BASE = new Rol("Miembro", null);
  @Getter @Setter public static ValidadorDeContrasenia validador = new ValidadorDeContrasenia();
}

