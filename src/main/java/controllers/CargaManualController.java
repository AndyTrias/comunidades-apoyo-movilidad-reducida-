package controllers;

import io.javalin.http.Context;

import lombok.AllArgsConstructor;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.Establecimiento;
import models.external.retrofit.georef.Georef;
import models.external.retrofit.georef.responseClases.ListadoProvincias;
import models.external.retrofit.georef.responseClases.Municipio;
import models.external.retrofit.georef.responseClases.Provincia;
import models.localizacion.Localizacion;
import models.localizacion.UbicacionExacta;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoEstablecimiento;
import models.repositorios.RepoServicio;
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


  public void cargaManual(Context ctx) {
    Map<String, Object> model = new HashMap<>();

    model.put("entidades", repoEntidad.buscarTodos());
    model.put("servicios", repoServicio.buscarTodos());
    model.put("establecimientos", repoEstablecimiento.buscarTodos());
    model.put("prestadoras", repoEntidadPrestadora.buscarTodos());
    model.put("administrador", true);

    ctx.render("admin/cargaManual.hbs", model);
  }

  public void seleccionarUbicacion(Context ctx) {
    Map<String, Object> model = new HashMap<>();

    model.put("provincias", Georef.getInstancia().listadoProvincias().provincias);

    ctx.render("admin/seleccionarProvincia.hbs", model);
  }

  public void seleccionarMunicipio(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    Localizacion localizacion = new Localizacion();
    String idProvincia = ctx.queryParam("provincia");

    model.put("municipios", Georef.getInstancia().listadoMunicipios(idProvincia).municipios);

    ctx.render("admin/seleccionarMunicipio.hbs", model);
  }


  public void guardarServicio(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_servicio")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un servicio");
    }

    Servicio servicio = new Servicio(ctx.formParam("nombre"));
    repoServicio.agregar(servicio);
    ctx.redirect("/admin");
  }

  public void guardarEntidad(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_entidad")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un entidad");
    }

    EntidadPrestadora entidadPrestadora = repoEntidadPrestadora.buscar(Long.valueOf(ctx.formParam("prestadora")));
    Entidad entidad = new Entidad(ctx.formParam("nombre"), new Localizacion());
    entidadPrestadora.agregarEntidad(entidad);
    repoEntidadPrestadora.modificar(entidadPrestadora);
    ctx.redirect("/admin");
  }

  public void guardarEstablecimiento(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_establecimiento")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un establecimiento");
    }

    Entidad entidad = repoEntidad.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("entidad"))));
    if (entidad == null) {
      throw new EntidadNoExistenteException("No existe esa entidad");
    }

    Establecimiento establecimiento = new Establecimiento(
        ctx.formParam("nombre"),
        new Localizacion()
    );

    entidad.agregarEstablecimiento(establecimiento);
    repoEntidad.modificar(entidad);

    ctx.redirect("/admin");
  }


  public void guardarPrestacion(Context ctx) {

    Usuario usuario = usuarioLogueado(ctx);

    if (usuario == null || !usuario.getRol().tenesPermiso("crear_prestacion")) {
      throw new PermisosInvalidosException("No tienes permisos para crear un prestacion");
    }


    Establecimiento establecimiento = repoEstablecimiento.buscar(Long.valueOf(Objects.requireNonNull(ctx.formParam("establecimiento"))));
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