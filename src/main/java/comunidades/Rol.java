package comunidades;

import comunidades.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Rol {
    @Getter @Setter private String nombre;
    @Getter private List<Usuario> usuarios;
    @Getter private Set<Permiso> permisos;

    public Rol(String nombre, Set<Permiso> permisos) {
        this.permisos = permisos;
        this.usuarios = new ArrayList<>();
    }

    public void setUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public boolean tenesPermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }

    public void eliminarUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
    }

}

