package localizacion;

import external.georef.ServicioGeoref;
import external.georef.responseClases.ListadoLocalidades;
import external.georef.responseClases.ListadoMunicipios;
import external.georef.responseClases.ListadoProvincias;

import java.io.IOException;

public class AdapterLocalizacionGeorefApi implements AdapterLocalizacion {
    private ServicioGeoref adapterServicioGeoref = ServicioGeoref.getInstancia();

    public ListadoProvincias getListadoProvincias() throws IOException {
        return adapterServicioGeoref.listadoProvincias();
    }

    public ListadoProvincias getProvinciaById(int idProvincia) throws IOException {
        return adapterServicioGeoref.listadoProvincias(idProvincia);
    }

    public ListadoMunicipios getMunicipiosDeProvincia(String idProvincia) throws IOException {
        return adapterServicioGeoref.listadoMunicipios(idProvincia);
    }

    public ListadoMunicipios getMunicipioById(int idMunicipio) throws IOException {
        return adapterServicioGeoref.listadoMunicipios(idMunicipio);
    }

    public ListadoLocalidades getLocalidadesDeMunicipio(String idProvincia, String idMunicipio) throws IOException {
        return adapterServicioGeoref.listadoLocalidades(idProvincia, idMunicipio);
    }

    public ListadoLocalidades getLocalidadById(long idLocalidad) throws IOException {
        return adapterServicioGeoref.listadoLocalidades(idLocalidad);
    }
}
