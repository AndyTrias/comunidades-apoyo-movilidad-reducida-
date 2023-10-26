package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.TipoRol;
import models.repositorios.RepoRol;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;
import server.exceptions.CredencialesInvalidaException;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class AuthController {
    private RepoUsuario repoUsuario;

    public void showLogin(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("notlogged", true);
        ctx.render("auth/login.hbs", model);

    }
    public void showRegister(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("notlogged", true);
        ctx.render("auth/register.hbs", model);
    }

    public void login(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        Usuario usuario = repoUsuario.buscarPorEmail(email).orElse(null);

        if (usuario == null || !usuario.getContrasenia().equals(password)) {
            throw new CredencialesInvalidaException("Usuario o contrasenia Incorrectos");
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
            throw new CredencialesInvalidaException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario(nombre, apellido, email);
        usuario.setTelefono(telefono);
        usuario.setContrasenia(password);
        usuario.setRol(RepoRol.INSTANCE.buscarPorNombre(TipoRol.MIEMBRO));
        repoUsuario.agregar(usuario);

        loguear_atributos(ctx,usuario);


    }

    public void loguear_atributos(Context ctx, Usuario usuario) {
        ctx.sessionAttribute("tipo_rol", usuario.getTipoRol().toString());
        ctx.sessionAttribute("usuario_id", usuario.getId().toString());
        ctx.redirect("/");
    }



}
