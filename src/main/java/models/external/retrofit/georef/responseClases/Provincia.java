package models.external.retrofit.georef.responseClases;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "provincia")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProvincia;

    @Column(name = "id")
    public int id;

    @Column(name = "nombre")
    public String nombre;
}