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

    public void setUbicacionAsProvincia(int idProvincia) throws Exception {
        this.ubicacion.setProvincia(adapter.getProvinciaById(idProvincia).provincias.get(0));
    }

    public void setUbicacionAsMunicipio(int idMunicipio) throws Exception {
        ListadoMunicipios municipio = adapter.getMunicipioById(idMunicipio);
        this.ubicacion.setMunicipio(municipio.municipios.get(0));
        this.ubicacion.setProvincia(municipio.municipios.get(0).provincia);
    }

    public void setUbicacionAsLocalidad(long idLocalidad) throws Exception {
        ListadoLocalidades localidad = adapter.getLocalidadById(idLocalidad);
        this.ubicacion.setLocalidad(localidad.localidades.get(0));
        this.ubicacion.setMunicipio(localidad.localidades.get(0).municipio);
        this.ubicacion.setProvincia(localidad.localidades.get(0).provincia);
    }
}
