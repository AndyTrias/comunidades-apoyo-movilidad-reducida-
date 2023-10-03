package controllers;
import io.javalin.http.Context;
import models.comunidades.Comunidad;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoIncidenteDeComunidad;
import models.repositorios.RepoIncidentes;
import models.repositorios.RepoPrestacion;
import models.usuario.Usuario;
import models.servicios.PrestacionDeServicio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
public class comunidadController {
    private RepoComunidad repoComunidad;

    public comunidadController(RepoComunidad repoComunidad) {
        this.repoComunidad = repoComunidad;
    }//LO HACE POR INYECCION

    public void index(Context ctx) {

        List<Comunidad> comunidades = repoComunidad.buscarTodos();
        ctx.json(comunidades);
        //ctx.render("comunidades/index.hsb", Map.of("comunidades", comunidades));
    }

    public void show(Context ctx) {
        Long comunidadId = Long.parseLong(ctx.pathParam("id"));
        Comunidad comunidad = repoComunidad.buscar(comunidadId);
        if (comunidad == null) {
            ctx.status(404);
            ctx.result("Comunidad no encontrada");
        } else {
            ctx.json(comunidad);
        }
    }

    //ME PARECE QUE TENEMOS QUE TENER OTRA PAGINA PARA CREAR UNA COMUNIDAD para tener de donde tomar
    //los datos para crear una comunidad



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
