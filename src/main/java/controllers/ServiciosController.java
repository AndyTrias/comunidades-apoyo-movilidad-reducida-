package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.repositorios.RepoServicio;
import server.exceptions.EntidadNoExistenteException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class ServiciosController {
  private RepoServicio repoServicio;

  public void update(Context ctx) {
    Long servicioId = Long.parseLong(ctx.pathParam("id"));

    Optional.ofNullable(repoServicio.buscar(servicioId))
        .ifPresentOrElse(
            servicio -> {
              servicio.setNombre(ctx.formParam("nombre"));
              repoServicio.modificar(servicio);
            },
            () -> {
              throw new EntidadNoExistenteException("No existe el servicio");
            }
        );
    ctx.redirect("/admin/servicios");
  }

  public void edit(Context ctx) {
    Map<String, Object> model = new HashMap<>();

    Long servicioId = Long.parseLong(ctx.pathParam("id"));
    Optional.ofNullable(repoServicio.buscar(servicioId))
        .ifPresentOrElse(
            servicio -> {
              model.put("servicio", servicio);
              model.put("administrador", true);
              ctx.render("servicios/servicio.hbs", model);
            },
            () -> {
              throw new EntidadNoExistenteException("No existe el servicio");
            }
        );
  }

  public void show(Context ctx){
    Map<String, Object> model = new HashMap<>();
    model.put("servicios", repoServicio.buscarTodos());
    model.put("administrador", true);
    ctx.render("servicios/servicios.hbs", model);
  }

  public void delete(Context ctx){
    Long servicioId = Long.parseLong(ctx.pathParam("id"));
    Optional.ofNullable(repoServicio.buscar(servicioId))
        .ifPresentOrElse(
            servicio -> {
              repoServicio.eliminar(servicio);
            },
            () -> {
              throw new EntidadNoExistenteException("No existe el servicio");
            }
        );
    ctx.redirect("/admin/servicios");
  }
}
