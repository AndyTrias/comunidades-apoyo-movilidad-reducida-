package comunidades;

import comunidades.incidentes.Incidente;
import comunidades.incidentes.IncidenteDeComunidad;
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
import java.util.concurrent.ExecutionException;

public class Comunidad {
    @Getter private List<Rol> roles;
    @Getter @Setter private String nombre;
    @Getter private Set<PrestacionDeServicio> serviciosDeInteres;
    @Getter private List<IncidenteDeComunidad> incidentes;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.serviciosDeInteres = new HashSet<>();
        this.roles = new ArrayList<>();
        this.incidentes = new ArrayList<>();
        roles.add(ServiceLocator.ROL_BASE);
    }
    
    public void agregarServicioDeInteres(PrestacionDeServicio servicio) {
        serviciosDeInteres.add(servicio);
    }
    
    public void agregarRol(Rol rol) {
        roles.add(rol);
    }

    public void eliminarRol(Rol rol) {
        roles.remove(rol);
    }

    public Rol aceptarUsuario(Usuario usuario) {
        Rol rol = roles.get(0);
        rol.setUsuario(usuario);
        return rol;
    }

    public void cambiarRol(Usuario usuario, Rol rol) throws Exception {
        exiteRol(rol);
        eliminarUsuarioDeSuRol(usuario);
        rol.setUsuario(usuario);
    }

    public void eliminarUsuario(Usuario usuario) {
        eliminarUsuarioDeSuRol(usuario);
    }

    private void eliminarUsuarioDeSuRol(Usuario usuario) {
        roles.stream().filter(r -> r.getUsuarios().contains(usuario)).forEach(r -> r.eliminarUsuario(usuario));
    }

    private boolean exiteRol(Rol rol) throws Exception {
        if (!roles.contains(rol)) {
            throw new Exception("El rol no existe en la comunidad");
        }
        return true;
    }

    public Integer getCantidadDeUsuarios(){
        return roles.stream().mapToInt(r -> r.getUsuarios().size()).sum();
    }

    public void nuevoIncidenteEn(Incidente incidente, Membresia miembro, String observaciones) {
        IncidenteDeComunidad incidenteDeComunidad = new IncidenteDeComunidad(incidente, miembro, observaciones);
        incidentes.add(incidenteDeComunidad);
    }
}
