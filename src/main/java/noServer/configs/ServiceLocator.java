package noServer.configs;

import noServer.comunidades.Rol;
import noServer.usuario.Contrasenia.ValidadorDeContrasenia;
import lombok.Getter;
import lombok.Setter;


public class ServiceLocator {
  @Getter public static final Rol ROL_BASE = new Rol("Miembro", null);
  @Getter @Setter public static ValidadorDeContrasenia validador = new ValidadorDeContrasenia();
}

