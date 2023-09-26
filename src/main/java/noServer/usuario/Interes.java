package noServer.usuario;

import lombok.Setter;
import noServer.servicios.Servicio;
import noServer.entidades.Entidad;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "interes")
public class Interes {
    @Id
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


    public Interes(){
    }

}
