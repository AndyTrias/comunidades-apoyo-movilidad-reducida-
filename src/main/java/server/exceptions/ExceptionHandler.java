package server.exceptions;

import io.javalin.http.Context;

public class ExceptionHandler {
  public static void handleInvalidCredentials(CredencialesInvalidaException e, Context ctx) {
    ctx.status(401); // Unauthorized
    ctx.result(e.getMessage());
  }
}
