package usuarios;

import comunidades.Comunidad;
import comunidades.roles.Miembro;
import comunidades.Rol;
import lombok.Getter;
import usuarios.Contrasenia.ValidadorDeContrasenia;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Usuario {

  private String nombre;
  private String apellido;
  private String correoElectronico;
  private String contrasena;
  private Set<Rol> comunidades;

  public Usuario(String nombre, String apellido, String correoElectronico) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.correoElectronico = correoElectronico;
    this.comunidades = new HashSet<>();
  }

  public void setContrasenia(String contrasenia) throws Exception {

    ValidadorDeContrasenia validadorDeContrasenia = new ValidadorDeContrasenia();
    if (!validadorDeContrasenia.validarContrasenia(contrasenia)) {
      throw new Exception("La contraseña no es valida");
    }

    this.contrasena = contrasenia;
  }

  public void unirseAComunidad(Comunidad comunidad) {
    Miembro miembro = new Miembro(this, comunidad);
    comunidades.add(miembro);
    comunidad.agregarMiembro(miembro);
  }
}
