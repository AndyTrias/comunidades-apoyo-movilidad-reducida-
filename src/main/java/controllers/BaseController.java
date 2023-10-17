package controllers;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoGenerico;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;
import server.exceptions.EntidadNoExistenteException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class BaseController {

  protected Usuario usuarioLogueado(Context ctx) {
    if (ctx.sessionAttribute("usuario_id") == null)
      return null;
    return RepoUsuario.INSTANCE.buscar(Long.parseLong(Objects.requireNonNull(ctx.sessionAttribute("usuario_id"))));
  }

  protected Comunidad obtenerComunidad(String id) {
    Comunidad comunidad = RepoComunidad.INSTANCE.buscar(Long.parseLong(id));

    if (comunidad == null) {
      throw new EntidadNoExistenteException("No existe esa comunidad");
    }

    return comunidad;
  }
}

