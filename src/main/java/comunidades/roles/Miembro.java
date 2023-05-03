package comunidades.roles;

import comunidades.Comunidad;
import comunidades.Rol;
import usuarios.Usuario;

public class Miembro extends Rol {
    public Miembro(Usuario usuario, Comunidad comunidad) {
        super(usuario, comunidad);
    }
}
