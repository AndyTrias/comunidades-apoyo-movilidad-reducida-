package comunidades;

import comunidades.servicios.PrestacionDeServicio;
import comunidades.usuario.Usuario;
import configs.Config;
import configs.ServiceLocator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.flogger.Flogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Comunidad {
    @Getter private List<Rol> roles;
    @Getter @Setter private String nombre;
    @Getter private Set<PrestacionDeServicio> serviciosDeInteres;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.serviciosDeInteres = new HashSet<>();
        this.roles = new ArrayList<>();
        roles.add(ServiceLocator.ROL_BASE);
    }
    
    public void agregarServicioDeInteres(PrestacionDeServicio servicio) {
        serviciosDeInteres.add(servicio);
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
