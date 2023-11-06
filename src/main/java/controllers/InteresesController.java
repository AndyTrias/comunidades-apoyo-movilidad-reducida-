package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.dto.InteresDTO;
import models.entidades.Entidad;
import models.external.retrofit.apiServicio3.responseClases.IncidenteDTO;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoServicio;
import models.repositorios.RepoUsuario;
import models.servicios.Servicio;
import models.usuario.Interes;
import models.usuario.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class InteresesController extends BaseController{
    private RepoUsuario repoUsuario;
    private RepoEntidad repoEntidad;
    private RepoServicio repoServicio;

    public void index(Context ctx) {
        Usuario usuario = usuarioLogueado(ctx);
        List<Interes> intereses = usuario.getIntereses();
        List<Entidad> entidades = intereses.stream().map(Interes::getEntidad).toList();
        List<Servicio> servicios = intereses.stream().map(Interes::getServicio).toList();
        List<InteresDTO> intereses2 = new ArrayList<>();
        entidades.forEach(e -> {
            if (intereses2.stream().noneMatch(i -> i.getEntidad().equals(e))) {
                intereses2.add(new InteresDTO(e, new ArrayList<>()));
            }
        });

        Map<String, Object> model = new HashMap<>();
        model.put("intereses", intereses);
        model.put("entidades", entidades);
        model.put("servicios", servicios);
        ctx.render("generales/intereses.hbs", model);
    }
}
