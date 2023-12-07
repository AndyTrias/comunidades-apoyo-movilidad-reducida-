package controllers;

import io.javalin.http.Context;

import lombok.AllArgsConstructor;
import models.configs.Config;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.Establecimiento;
import models.external.retrofit.georef.Georef;
import models.external.retrofit.georef.responseClases.ListadoMunicipios;
import models.external.retrofit.georef.responseClases.ListadoProvincias;
import models.external.retrofit.georef.responseClases.Municipio;
import models.external.retrofit.georef.responseClases.Provincia;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.repositorios.*;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;
import server.exceptions.EntidadNoExistenteException;
import server.exceptions.PermisosInvalidosException;

import java.util.*;

@AllArgsConstructor
public class CargaManualController extends BaseController {

  private RepoServicio repoServicio;
  private RepoEntidad repoEntidad;
  private RepoEstablecimiento repoEstablecimiento;
  private RepoEntidadPrestadora repoEntidadPrestadora;
  private RepoLocalizacion repoLocalizacion;

  public void cargaManual(Context ctx) {
    Map<String, Object> model = new HashMap<>();

    model.put("entidades", repoEntidad.buscarTodos());
    model.put("servicios", repoServicio.buscarTodos());
    model.put("establecimientos", repoEstablecimiento.buscarTodos());
    model.put("prestadoras", repoEntidadPrestadora.buscarTodos());
    model.put("administrador", true);
    model.put("API_GEOREF", Config.getInstance().API_GEOREF);
    model.put("provincias", Georef.getInstancia().listadoProvincias().provincias);

    ctx.render("admin/cargaManual.hbs", model);
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


  public void guardarServicio(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_servicio")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un servicio");
    }

    Servicio servicio = new Servicio(ctx.formParam("nombre"));
    repoServicio.agregar(servicio);
    ctx.redirect("/admin/servicios");
  }

  public void guardarEntidad(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_entidad")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un entidad");
    }
    List<Long> numbersList = ctx.formParams("prestadora").stream()
            .filter(str -> str.matches("\\d+")) // Only consider strings with digits
            .map(Long::valueOf) // Convert each string to Long
            .toList();
    EntidadPrestadora entidadPrestadora = repoEntidadPrestadora.buscar(numbersList.get(0));
    Localizacion localizacion = guardarLocalizacion(ctx);
    Entidad entidad = new Entidad(ctx.formParam("nombre"), localizacion);
    entidadPrestadora.agregarEntidad(entidad);
    repoEntidadPrestadora.modificar(entidadPrestadora);
    ctx.redirect("/admin/entidades");
  }

  public void guardarEstablecimiento(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_establecimiento")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un establecimiento");
    }
    List<Long> numbersList = ctx.formParams("entidad").stream()
            .filter(str -> str.matches("\\d+")) // Only consider strings with digits
            .map(Long::valueOf) // Convert each string to Long
            .toList();
    Entidad entidad = repoEntidad.buscar(numbersList.get(0));
    if (entidad == null) {
      throw new EntidadNoExistenteException("No existe esa entidad");
    }

    Establecimiento establecimiento = new Establecimiento(
        ctx.formParam("nombre"),
        new Localizacion()
    );

    entidad.agregarEstablecimiento(establecimiento);
    repoEntidad.modificar(entidad);

    ctx.redirect("/admin/entidades/" + entidad.getId() + "/establecimientos");
  }


  public void guardarPrestacion(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_prestacion")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un prestacion");
    }

    List<Long> numbersList = ctx.formParams("prestadora").stream()
            .filter(str -> str.matches("\\d+")) // Only consider strings with digits
            .map(Long::valueOf) // Convert each string to Long
            .toList();
    Establecimiento establecimiento = repoEstablecimiento.buscar(numbersList.get(0));
    if (establecimiento == null) {
      throw new EntidadNoExistenteException("No existe ese establecimiento");
    }

    Servicio servicio = repoServicio.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("servicio"))));
    if (servicio == null) {
      throw new EntidadNoExistenteException("No existe ese servicio");
    }

    PrestacionDeServicio prestacionDeServicio = new PrestacionDeServicio(servicio, ctx.formParam("nombre"), new UbicacionExacta());
    establecimiento.agregarServicioPrestado(prestacionDeServicio);
    repoEstablecimiento.modificar(establecimiento);

    ctx.redirect("/admin");
  }
}
