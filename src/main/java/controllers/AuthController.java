package controllers;

import io.javalin.http.Context;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;

public class AuthController {
    private RepoUsuario repoUsuario;

    public AuthController(RepoUsuario repoUsuario) {
        this.repoUsuario = repoUsuario;
    }

    public void showLogin(Context ctx) {
        ctx.render("auth/login.hbs");
    }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        Usuario usuario = repoUsuario.buscarPorEmail(email);

        if (usuario == null || !usuario.getContrasenia().equals(password)) {
            ctx.status(401);
            ctx.result("Usuario o contraseña incorrectos");
            return;
        }

        ctx.cookie("tipo_rol", usuario.getTipoRol().toString());
        ctx.cookie("usuario_id", usuario.getId().toString());
        ctx.redirect("/");
    }

    public void logout(Context ctx) {
        ctx.removeCookie("usuario_id");
        ctx.removeCookie("tipo_rol");
        ctx.redirect("/login");
    }
}
