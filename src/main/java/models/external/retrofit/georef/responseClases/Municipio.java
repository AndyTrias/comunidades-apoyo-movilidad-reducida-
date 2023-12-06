package models.external.retrofit.georef.responseClases;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "municipio")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMunicipio;

    @Getter
    @Column(name = "id")
    public int id;

    @Getter
    @Column(name = "nombre")
    public String nombre;

    @Setter
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    public Provincia provincia;

}
