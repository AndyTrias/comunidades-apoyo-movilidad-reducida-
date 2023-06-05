package comunidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Rol {
    @Getter private List<Usuario> usuarios;
    @Getter private Set<Permiso> permisos;

    public Rol(Set<Permiso> permisos) {
        this.permisos = permisos;
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
    }

    public void tenesPermiso() {
        // Implementaci�n
    }
}
