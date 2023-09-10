package usuario;

import servicios.Servicio;
import entidades.Entidad;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "interes")
public class Interes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToMany
    private Set<Servicio> servicios;

    @Getter
    @Transient
    private Set<Entidad> entidades;

    public Interes(){
        this.servicios = new HashSet<>();
        this.entidades = new HashSet<>();
    }

    public boolean estaInteresado(Servicio servicio, Entidad entidad) {
        return servicios.contains(servicio) && entidades.contains(entidad);
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public void agregarEntidad(Entidad entidad) {
        entidades.add(entidad);
    }


}
