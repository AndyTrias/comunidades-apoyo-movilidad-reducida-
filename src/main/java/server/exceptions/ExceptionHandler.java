package server.exceptions;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandler {
  public static void handleInvalidCredentials(CredencialesInvalidaException e, Context ctx) {
    ctx.status(401);
    ctx.result(e.getMessage());
    renderizarError(ctx);
  }

  public static void handleInvalidPermission(PermisosInvalidosException e, Context ctx) {
    ctx.status(403);
    ctx.result(e.getMessage());
    renderizarError(ctx);
  }

  public static void handleEntidadNoExistente(EntidadNoExistenteException e, Context ctx) {
    ctx.status(400);
    ctx.result(e.getMessage());
    renderizarError(ctx);
  }

  public static void handleServerException(ServerErrorException e, Context ctx) {
    ctx.status(500);
    ctx.result(e.getMessage());
    renderizarError(ctx);
  }

  public static void handlePaginaNoEncontrada(PaginaNoEncontradaException e, Context ctx) {
    ctx.result(e.getMessage());
    renderizarError(ctx);
  }

  private static void renderizarError(Context ctx){
    Map<String, Object> model = new HashMap<>();
    model.put("error", ctx.status());
    model.put("errorCode", ctx.status().toString().substring(0, 3));
    model.put("mensaje", ctx.result());
    ctx.render("error.hbs", model);
  }

}
