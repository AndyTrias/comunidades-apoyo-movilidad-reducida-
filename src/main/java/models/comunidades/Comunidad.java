package models.comunidades;

import models.converters.NotificadorConverter;
import lombok.Getter;
import lombok.Setter;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.servicios.PrestacionDeServicio;
import models.notificaciones.notificador.CierreIncidente;
import models.notificaciones.notificador.Notificador;
import models.usuario.Usuario;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "comunidad")
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comunidad_id")
    private List<Rol> roles;

    @Getter
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<PrestacionDeServicio> serviciosDeInteres;

//    @Getter
//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinTable(name = "incidentes_abiertos", joinColumns = @JoinColumn(name = "comunidad_id"), inverseJoinColumns = @JoinColumn(name = "incidente_id"))
//    private List<Incidente> incidentesAbiertos;
//
//    @Getter
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "incidentes_cerrados", joinColumns = @JoinColumn(name = "comunidad_id"), inverseJoinColumns = @JoinColumn(name = "incidente_id"))
//    private List<Incidente> incidentesCerrados;

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "incidente_de_comunidad_id")
    private List<IncidenteDeComunidad> incidentes;

    @Setter
    @Convert(converter = NotificadorConverter.class)
    @Column(name = "notificador")
    private Notificador notificador;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.serviciosDeInteres = new HashSet<>();
        this.roles = new ArrayList<>();
        this.incidentes = new ArrayList<>();
        this.roles.add(new Rol("Miembro", null));
        this.notificador = new CierreIncidente();
    }

    public Comunidad() {

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

    public Integer getCantidadDeUsuarios() {
        return roles.stream().mapToInt(r -> r.getUsuarios().size()).sum();
    }

    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        roles.forEach(r -> usuarios.addAll(r.getUsuarios()));
        return usuarios;
    }

    public void agregarServicioDeInteres(PrestacionDeServicio servicio) {
        serviciosDeInteres.add(servicio);
    }



    public void abrirIncidente(Incidente incidente) {
        if (!estaCerradoElIncidente(incidente)) {
            agregarIncidente(incidente);
        }
    }


    public void cerrarIncidente(Incidente incidente, Usuario usuario) {
        if (this.estaCerradoElIncidente(incidente)) {
            throw new RuntimeException("El incidente ya está cerrado");
        }

        Optional<IncidenteDeComunidad> incidenteDeComunidadOptional = incidentes.stream()
            .filter(i -> i.getIncidente().getPrestacionDeServicio().equals(incidente.getPrestacionDeServicio()))
            .findFirst();

        if (incidenteDeComunidadOptional.isPresent()) {
            IncidenteDeComunidad incidenteDeComunidad = incidenteDeComunidadOptional.get();
            incidenteDeComunidad.setAbierto(false);
            notificador.notificar(usuario, incidente);
        } else {
            throw new RuntimeException("No se encontró el incidente correspondiente");
        }
    }


    public boolean estaCerradoElIncidente(Incidente incidente) {
        return incidentes.stream()
            .filter(i -> i.getIncidente().getPrestacionDeServicio().equals(incidente.getPrestacionDeServicio()))
            .noneMatch(IncidenteDeComunidad::isAbierto);
    }

    public int getCantidadDeAfectados() {
        return getUsuarios().stream().filter(u -> u.getMembresia(this).esAfectado()).mapToInt(u -> 1).sum();
    }


    private void agregarIncidente(Incidente incidente) {
        IncidenteDeComunidad incidenteNuevo = new IncidenteDeComunidad();
        incidenteNuevo.setIncidente(incidente);
        incidenteNuevo.setAbierto(true);
        incidentes.add(incidenteNuevo);
    }
}

