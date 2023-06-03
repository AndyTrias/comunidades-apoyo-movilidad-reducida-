package localizacion;

import apiCalls.georef.responseClases.ListadoLocalidades;
import apiCalls.georef.responseClases.ListadoMunicipios;
import apiCalls.georef.responseClases.ListadoProvincias;

import java.io.IOException;

public interface AdapterLocalizacion {
    public ListadoProvincias getListadoProvincias() throws IOException;

    public ListadoProvincias getProvinciaId(int idProvincia) throws IOException;

    public ListadoMunicipios getMunicipiosDeProvincia(String idProvincia) throws IOException;

    public ListadoMunicipios getMunicipioId(int idMunicipio) throws IOException;

    public ListadoLocalidades getLocalidadesDeMunicipio(String idProvincia, String idMunicipio) throws IOException;

    public ListadoLocalidades getLocalidadesDeMunicipio(long idLocalidad) throws IOException;
}
