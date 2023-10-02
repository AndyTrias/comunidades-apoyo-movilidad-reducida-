package models.usuario;

import lombok.Setter;
import models.servicios.Servicio;
import models.entidades.Entidad;
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