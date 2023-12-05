package models.comunidades;

import lombok.Getter;
import lombok.Setter;
import models.external.retrofit.apiServicio1.responseClases.EstadoFusion;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "fusion")
public class Fusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoFusion estado;

    @ManyToOne
    private Comunidad comunidad1;

    @ManyToOne
    private Comunidad comunidad2;

    @Column(name = "fecha_creada")
    private Date fechaCreada;

    @Column(name = "realizada")
    private boolean realizada;

}
