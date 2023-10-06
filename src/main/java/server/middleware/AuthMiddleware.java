package server.middleware;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import models.comunidades.TipoRol;

import java.nio.file.AccessDeniedException;

public class AuthMiddleware {
    public static void apply(JavalinConfig config) {
        config.accessManager((handler, ctx, permittedRoles) -> {
            TipoRol userRole = getUserRoleType(ctx);


            if (ctx.cookie("usuario_id") == null) {
                ctx.redirect("/login");
                return;
            }

            if((permittedRoles.isEmpty() || permittedRoles.contains(userRole)) && ctx.cookie("usuario_id") != null) {
                handler.handle(ctx);
            }
            else {
                ctx.redirect("/login");
            }
        });
    }

    private static TipoRol getUserRoleType(Context context) {
        return context.cookie("tipo_rol") != null?
                TipoRol.valueOf(context.cookie("tipo_rol")) : null;
    }
}
