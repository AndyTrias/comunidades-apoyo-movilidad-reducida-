package models.external.retrofit.georef.responseClases;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "provincia")
@EqualsAndHashCode(of = "id")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProvincia;

    @Column(name = "id")
    public int id;

    @Column(name = "nombre")
    public String nombre;
}