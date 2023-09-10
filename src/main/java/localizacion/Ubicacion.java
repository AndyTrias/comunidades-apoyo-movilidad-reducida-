package localizacion;

import external.georef.responseClases.Localidad;
import external.georef.responseClases.Municipio;
import external.georef.responseClases.Provincia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "ubicacion")

public class Ubicacion {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Getter @Setter private int id;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    @Getter @Setter private Provincia provincia;
    @ManyToOne
    @JoinColumn(name = "municipio_id")
    @Getter @Setter private Municipio municipio;
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    @Getter @Setter private Localidad localidad;
}
