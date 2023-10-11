package controllers;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.comunidades.Membresia;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoPrestacion;
import models.repositorios.RepoRol;
import models.repositorios.RepoUsuario;
import models.servicios.PrestacionDeServicio;
import models.servicios.Servicio;
import models.usuario.Usuario;


import java.util.*;
import java.util.stream.Collectors;
@AllArgsConstructor
public class ComunidadController extends BaseController {


    private RepoComunidad repoComunidad;
    private RepoPrestacion repoPrestacion;


    public void index(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);
        List<Comunidad> comunidades = usuario.getComunidades();
        Map<String, Object> model = new HashMap<>();
        model.put("comunidades", comunidades);

        List<Comunidad> comunidadseQNoPretUsu = repoComunidad.buscarTodos().stream().
                filter(comunidad -> !usuario.getComunidades().contains(comunidad)).
                toList();
        model.put("comunidadesQNoPretUsu", comunidadseQNoPretUsu); //deberia estar en otra parte pero me da paja hacer otro hbs y en otra funcion aparte
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
        System.out.println("entre a crear comunidad");
        Map<String, Object> model = new HashMap<>();
        List<PrestacionDeServicio> servicios = repoPrestacion.buscarTodos();
        model.put("servicios", servicios);
        ctx.render("comunidades/crearComunidad.hbs", model);
    }

    public void save(Context ctx){

        Usuario usuario = usuarioLogueado(ctx);

        Comunidad comunidad = new Comunidad(ctx.formParam("nombre"));
        Membresia membresia = new Membresia(comunidad, usuario , new RepoRol().buscarPorNombre("Administrador de Comunidad"));
        comunidad.agregarMembresia(membresia);
        usuario.unirseAComunidad(membresia);

        List<String> servicioIds = ctx.formParams("prestaciones");//son las id de todos los servicios.

       // List<String> servicioIds = Arrays.asList(servicios.split(","));

        List<Long> idsLong = servicioIds.stream()
                .map(Long::parseLong)
                .toList();

        idsLong.forEach(id -> {
            // Realiza operaciones con "id" aquí, por ejemplo:

            PrestacionDeServicio prestacionDeServicio = repoPrestacion.buscar(id);
           if( prestacionDeServicio != null){
               comunidad.agregarServicioDeInteres(prestacionDeServicio);
           }
        });

        repoComunidad.agregar(comunidad);
        ctx.redirect("/comunidades");
    }

    public void show(Context ctx) {
        Comunidad comunidad = obtenerComunidad(ctx);
        Usuario usuario = usuarioLogueado(ctx);
        if (comunidad == null) {
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("comunidad", comunidad);
        model.put("membresia", usuario.getMembresia(comunidad));

        ctx.render("comunidades/comunidad.hbs", model);
    }

    public void unirse(Context ctx){
        Usuario usuario = usuarioLogueado(ctx);
        Long comunidadId = Long.parseLong(ctx.formParam("comunidad_id"));
        Comunidad comunidad = repoComunidad.buscar(comunidadId);
        if (comunidad == null) {
            ctx.status(404);
            ctx.result("Comunidad no encontrada");
            return;
        }
        Membresia membresia = new Membresia(comunidad, usuario , new RepoRol().buscarPorNombre("Miembro"));
        comunidad.agregarMembresia(membresia);
        usuario.unirseAComunidad(membresia);
        repoComunidad.modificar(comunidad);
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
