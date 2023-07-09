package comunidades;

import comunidades.incidentes.Incidente;
import comunidades.servicios.PrestacionDeServicio;
import comunidades.usuario.Usuario;
import configs.ServiceLocator;
import lombok.Getter;
import lombok.Setter;
import notificaciones.FactoryNotificacion;
import notificaciones.Notificacion;
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
        this.serviciosDeInteres = new HashSet<>(PrestacionDeServicio);
        this.roles = new ArrayList<>();
        this.incidentesAbiertos = new ArrayList<>(Incidente);
        this.incidentesCerrados = new ArrayList<>(Incidente);
        this.roles.add(ServiceLocator.ROL_BASE);
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
        List<Usuario> miembros = new ArrayList<>(Usuario);
        roles.forEach(r -> miembros.addAll(r.getUsuarios()));
        return miembros;
    }

    public void abrirIncidente(Incidente incidente) {
        incidentesAbiertos.add(incidente);
    }

    public void cerrarIncidente(Incidente incidente) {
        incidente.cerrar();
        incidentesAbiertos.remove(incidente);
        incidentesCerrados.add(incidente);
        notificarAUsuarios("Cierre de incidente");
    }

    private void notificarAUsuarios(String tipoDeNotificacion) {
        List<Usuario> usuarios = getUsuarios();
        Notificacion notificacion = FactoryNotificacion.crearNotificacion(tipoDeNotificacion);
        usuarios.forEach(usuario -> {
            notificacion.setDestinatario(usuario);
            notificador.notificar(notificacion, usuario.getEstrategiaDeNotificacion().getFormaDeRecibir());
        });
    }
}
