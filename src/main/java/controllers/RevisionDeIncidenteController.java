package controllers;

import io.javalin.http.Context;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RevisionDeIncidenteController {
    private RepoUsuario repoUsuario;

    public RevisionDeIncidenteController(RepoUsuario repoUsuario){
        this.repoUsuario = repoUsuario;
    }

    public void show(Context ctx){
        Long usuarioId = Long.valueOf(Objects.requireNonNull(ctx.cookie("usuario_id")));
        Usuario usuario = repoUsuario.buscar(usuarioId);

        Map<String, Object> model = new HashMap<>();
        model.put("incidenteARevisar", usuario.getRevisionDeIncidentes());
        ctx.render("generales/revisionDeIncidentes.hbs", model);
    }
}
