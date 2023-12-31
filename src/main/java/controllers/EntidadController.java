package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.configs.Config;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.external.retrofit.georef.Georef;
import models.external.retrofit.georef.responseClases.Provincia;
import models.localizacion.Localizacion;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoLocalizacion;
import server.exceptions.EntidadNoExistenteException;

import java.util.*;


@AllArgsConstructor
public class EntidadController {
  private RepoEntidad repoEntidad;
  private RepoEntidadPrestadora repoEntidadPrestadora;
  private RepoLocalizacion repoLocalizacion;

  public void update(Context ctx) {
    Long entidadId = Long.parseLong(ctx.pathParam("id"));

    Entidad entidad = Optional.ofNullable(repoEntidad.buscar(entidadId)).orElseThrow(() -> new EntidadNoExistenteException("No existe la entidad"));
    entidad.setNombre(ctx.formParam("nombre"));

    EntidadPrestadora prestadoraVieja = repoEntidadPrestadora.buscarPrestadoraporEntidad(entidad.getId());
    prestadoraVieja.sacarEntidad(entidad);

  List<Long> numbersList = ctx.formParams("prestadora").stream()
          .filter(str -> str.matches("\\d+")) // Only consider strings with digits
          .map(Long::valueOf) // Convert each string to Long
          .toList();
    EntidadPrestadora prestadoraNueva = repoEntidadPrestadora.buscar(numbersList.get(0));
    prestadoraNueva.agregarEntidades(entidad);

    entidad.setLocalizacion(guardarLocalizacion(ctx));

    repoEntidadPrestadora.modificar(prestadoraVieja);
    repoEntidadPrestadora.modificar(prestadoraNueva);

    ctx.redirect("/admin/entidades");
  }

  public void edit(Context ctx) {
    Map<String, Object> model = new HashMap<>();

    Long entidadId = Long.parseLong(ctx.pathParam("id"));
    Optional.ofNullable(repoEntidad.buscar(entidadId))
        .ifPresentOrElse(
            entidad -> {
              model.put("entidad", entidad);

              EntidadPrestadora prestadora = repoEntidadPrestadora.buscarPrestadoraporEntidad(entidad.getId());
              model.put("prestadora", prestadora);

              List<EntidadPrestadora> prestadoras = new ArrayList<>(repoEntidadPrestadora.buscarTodos());
              prestadoras.remove(prestadora);
              model.put("prestadoras", prestadoras);
              model.put("API_GEOREF", Config.getInstance().API_GEOREF);
              model.put("provincia", entidad.getLocalizacion().obtenerProvincia());

              List<Provincia> provincias = Georef.getInstancia().listadoProvincias().provincias;
              Optional<Provincia> provinciaActual = Optional.ofNullable(entidad.getLocalizacion().obtenerProvincia());
              provincias.removeIf(provincia -> provincia.equals(provinciaActual.orElse(null)));


              model.put("provincias", provincias);

              model.put("municipio", entidad.getLocalizacion().obtenerMunicipio());
              model.put("localidad", entidad.getLocalizacion().obtenerLocalidad());

              model.put("administrador", true);
              ctx.render("entidades/entidad.hbs", model);
            },
            () -> {
              throw new EntidadNoExistenteException("No existe la entidad");
            }
        );
  }

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("entidades", repoEntidad.buscarTodos());
    model.put("administrador", true);
    ctx.render("entidades/entidades.hbs", model);
  }

  public void delete(Context ctx) {
    Long entidadId = Long.parseLong(ctx.pathParam("id"));
    Optional.ofNullable(repoEntidad.buscar(entidadId))
        .ifPresentOrElse(
            entidad -> {
              repoEntidad.eliminar(entidad);
            },
            () -> {
              throw new EntidadNoExistenteException("No existe la entidad");
            }
        );
    ctx.redirect("/admin/entidades");
  }

  private Localizacion guardarLocalizacion(Context ctx) {
    String idProvincia = ctx.formParam("provincia");
    String idMunicipio = ctx.formParam("municipio");
    String idLocalidad = ctx.formParam("localidad");

    if (idProvincia == null || idProvincia.isEmpty()) {
      return null;
    }

    if (idMunicipio == null || idMunicipio.isEmpty()) {
      Localizacion localizacion = new Localizacion();
      localizacion.setUbicacionAsProvincia(Integer.parseInt(idProvincia));
      repoLocalizacion.agregarOModificar(localizacion);
      return localizacion;
    }

    if (idLocalidad == null || idLocalidad.isEmpty()) {
      Localizacion localizacion = new Localizacion();
      localizacion.setUbicacionAsMunicipio(Integer.parseInt(idMunicipio));
      repoLocalizacion.agregarOModificar(localizacion);
      return localizacion;
    }

    Localizacion localizacion = new Localizacion();
    localizacion.setUbicacionAsLocalidad(Long.parseLong(idLocalidad));
    repoLocalizacion.agregarOModificar(localizacion);
    return localizacion;
  }
}


