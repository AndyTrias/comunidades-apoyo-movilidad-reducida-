package localizacion;

import apiCalls.georef.responseClases.*;
import lombok.Getter;
import lombok.Setter;

public class Localizacion {
    @Setter AdapterLocalizacion adapter;
    @Getter private Ubicacion ubicacion = new Ubicacion();

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
        this.ubicacion.setProvincia(adapter.getProvinciaId(idProvincia).provincias.get(0));
    }

    public void setMunicipio(int idMunicipio) throws Exception {
        this.ubicacion.setMunicipio(adapter.getMunicipioId(idMunicipio).municipios.get(0));
    }

    public void setLocalidad(long idLocalidad) throws Exception {
        this.ubicacion.setLocalidad(adapter.getLocalidadesDeMunicipio(idLocalidad).localidades.get(0));
    }
}
