package controllers;

import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.repositorios.RepoGenerico;
import models.repositorios.RepoUsuario;
import models.usuario.Interes;
import models.usuario.Usuario;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PerfilController extends BaseController{

    private RepoUsuario repoUsuario;

    public PerfilController() {
    }

    public void index (Context ctx){
        Usuario usuario = usuarioLogueado(ctx);
        List<Comunidad> comunidades = usuario.getComunidades();
        List<Interes> intereses= usuario.getIntereses();
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", usuario);
        model.put("comunidades", comunidades);
        model.put("intereses", intereses);
        ctx.render("perfil/perfil.hbs", model);
    }

    public void save (Context ctx){
        Usuario usuario = usuarioLogueado(ctx);
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
