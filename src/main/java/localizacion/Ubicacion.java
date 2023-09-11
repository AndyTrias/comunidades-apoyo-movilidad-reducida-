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

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "localidad_id")
    private Localidad localidad;
}
