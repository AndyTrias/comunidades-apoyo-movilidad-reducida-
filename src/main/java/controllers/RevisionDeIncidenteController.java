package controllers;

import io.javalin.http.Context;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;

public class RevisionDeIncidenteController extends BaseController {

    public RevisionDeIncidenteController() {

    }

    public void show(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);

        Map<String, Object> model = new HashMap<>();
        model.put("incidenteARevisar", usuario.getRevisionDeIncidentes());
        ctx.render("generales/revisionDeIncidentes.hbs", model);
    }

    public void showIncidente(Context ctx) {
        ctx.render("generales/revisionDeUnIncidente.hbs");
    }

}


