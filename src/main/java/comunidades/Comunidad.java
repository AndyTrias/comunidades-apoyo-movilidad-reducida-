package comunidades;

import comunidades.servicios.PrestacionDeServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Comunidad {
    private List<Rol> roles;
    private String nombre;
    private Set<PrestacionDeServicio> serviciosDeInteres;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.roles = new ArrayList<>();
        roles.add(new Rol("rolBASE", null));
    }

    public void aceptarUsuario(Usuario usuario) {
        Rol rol = this.asignarRol(usuario);
        rol.setUsuario(usuario);
    }

    public Rol asignarRol(Usuario usuario) {
        // En base a los distintos filtros le devuelve uno de sus roles
        // Le asigna un rol 0 que no tiene permisos
        return roles.get(0);
    }

    // Recibe un nuevo rol y lo agrega a la lista de roles
    public void cambiarRol(Usuario usuario, Rol rol) {
        roles.removeIf(r -> r.getUsuarios().equals(usuario));
        rol.setUsuario(usuario);
    }

}
