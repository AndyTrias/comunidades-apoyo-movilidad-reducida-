package usuario;

import lombok.Setter;
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
    @Setter
    @ManyToOne
    private Servicio servicio;

    @Getter
    @Setter
    @ManyToOne
    private Entidad entidad;


    public Interes(){
    }

}
