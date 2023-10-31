package server.middleware;

import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import models.comunidades.TipoRol;
import server.exceptions.PermisosInvalidosException;

import java.nio.file.AccessDeniedException;

public class AuthMiddleware {
    public static void apply(JavalinConfig config) {
        config.accessManager((handler, ctx, permittedRoles) -> {
            String path = ctx.path();

            if (authPath(path)) {
                handler.handle(ctx);
                return;
            }

            if (ctx.sessionAttribute("usuario_id") == null) {
                ctx.redirect("/login");
                return;
            }

            TipoRol userRole = getUserRoleType(ctx);
            if (permittedRoles.isEmpty() || permittedRoles.contains(userRole)) {
                handler.handle(ctx);
                return;
            }

            throw new PermisosInvalidosException("No tiene permisos para acceder a la siguiente ruta");
        });
    }

    private static TipoRol getUserRoleType(Context context) {
        Object tipoRolAttribute = context.sessionAttribute("tipo_rol");
        if (tipoRolAttribute == null) {
            return null;
        }
        return TipoRol.valueOf(tipoRolAttribute.toString());
    }

    private static boolean authPath(String path) {
        return path.contains("/login") || path.contains("/register");
    }
}

