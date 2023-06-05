package comunidades;

import comunidades.servicios.PrestacionDeServicio;
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
        roles.add(new Rol("Administrador", new HashSet<>()));
    }

    public Rol aceptarUsuario(Usuario usuario) {
        Rol rol = roles.get(0);
        rol.setUsuario(usuario);
        return rol;
    }

    public void cambiarRol(Usuario usuario, Rol rol) {
        roles.removeIf(r -> r.getUsuarios().equals(usuario));
        roles.getUsuario().remove(usuario);
        rol.setUsuario(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        roles.removeIf(r -> r.getUsuarios().equals(usuario));
    }

}
