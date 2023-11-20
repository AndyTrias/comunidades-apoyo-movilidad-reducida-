package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.entidades.Entidad;
import models.incidentes.Incidente;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoInteres;
import models.repositorios.RepoServicio;
import models.repositorios.RepoUsuario;
import models.servicios.Servicio;
import models.usuario.Interes;
import models.usuario.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class InteresesController extends BaseController{
    private RepoInteres repoInteres;
    private RepoUsuario repoUsuario;
    private RepoEntidad repoEntidad;
    private RepoServicio repoServicio;

    public void index(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);
        List<Interes> intereses = usuario.getIntereses();
        List<Entidad> entidades = repoEntidad.buscarTodos();
        List<Servicio> servicios = repoServicio.buscarTodos();
        List<Incidente> incidentes = usuario.getIncidentesDeInteres();

        Map<String, Object> model = new HashMap<>();
        model.put("intereses", intereses);
        model.put("entidades", entidades);
        model.put("servicios", servicios);
        model.put("incidentes", incidentes);
        ctx.render("generales/intereses.hbs", model);
    }

    public void save(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);
        Long idEntidad = Long.parseLong(Objects.requireNonNull(ctx.formParam("entidad")));
        Long idServicio = Long.parseLong(Objects.requireNonNull(ctx.formParam("servicio")));
        Entidad entidad = repoEntidad.buscar(idEntidad);
        Servicio servicio = repoServicio.buscar(idServicio);
        Interes interes = new Interes();
        interes.setEntidad(entidad);
        interes.setServicio(servicio);
        usuario.agregarInteres(interes);
        repoUsuario.modificar(usuario);
        ctx.redirect("/intereses");
    }

    public void delete(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Interes interes = repoInteres.buscar(id);
        repoInteres.eliminar(interes);
    }
}
