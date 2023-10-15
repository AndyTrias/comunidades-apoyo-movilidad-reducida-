package controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class BaseController implements WithSimplePersistenceUnit {

  protected Usuario usuarioLogueado(Context ctx) {
    if (ctx.sessionAttribute("usuario_id") == null)
      return null;
    return entityManager()
        .find(Usuario.class, Long.parseLong(Objects.requireNonNull(ctx.sessionAttribute("usuario_id"))));
  }

  Map<String, Object> crearModel(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("usuario", usuarioLogueado(ctx));
    return model;
  }
}

