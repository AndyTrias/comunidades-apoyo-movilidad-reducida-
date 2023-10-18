package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.incidentes.Incidente;
import models.incidentes.RevisionDeIncidente;
import models.localizacion.UbicacionExacta;
import models.repositorios.RepoIncidentes;
import models.repositorios.RepoUsuario;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class RevisionDeIncidenteController extends BaseController {

    private RepoIncidentes repoIncidentes;
    private RepoUsuario repoUsuario;

    public void show(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);

        Map<String, Object> model = new HashMap<>();
        model.put("incidenteARevisar", usuario.getRevisionDeIncidentes());
        ctx.render("generales/revisionDeIncidentes.hbs", model);
    }

    public void showIncidente(Context ctx) {
        ctx.render("generales/revisionDeUnIncidente.hbs");
    }

    public void postUbicacionExacta(Context ctx){

        Usuario usuario = usuarioLogueado(ctx);
        double latitud = Double.valueOf(ctx.formParam("latitud"));
        double longitud = Double.valueOf(ctx.formParam("longitud"));

        UbicacionExacta ubicacion = new UbicacionExacta(latitud, longitud);
        usuario.setUbicacionExacta(ubicacion);

        List<Incidente> incidentes = repoIncidentes.buscarTodos();
        RevisionDeIncidente.comprobarCercania(usuario, incidentes);

        repoUsuario.modificar(usuario);
    }

}


