package usuarios;

import lombok.Getter;
import lombok.Setter;
import usuarios.Contrasenia.ValidadorDeContrasenia;

@Getter @Setter
public class Usuario {

  private String nombreUsuario;
  private String contrasena;

  public Usuario(String nombreUsuario, String contrasenia) throws Exception {
    this.nombreUsuario = nombreUsuario;

    ValidadorDeContrasenia validador = new ValidadorDeContrasenia();
    if (!validador.validarContrasenia(contrasenia)) {
      throw new Exception("Contrasenia inválida");
    }

    this.contrasena = contrasenia;
  }
  public static void main(String[]args) throws Exception {
    Usuario usuario = new Usuario("usuario", "contrasenia");
  }
}