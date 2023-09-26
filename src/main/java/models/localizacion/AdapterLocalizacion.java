package models.localizacion;

import models.external.georef.responseClases.ListadoLocalidades;
import models.external.georef.responseClases.ListadoMunicipios;
import models.external.georef.responseClases.ListadoProvincias;

import java.io.IOException;

public interface AdapterLocalizacion {
    public ListadoProvincias getListadoProvincias() throws IOException;
    public ListadoMunicipios getMunicipiosDeProvincia(String idProvincia) throws IOException;
    public ListadoLocalidades getLocalidadesDeMunicipio(String idProvincia, String idMunicipio) throws IOException;

    public ListadoProvincias getProvinciaById(int idProvincia) throws IOException;
    public ListadoMunicipios getMunicipioById(int idMunicipio) throws IOException;
    public ListadoLocalidades getLocalidadById(long idLocalidad) throws IOException;
}
