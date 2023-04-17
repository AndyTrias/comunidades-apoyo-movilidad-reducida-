package usuarios;

import lombok.Getter;
import lombok.Setter;
import usuarios.Contrasenia.ValidadorDeContrasenia;

public class Usuario {

  @Getter @Setter
  private String nombreUsuario;

  @Getter
  private String contrasena;

  public Usuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public void setContrasenia(String contrasenia) throws Exception {

    //ValidadorDeContrasenia validador = new ValidadorDeContrasenia();
    if (!ValidadorDeContrasenia.validarContrasenia(contrasenia)) {
      throw new Exception("Contrasenia inválida");
    }

    this.contrasena = contrasenia;
  }

}