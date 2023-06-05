package comunidades;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Rol {
    @Getter @Setter
    private List<Usuario> usuarios;
    private Set<Permiso> permisos;
    @Getter @Setter
    private String nombre;

    public Rol(String nombre, Set<Permiso> permisos) {
        this.permisos = permisos;
        this.usuarios = new ArrayList<>(); {
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public boolean tenesPermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }
}

