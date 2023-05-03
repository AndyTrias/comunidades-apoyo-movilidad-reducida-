package comunidades.roles;

import comunidades.Comunidad;
import comunidades.Rol;
import usuarios.Usuario;

public class Administrador extends Rol {
    public Administrador(Usuario usuario, Comunidad comunidad) {
        super(usuario, comunidad);
    }
}
