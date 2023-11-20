package models.usuario;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import models.incidentes.Incidente;
import models.servicios.Servicio;
import models.entidades.Entidad;
import lombok.Getter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "interes")
public class Interes {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Servicio servicio;

    @Getter
    @Setter
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Entidad entidad;

    public Optional<Incidente> getIncidente() {
        return entidad.getIncidentes().stream().filter(incidente -> incidente.getPrestacionDeServicio().getServicio().equals(servicio)).findFirst();
    }

}
