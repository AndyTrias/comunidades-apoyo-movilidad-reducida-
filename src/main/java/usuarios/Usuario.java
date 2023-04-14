package usuarios;

import usuarios.Contraseña.ValidadorDeContraseña;

public class Usuario {


  private String nombreUsuario;
  private String contrasena;

  public Usuario(String nombreUsuario, String contrasena) {
    this.nombreUsuario = nombreUsuario;
    this.contrasena = contrasena;

    // Revisar esto
    new ValidadorDeContraseña().validarContraseña(contrasena);
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
