package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.incidentes.Incidente;
import models.incidentes.RevisionDeIncidente;
import models.localizacion.UbicacionExacta;
import models.repositorios.RepoComunidad;
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
    private RepoComunidad repoComunidad;

    public void show(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);

        Map<String, Object> model = new HashMap<>();
        model.put("incidentesARevisar", usuario.getRevisionDeIncidentes());
        ctx.render("generales/revisionDeIncidentes.hbs", model);
    }

    public void showIncidente(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);
        Incidente incidente = usuario.getRevisionDeIncidente(Long.parseLong(ctx.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("incidente", incidente);
        ctx.render("generales/revisionDeUnIncidente.hbs", model);
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

    public void resolucionDeIncidente(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);
        Incidente incidente = usuario.getRevisionDeIncidente(Long.parseLong(ctx.pathParam("id")));
        String si = ctx.formParam("Si");
        String no = ctx.formParam("No");
        if (si != null) {
            List<Comunidad> comunidades = repoComunidad.buscarTodosPorIncidente(incidente);
            comunidades.forEach(comunidad -> {
                comunidad.cerrarIncidente(incidente, usuario);
                repoComunidad.modificar(comunidad);
            });
            usuario.getRevisionDeIncidentes().remove(incidente);
            repoUsuario.modificar(usuario);
        } else if (no != null) {
            usuario.getRevisionDeIncidentes().remove(incidente);
            repoUsuario.modificar(usuario);
        }
        ctx.redirect("/revisionDeIncidentes");
    }

}


