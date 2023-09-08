package localizacion;

import apiCalls.georef.responseClases.*;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localizacion")
public class Localizacion {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    @Getter @Setter private int id;

    @Transient
    @Setter AdapterLocalizacion adapter;

    @ManyToOne
    @JoinColumn(name = "ubicacion_id")
    @Getter private Ubicacion ubicacion = new Ubicacion();

    @Transient
    public ListadoProvincias getListadoProvincias() throws Exception {
        return adapter.getListadoProvincias();
    }
    @Transient
    public ListadoMunicipios getMunicipiosDeProvincia(String idProvincia) throws Exception {
        return adapter.getMunicipiosDeProvincia(idProvincia);
    }
    @Transient
    public ListadoLocalidades getLocalidadesDeMunicipio(String idProvincia, String idMunicipio) throws Exception {
        return adapter.getLocalidadesDeMunicipio(idProvincia, idMunicipio);
    }
    @Transient
    public void setProvincia(int idProvincia) throws Exception {
        this.ubicacion.setProvincia(adapter.getProvinciaId(idProvincia).provincias.get(0));
    }
    @Transient
    public void setMunicipio(int idMunicipio) throws Exception {
        ListadoMunicipios municipio = adapter.getMunicipioId(idMunicipio);
        this.ubicacion.setMunicipio(municipio.municipios.get(0));
        this.ubicacion.setProvincia(municipio.municipios.get(0).provincia);
    }
    @Transient
    public void setLocalidad(long idLocalidad) throws Exception {
        ListadoLocalidades localidad = adapter.getLocalidadId(idLocalidad);
        this.ubicacion.setLocalidad(localidad.localidades.get(0));
        this.ubicacion.setMunicipio(localidad.localidades.get(0).municipio);
        this.ubicacion.setProvincia(localidad.localidades.get(0).provincia);
    }
}
