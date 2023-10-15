package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.converters.EstrategiaDeNotificacionConverter;
import models.converters.MedioPreferidoConverter;
import models.repositorios.RepoGenerico;
import models.repositorios.RepoUsuario;
import models.usuario.Interes;
import models.usuario.Usuario;
import models.usuario.configuraciones.formas.SinApuros;


import java.util.*;

@AllArgsConstructor
public class PerfilController extends BaseController{

    private RepoUsuario repoUsuario;


    public void index (Context ctx){
        Usuario usuario = usuarioLogueado(ctx);
        List<Comunidad> comunidades = usuario.getComunidades();
        List<Interes> intereses = usuario.getIntereses();
        List<Date> horarios = ((SinApuros) usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion()).getHorarios();
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", usuario);
        model.put("comunidades", comunidades);
        model.put("intereses", intereses);
        model.put("horarios", horarios);
        ctx.render("perfil/perfil.hbs", model);
    }

    public void save (Context ctx){
        Usuario usuario = usuarioLogueado(ctx);
        String nombre = ctx.formParam("nombre");
        String apellido = ctx.formParam("apellido");
        String email = ctx.formParam("email");
        String telefono = ctx.formParam("telefono");
        String medioDeNotificacion = ctx.formParam("medioNotificacion");
        String estrategiaDeNotificacion = ctx.formParam("cuandoEnviar");
        List<String> horarios = ctx.formParams("horarios");

        EstrategiaDeNotificacionConverter estrategiaDeNotificacionConverter = new EstrategiaDeNotificacionConverter();
        MedioPreferidoConverter medioPreferidoConverter = new MedioPreferidoConverter();


        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreoElectronico(email);
        usuario.setTelefono(telefono);
        usuario.getConfiguracionDeNotificaciones().setEstrategiaDeNotificacion(estrategiaDeNotificacionConverter.convertToEntityAttribute(estrategiaDeNotificacion));
        usuario.getConfiguracionDeNotificaciones().setMedioPreferido(medioPreferidoConverter.convertToEntityAttribute(medioDeNotificacion));

        if (!horarios.isEmpty() && estrategiaDeNotificacion.equals("SinApuros")) {
            SinApuros sinApuros = (SinApuros) usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion();
            for (String horario : horarios) {
                Date horarioDate = new Date();
                horarioDate.setHours(Integer.parseInt(horario.split(":")[0]));
                horarioDate.setMinutes(Integer.parseInt(horario.split(":")[1]));
                sinApuros.agregarHorario(horarioDate);
            }
        }

        repoUsuario.modificar(usuario);
        ctx.redirect("/perfil");


    }
}
