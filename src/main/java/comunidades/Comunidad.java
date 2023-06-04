package comunidades;

import comunidades.servicios.PrestacionDeServicio;

import java.util.Set;

public class Comunidad {
    private Set<Rol> roles;
    private String nombre;
    private Set<PrestacionDeServicio> serviciosDeInteres;

    public void aceptarUsuario(Usuario usuario) {
        Rol rol = this.asignarRol(usuario);
        rolBase.setUsuario(usuario);
    }

    public Rol asignarRol(Usuario usuario) {
        // EN base a los distintos filtros que dijeron (???
        // le devuelve uno de sus roles
    }
}
