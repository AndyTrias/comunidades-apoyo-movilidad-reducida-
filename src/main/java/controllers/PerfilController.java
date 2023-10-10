package controllers;

import io.javalin.http.Context;
import models.repositorios.RepoGenerico;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PerfilController {

    private RepoUsuario repoUsuario;

    public void index (Context ctx){
        Long usuarioId = Long.valueOf(Objects.requireNonNull(ctx.cookie("usuario_id")));
        Usuario usuario = repoUsuario.buscar(usuarioId);
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", usuario);
        ctx.render("perfil/perfil.hbs", model);
    }

    public void save (Context ctx){
        Long usuarioId = Long.valueOf(Objects.requireNonNull(ctx.cookie("usuario_id")));
        Usuario usuario = repoUsuario.buscar(usuarioId);
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String email = ctx.formParam("email");
        String telefono = ctx.formParam("telefono");

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreoElectronico(email);
        usuario.setTelefono(telefono);
        repoUsuario.modificar(usuario);
        ctx.redirect("/perfil");


    }
}
