package comunidades.usuario;

public class Email {
    public String nombreDeUsuario;
    public String dominio;

  public Email() {
  }

  public String getEmail() {
        return nombreDeUsuario + "@" + dominio;
    }
}
