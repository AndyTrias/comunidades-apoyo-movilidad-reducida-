package comunidades;

import comunidades.servicios.PrestacionDeServicio;
import comunidades.usuario.Usuario;
import configs.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Comunidad {
    private List<Rol> roles;
    private String nombre;
    private Set<PrestacionDeServicio> serviciosDeInteres;

    public Comunidad(String nombre, Set<PrestacionDeServicio> serviciosDeInteres) {
        this.nombre = nombre;
        this.serviciosDeInteres = serviciosDeInteres;
        this.roles = new ArrayList<>();
        roles.add(Config.ROL_BASE);
    }

    public Rol aceptarUsuario(Usuario usuario) {
        Rol rol = roles.get(0);
        rol.setUsuario(usuario);
        return rol;
    }

    public void cambiarRol(Usuario usuario, Rol rol) {
        eliminarUsuarioDeRol(usuario, rol);
        rol.setUsuario(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        eliminarUsuarioDeRol(usuario, roles.stream().filter(r -> r.getUsuarios().contains(usuario)).findFirst().get());
    }

    private void eliminarUsuarioDeRol(Usuario usuario, Rol rol) {
        roles.stream().filter(r -> r.getUsuarios().contains(usuario)).forEach(r -> r.eliminarUsuario(usuario));
    }

}
