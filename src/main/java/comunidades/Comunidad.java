package comunidades;

import incidentes.Incidente;
import servicios.PrestacionDeServicio;
import comunidades.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;
import notificaciones.notificador.CierreIncidente;
import notificaciones.notificador.Notificador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class Comunidad {
    @Getter private List<Rol> roles;
    @Getter @Setter private String nombre;
    @Getter private Set<PrestacionDeServicio> serviciosDeInteres;
    @Getter private List<Incidente> incidentesAbiertos;
    @Getter private List<Incidente> incidentesCerrados;
    @Setter private Notificador notificador;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.serviciosDeInteres = new HashSet<>();
        this.roles = new ArrayList<>();
        this.incidentesAbiertos = new ArrayList<>();
        this.incidentesCerrados = new ArrayList<>();
        this.roles.add(new Rol("Miembro", null));
        this.notificador = new CierreIncidente();
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

    public List<Usuario> getUsuarios(){
        List<Usuario> usuarios = new ArrayList<>();
        roles.forEach(r -> usuarios.addAll(r.getUsuarios()));
        return usuarios;
    }

    public void abrirIncidente(Incidente incidente) {

        // Habria que ver si ese incidente ya esta abierto
        for (Incidente i : incidentesAbiertos) {
            if (i.getPrestacionDeServicio().equals(incidente.getPrestacionDeServicio())) {
                return;
            }
        }

        // Unicamente se agrega si lo tiene como interes
        if (serviciosDeInteres.contains(incidente.getPrestacionDeServicio())) {
            incidentesAbiertos.add(incidente);
        }
    }

    public void cerrarIncidente(Incidente incidente, Usuario usuario)  {

        if (this.estaCerradoElIncidente(incidente))
        {
            throw new RuntimeException("El incidente ya esta cerrado");
        }

        incidente.cerrar();
        incidentesAbiertos.remove(incidente);
        incidentesCerrados.add(incidente);
        notificador.notificar(usuario, incidente);
    }

    public List<Incidente> getTodosLosIncidentes(){
        List<Incidente> incidentes = new ArrayList<>();
        incidentes.addAll(incidentesAbiertos);
        incidentes.addAll(incidentesCerrados);
        return incidentes;
    }

    public int getCantidadDeAfectados() {
        return getUsuarios().stream().filter(u -> u.getMembresia(this).esAfectado()).mapToInt(u -> 1).sum();
    }

    public boolean estaCerradoElIncidente(Incidente incidente)  {
        if (!getTodosLosIncidentes().contains(incidente)) {
            throw new RuntimeException("El incidente no pertenece a la comunidad");
        }

        return this.incidentesCerrados.contains(incidente);
    }
}

