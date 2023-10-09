package controllers;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoUsuario;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;


import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ComunidadController {
    private RepoComunidad repoComunidad;
    private RepoUsuario repoUsuario;
    private RepoPrestacion repoPrestacion;

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
        //List<PrestacionDeServicio> servicios = repoPrestacion.buscarTodos();
        ctx.render("incidentes/crearComunidad.hbs", model);
    }

    public void save(Context ctx){
        Comunidad comunidad = new Comunidad();
        comunidad.setNombre(ctx.formParam("nombre"));
        String servicios = ctx.formParam("prestaciones[]");//son las id de todos los servicios.

        List<String> servicioIds = Arrays.asList(servicios.split(","));

        List<Long> idsLong = servicioIds.stream()
                .map(Long::parseLong)
                .toList();

        idsLong.forEach(id -> {
            // Realiza operaciones con "id" aquí, por ejemplo:

           if(repoPrestacion.buscar(id) != null){
               comunidad.agregarServicioDeInteres(repoPrestacion.buscar(id));
           }
        });

        repoComunidad.agregar(comunidad);
        ctx.redirect("/comunidades");
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
