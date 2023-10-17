package models.servicios;

import models.incidentes.Incidente;
import models.localizacion.UbicacionExacta;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prestacion_de_servicio")
public class PrestacionDeServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Getter
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestacion_de_servicio_id")
    private List<Incidente> incidentes;

    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    private UbicacionExacta ubicacionExacta;

    public PrestacionDeServicio(Servicio servicio, String nombre, UbicacionExacta ubicacionExacta) {
        this.servicio = servicio;
        this.nombre = nombre;
        this.incidentes = new ArrayList<>();
        this.ubicacionExacta = ubicacionExacta;
    }

    public PrestacionDeServicio() {

    }

    public void agregarIncidente(Incidente incidente){
        incidentes.add(incidente);
    }

}


