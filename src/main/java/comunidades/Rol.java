package comunidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Rol {
    private List<Usuario> usuarios;
    private Set<Permiso> permisos;

    public Rol(Set<Permiso> permisos) {
        this.permisos = permisos;
        this.usuarios = new ArrayList<>(); {
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void TenesPermiso() {
        // Implementaci�n
    }
}
