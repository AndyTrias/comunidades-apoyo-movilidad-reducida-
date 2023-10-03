package controllers;
import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.repositorios.RepoComunidad;
import models.servicios.Servicio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComunidadController {
    private RepoComunidad repoComunidad;

    public ComunidadController(RepoComunidad repoComunidad) {
        this.repoComunidad = repoComunidad;
    }

    public void index(Context ctx) {

        List<Comunidad> comunidades = repoComunidad.buscarTodos();
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
