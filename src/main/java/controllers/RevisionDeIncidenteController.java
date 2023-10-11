package controllers;

import io.javalin.http.Context;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RevisionDeIncidenteController extends BaseController{

    public RevisionDeIncidenteController(){

    }

    public void show(Context ctx){
        Usuario usuario = usuarioLogueado(ctx);

        Map<String, Object> model = new HashMap<>();
        model.put("incidentesARevisar", usuario.getRevisionDeIncidentes());
        ctx.render("generales/revisionDeIncidentes.hbs", model);
    }
}
