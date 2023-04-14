package usuarios;

public class Usuario {


  private String nombreUsuario;
  private String contrasena;

  public Usuario(String nombreUsuario, String contrasena) {
    this.nombreUsuario = nombreUsuario;
    this.contrasena = contrasena;

    // Revisar esto
    ValidadorDeContraseña validador = new ValidadorDeContraseña();
    if (!validador.validarContraseña(contrasena)) {
      throw new RuntimeException("Contraseña no válida");
    }
    this.contrasena = contrasena;
  }
  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }
}
