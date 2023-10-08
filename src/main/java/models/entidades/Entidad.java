package models.entidades;

import lombok.NoArgsConstructor;
import models.incidentes.Incidente;
import models.localizacion.Localizacion;
import models.servicios.PrestacionDeServicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "entidad")
@NoArgsConstructor
public class Entidad {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "entidad_id", nullable = false)
    private Set<Establecimiento> establecimientos;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    private Localizacion localizacion;

    @Getter
    @Column(name = "nombre")
    private String nombre;


    public Entidad(String nombre, Localizacion localizacion) {
        this.nombre = nombre;
        this.establecimientos = new HashSet<>();
        this.localizacion = localizacion;
    }

    public List<PrestacionDeServicio> getPrestacionesDeServicios() {
        List<PrestacionDeServicio> prestaciones = new ArrayList<>();
        for (Establecimiento establecimiento : this.establecimientos) {
            prestaciones.addAll(establecimiento.getServicios());
        }
        return prestaciones;
    }

    public void agregarEstablecimiento(Establecimiento establecimiento) {
        this.establecimientos.add(establecimiento);
    }

    public List<Incidente> getIncidentes() {
        List<Incidente> incidentes = new ArrayList<>();
        for (Establecimiento establecimiento : this.establecimientos) {
            establecimiento.getServicios().forEach(servicio -> incidentes.addAll(servicio.getIncidentes()));
        }
        return incidentes;
    }
}
