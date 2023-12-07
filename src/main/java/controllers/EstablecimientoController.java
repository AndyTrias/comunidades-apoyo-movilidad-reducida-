package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoEstablecimiento;
import server.exceptions.EntidadNoExistenteException;

import java.util.*;

@AllArgsConstructor
public class EstablecimientoController {

  private RepoEstablecimiento repoEstablecimiento;
  private RepoEntidad repoEntidad;

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    Optional<String> entidadIdParam = Optional.of(ctx.pathParam("id"));

    Long establecimiento = Long.valueOf(entidadIdParam.get());
    Entidad entidad = repoEntidad.buscar(establecimiento);
    List<Establecimiento> establecimientos = new ArrayList<>(entidad.getEstablecimientos());
    model.put("entidad", entidad);

    model.put("establecimientos", establecimientos);
    model.put("administrador", true);
    ctx.render("establecimientos/establecimientos.hbs", model);
  }

  public void index(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("establecimientos", repoEstablecimiento.buscarTodos());
    model.put("administrador", true);
    ctx.render("establecimientos/establecimientos.hbs", model);
  }


  public void edit(Context ctx) {
    Map<String, Object> model = new HashMap<>();

    Long establecimientoId = Long.parseLong(ctx.pathParam("id"));
    Optional.ofNullable(repoEstablecimiento.buscar(establecimientoId))
        .ifPresentOrElse(
            establecimiento -> {
              model.put("establecimiento", establecimiento);

              Entidad entidad = repoEntidad.buscarEntidadporEstablecimiento(establecimiento);
              model.put("entidad", entidad);

              List<Entidad> establecimientos = new ArrayList<>(repoEntidad.buscarTodos());
              establecimientos.remove(entidad);
              model.put("entidades", establecimientos);

              model.put("administrador", true);
              ctx.render("establecimientos/establecimiento.hbs", model);
            },
            () -> {
              throw new EntidadNoExistenteException("No existe la entidad");
            }
        );
  }


  public void delete(Context ctx) {
    Long establecimiento = Long.parseLong(ctx.pathParam("id"));
    Optional.ofNullable(repoEstablecimiento.buscar(establecimiento))
        .ifPresentOrElse(
            entidad -> {
              repoEstablecimiento.eliminar(entidad);
            },
            () -> {
              throw new EntidadNoExistenteException("No existe la entidad");
            }
        );
    ctx.redirect("/admin/establecimientos");
  }

  public void update(Context ctx) {
    Long establecimientoId = Long.parseLong(ctx.pathParam("id"));

    Establecimiento establecimiento = Optional.ofNullable(repoEstablecimiento.buscar(establecimientoId)).orElseThrow(() -> new EntidadNoExistenteException("No existe el establecimiento"));
    establecimiento.setNombre(ctx.formParam("nombre"));

    Entidad entidadVieja = repoEntidad.buscarEntidadporEstablecimiento(establecimiento);
    entidadVieja.sacarEstablecimiento(establecimiento);

          List<Long> numbersList = ctx.formParams("prestadora").stream()
                  .filter(str -> str.matches("\\d+")) // Only consider strings with digits
                  .map(Long::valueOf) // Convert each string to Long
                  .toList();
    Entidad entidadNueva = repoEntidad.buscar(numbersList.get(0));
    entidadNueva.agregarEstablecimiento(establecimiento);

    repoEntidad.modificar(entidadVieja);
    repoEntidad.modificar(entidadNueva);

    ctx.redirect("/admin/entidades/" + entidadNueva.getId() + "/establecimientos");
  }
}

