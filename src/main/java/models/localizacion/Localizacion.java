package models.localizacion;

import models.external.retrofit.georef.responseClases.ListadoLocalidades;
import models.external.retrofit.georef.responseClases.ListadoMunicipios;
import models.external.retrofit.georef.responseClases.ListadoProvincias;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "localizacion")
public class Localizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Setter
    @Transient
    private AdapterLocalizacion adapter = new AdapterLocalizacionGeorefApi();

    @Getter
    @Embedded
    private Ubicacion ubicacion;

    public Localizacion() {
        this.ubicacion = new Ubicacion();
    }

    public ListadoProvincias getListadoProvincias() throws Exception {
        return adapter.getListadoProvincias();
    }

    public ListadoMunicipios getMunicipiosDeProvincia(String idProvincia) {
        try {
            return adapter.getMunicipiosDeProvincia(idProvincia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ListadoLocalidades getLocalidadesDeMunicipio(String idProvincia, String idMunicipio) throws Exception {
        return adapter.getLocalidadesDeMunicipio(idProvincia, idMunicipio);
    }

    public void setUbicacionAsProvincia(int idProvincia) {
        try {
            this.ubicacion.setProvincia(adapter.getProvinciaById(idProvincia).provincias.get(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUbicacionAsMunicipio(int idMunicipio) {
        try {
            ListadoMunicipios municipio = adapter.getMunicipioById(idMunicipio);
            this.ubicacion.setMunicipio(municipio.municipios.get(0));
            this.ubicacion.getMunicipio().setProvincia(municipio.municipios.get(0).provincia);
            this.ubicacion.setProvincia(municipio.municipios.get(0).provincia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUbicacionAsLocalidad(long idLocalidad) {
        try {
            ListadoLocalidades localidad = adapter.getLocalidadById(idLocalidad);
            this.ubicacion.setLocalidad(localidad.localidades_censales.get(0));
            this.ubicacion.setMunicipio(localidad.localidades_censales.get(0).municipio);
            this.ubicacion.getMunicipio().setProvincia(localidad.localidades_censales.get(0).municipio.provincia);
            this.ubicacion.setProvincia(localidad.localidades_censales.get(0).provincia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
