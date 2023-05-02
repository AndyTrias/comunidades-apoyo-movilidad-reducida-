package usuarios;

import lombok.Getter;
import usuarios.Contrasenia.ValidadorDeContrasenia;

@Getter
public class Usuario {

  private String nombreUsuario;

  private Rol rol;

  private String contrasena;

  public Usuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public void setContrasenia(String contrasenia) throws Exception {

    ValidadorDeContrasenia validadorDeContrasenia = new ValidadorDeContrasenia();
    if (!validadorDeContrasenia.validarContrasenia(contrasenia)) {
      throw new Exception("La contraseña no es valida");
    }

    this.contrasena = contrasenia;
  }

}
