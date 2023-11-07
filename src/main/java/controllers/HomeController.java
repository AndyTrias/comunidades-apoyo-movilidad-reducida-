package controllers;

import controllers.BaseController;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoOrganismoDeControl;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class HomeController extends BaseController {
  public void index(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);


    switch (usuario.getRol().getTipoRol()) {
      case MIEMBRO -> ctx.render("show/home.hbs");
      case ADMINISTRADOR_PLATAFORMA -> {
        ctx.redirect("/admin");
      }

      case ENTIDAD_PRESTADORA -> {
        ctx.redirect("/entidadPrestadora");
      }

      case ORGANISMO_DE_CONTROL -> {
        ctx.redirect("/organismoDeControl");
      }

    }
  }

  public void showAdmin(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("administrador", true);
    ctx.render("show/admin.hbs", model);
  }

}

