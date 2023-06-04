package localizacion;

import apiCalls.georef.responseClases.*;
import lombok.Getter;
import lombok.Setter;

public class Localizacion {
    @Setter
    AdapterLocalizacion adapter;
    @Getter private Provincia provincia;
    @Getter private Municipio municipio;
    @Getter private Localidad localidad;


    public ListadoProvincias getListadoProvincias() throws Exception {
        return adapter.getListadoProvincias();
    }

    public ListadoMunicipios getMunicipiosDeProvincia(String idProvincia) throws Exception {
        return adapter.getMunicipiosDeProvincia(idProvincia);
    }

    public ListadoLocalidades getLocalidadesDeMunicipio(String idProvincia, String idMunicipio) throws Exception {
        return adapter.getLocalidadesDeMunicipio(idProvincia, idMunicipio);
    }

    public void setProvincia(int idProvincia) throws Exception {
        this.provincia = adapter.getProvinciaId(idProvincia).provincias.get(0);
    }

    public void setMunicipio(int idMunicipio) throws Exception {
        this.municipio = adapter.getMunicipioId(idMunicipio).municipios.get(0);
    }

    public void setLocalidad(long idLocalidad) throws Exception {
        this.localidad = adapter.getLocalidadesDeMunicipio(idLocalidad).localidades.get(0);
    }
}
