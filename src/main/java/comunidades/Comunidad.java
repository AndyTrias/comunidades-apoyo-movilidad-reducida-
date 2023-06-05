package comunidades;

import comunidades.servicios.PrestacionDeServicio;
import comunidades.usuario.Usuario;
import configs.Config;

import java.util.HashSet;
import java.util.Set;

public class Comunidad {
    private String nombre;
    private Set<Rol> roles;
    private Set<PrestacionDeServicio> serviciosDeInteres;

    public Comunidad(String nombre, Set<PrestacionDeServicio> serviciosDeInteres) {
        this.nombre = nombre;
        this.serviciosDeInteres = serviciosDeInteres;
        this.roles = new HashSet<>();
        roles.add(Config.ROL_BASE);
    }

    public Rol aceptarUsuario(Usuario usuario) {
        Rol rol = roles.stream().filter(r -> r.equals(Config.ROL_BASE)).findFirst().get();
        rol.agregarUsuario(usuario);
        return rol;
    }

    public void eliminarUsuario(Usuario usuario) {
        roles.stream().filter(r -> r.getUsuarios().equals(usuario)).findFirst().get().eliminarUsuario(usuario);
    }


}
