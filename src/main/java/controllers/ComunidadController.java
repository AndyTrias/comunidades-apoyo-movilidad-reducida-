package controllers;
import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoUsuario;
import models.servicios.Servicio;
import models.usuario.Usuario;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ComunidadController {
    private RepoComunidad repoComunidad;
    private RepoUsuario repoUsuario;

    public ComunidadController(RepoComunidad repoComunidad, RepoUsuario repoUsuario) {
        this.repoComunidad = repoComunidad;
        this.repoUsuario = repoUsuario;
    }

    public void index(Context ctx) {
        Long usuarioId = Long.valueOf(Objects.requireNonNull(ctx.cookie("usuario_id")));
        Usuario usuario = repoUsuario.buscar(usuarioId);
        List<Comunidad> comunidades = usuario.getComunidades();
        Map<String, Object> model = new HashMap<>();
        model.put("comunidades", comunidades);
        ctx.render("comunidades/comunidades.hbs", model);
    }


    public void delete(Context ctx) {
        Long comunidadId = Long.parseLong(ctx.pathParam("id"));
        Comunidad comunidad = repoComunidad.buscar(comunidadId);
        if (comunidad == null) {
            ctx.status(404);
            ctx.result("Comunidad no encontrada");
            return;
        }

        repoComunidad.eliminar(comunidad);
        ctx.status(204);
    }



    public void create(Context ctx) {
        Comunidad comunidad = obtenerComunidad(ctx);
        if (comunidad == null) {
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);

        //    ctx.render("incidentes/incidente.hbs", model);
    }

    private Comunidad obtenerComunidad(Context ctx) {
        Long comunidad_id = Long.parseLong(ctx.pathParam("id"));
        Comunidad comunidad = repoComunidad.buscar(comunidad_id);

        if (comunidad == null) {
            ctx.status(404);
            ctx.result("Comunidad no encontrada");
            return null;
        }

        return comunidad;
    }


}
