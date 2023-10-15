package controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import models.repositorios.RepoGenerico;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class BaseController {

  protected Usuario usuarioLogueado(Context ctx) {
    if (ctx.sessionAttribute("usuario_id") == null)
      return null;
    return RepoUsuario.INSTANCE.buscar(Long.parseLong(Objects.requireNonNull(ctx.sessionAttribute("usuario_id"))));
  }

  Map<String, Object> crearModel(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("usuario", usuarioLogueado(ctx));
    return model;
  }
}

