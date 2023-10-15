package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.TipoRol;
import models.repositorios.RepoRol;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;

@AllArgsConstructor
public class AuthController {
    private RepoUsuario repoUsuario;

    public void showLogin(Context ctx) {
        ctx.render("auth/login.hbs");
    }
    public void showRegister(Context ctx) {ctx.render("auth/register.hbs"); }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        Usuario usuario = repoUsuario.buscarPorEmail(email).orElse(null);

        if (usuario == null || !usuario.getContrasenia().equals(password)) {
            ctx.status(401);
            ctx.result("Usuario o contraseña incorrectos");
            return;
        }

        loguear_atributos(ctx, usuario);
    }

    public void logout(Context ctx) {
        ctx.consumeSessionAttribute("usuario_id");
        ctx.consumeSessionAttribute("tipo_rol");
        ctx.redirect("/login");
    }


    public void register(Context ctx) {
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String telefono = ctx.formParam("telefono");

        if (repoUsuario.buscarPorEmail(email).orElse(null) != null) {
            ctx.status(409);
            return;
        }

        try {
            Usuario usuario = new Usuario(nombre, apellido, email);
            usuario.setTelefono(telefono);
            usuario.setContrasenia(password);
            usuario.setRol(RepoRol.INSTANCE.buscarPorNombre(TipoRol.MIEMBRO));
            repoUsuario.agregar(usuario);
            loguear_atributos(ctx,usuario);

        } catch (Exception e) {
            ctx.status(400); // HTTP 400 Bad Request - Weak password
            ctx.result("Contraseña debil");
        }
    }

    public void loguear_atributos(Context ctx, Usuario usuario) {
        ctx.sessionAttribute("tipo_rol", usuario.getTipoRol().toString());
        ctx.sessionAttribute("usuario_id", usuario.getId().toString());
        ctx.redirect("/");
    }



}
