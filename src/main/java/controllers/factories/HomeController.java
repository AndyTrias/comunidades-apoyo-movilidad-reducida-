package controllers.factories;

import controllers.BaseController;
import io.javalin.http.Context;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;

public class HomeController extends BaseController {
  public void index(Context ctx) {
    Usuario usuario = usuarioLogueado(ctx);


    switch (usuario.getRol().getTipoRol()) {
      case MIEMBRO -> ctx.render("home.hbs");
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
}
