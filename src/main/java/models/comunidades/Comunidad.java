package models.comunidades;

import models.converters.NotificadorConverter;
import lombok.Getter;
import lombok.Setter;
import models.entidades.Establecimiento;
import models.external.apiServicio.responseClases.ComunidadDTO;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.servicios.PrestacionDeServicio;
import models.notificaciones.notificador.CierreIncidente;
import models.notificaciones.notificador.Notificador;
import models.servicios.Servicio;
import models.usuario.Usuario;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "comunidad")
public class Comunidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comunidad")
    private List<Membresia> membresias;

    @Getter
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<PrestacionDeServicio> serviciosDeInteres;

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comunidad_id")
    private List<IncidenteDeComunidad> incidentes;

    @Setter
    @Convert(converter = NotificadorConverter.class)
    @Column(name = "notificador")
    private Notificador notificador;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.serviciosDeInteres = new HashSet<>();
        this.membresias = new ArrayList<>();
        this.incidentes = new ArrayList<>();
        this.notificador = new CierreIncidente();
    }

    public Comunidad() {}

    public void agregarMembresia(Membresia membresia) {
        if (!membresias.contains(membresia)) {
            membresias.add(membresia);
        }
    }

    public void eliminarMemebresia(Membresia membresia) {
        membresias.remove(membresia);
    }


    //TODO:  Ver que hacemos
    //    public void eliminarUsuario(Usuario usuario) {
    //        eliminarUsuarioDeSuRol(usuario);
    //    }

    public void agregarServicioDeInteres(PrestacionDeServicio servicio) {
        if (!serviciosDeInteres.contains(servicio)) {
            serviciosDeInteres.add(servicio);
        }
    }

    public void abrirIncidente(Incidente incidente) {
        if (!estaAbiertoElIncidente(incidente)) {
            agregarIncidente(incidente);
        }
    }

    public void cerrarIncidente(Incidente incidente, Usuario usuario) {
        if (this.estaAbiertoElIncidente(incidente)) {
            incidente.cerrar();
            incidentes.stream()
                .filter(i -> i.getIncidente().equals(incidente))
                .forEach(i -> i.cerrarIncidente(usuario));
           notificador.notificar(usuario, incidente);
        }
    }

    public boolean estaAbiertoElIncidente(Incidente incidente) {
        return incidentes.stream()
            .filter(i -> i.getIncidente().getPrestacionDeServicio()
                .equals(incidente.getPrestacionDeServicio()))
            .anyMatch(IncidenteDeComunidad::isAbierto);
    }


    private void agregarIncidente(Incidente incidente) {
        IncidenteDeComunidad incidenteNuevo = new IncidenteDeComunidad();
        incidenteNuevo.setIncidente(incidente);
        incidenteNuevo.setAbierto(true);
        incidentes.add(incidenteNuevo);
    }

    public int getCantidadDeAfectados(PrestacionDeServicio prestacionDeServicio) {
        return (int) membresias.stream()
            .filter(m -> m.getAfectacion(prestacionDeServicio).isAfectado())
            .count();
    }

    public Map<String, Object> getEstadisticas() {
        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("miembros", membresias.size());
        estadisticas.put("abiertos", incidentes.stream().filter(IncidenteDeComunidad::isAbierto).count());
        estadisticas.put("cerrados", incidentes.stream().filter(i -> !i.isAbierto()).count());
        estadisticas.put("prestaciones", serviciosDeInteres.size());
        return estadisticas;
    }

    public Integer getGradoDeConfianza(){
        return 1;
    }

    public List<Integer> getIdEstablecimientoObservados(List<Establecimiento> establecimientos){
        List<Integer> ids = new ArrayList<>();
        establecimientos.stream().filter(e -> e.getServicios().stream().anyMatch(s -> serviciosDeInteres.contains(s))).forEach(e -> ids.add(Integer.valueOf(String.valueOf(e.getId()))));
        return ids;
    }

    public List<Integer> getIdServiciosObservados(){
        List<Integer> ids = new ArrayList<>();
        serviciosDeInteres.forEach(s -> ids.add(Integer.valueOf(String.valueOf(s.getId()))));
        return ids;
    }

    public List<Integer> getIdMiembros(){
        List<Integer> ids = new ArrayList<>();
        membresias.forEach(m -> ids.add(Integer.valueOf(String.valueOf(m.getUsuario().getId()))));
        return ids;
    }

    public ComunidadDTO.EstadoComunidad getEstado(){
        return ComunidadDTO.EstadoComunidad.ACTIVADA;
    }

}

