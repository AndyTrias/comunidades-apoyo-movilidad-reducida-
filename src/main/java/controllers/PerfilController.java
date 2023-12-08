package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.configs.Config;
import models.converters.MedioPreferidoConverter;
import models.external.retrofit.georef.Georef;
import models.external.retrofit.georef.responseClases.Provincia;
import models.localizacion.Localizacion;
import models.repositorios.RepoLocalizacion;
import models.repositorios.RepoUsuario;
import models.usuario.Interes;
import models.usuario.Usuario;
import models.usuario.configuraciones.formas.SinApuros;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class PerfilController extends BaseController{

    private RepoUsuario repoUsuario;
    private RepoLocalizacion repoLocalizacion;


    public void index (Context ctx){
        Usuario usuario = usuarioLogueado(ctx);
        List<Comunidad> comunidades = usuario.getComunidades();
        List<Interes> intereses = usuario.getIntereses();
        Map<String, Object> model = new HashMap<>();
        model.put("usuario", usuario);
        model.put("comunidades", comunidades);
        model.put("intereses", intereses);
        model.put("API_GEOREF", Config.getInstance().API_GEOREF);
        model.put("provincia", usuario.getLocalizacion().obtenerProvincia());

        List<Provincia> provincias = Georef.getInstancia().listadoProvincias().provincias;

        provincias.removeIf(provincia ->
            provincia.equals(usuario.getLocalizacion().obtenerProvincia())
        );

        model.put("provincias", provincias);

        model.put("municipio", usuario.getLocalizacion().obtenerMunicipio());
        model.put("localidad", usuario.getLocalizacion().obtenerLocalidad());

        if (usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion() instanceof SinApuros) {
            List<Date> horarios = ((SinApuros) usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion()).getHorarios();
            model.put("horarios", horarios);
        }
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

        MedioPreferidoConverter medioPreferidoConverter = new MedioPreferidoConverter();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCorreoElectronico(email);
        usuario.setTelefono(telefono);
        usuario.getConfiguracionDeNotificaciones().setMedioPreferido(medioPreferidoConverter.convertToEntityAttribute(medioDeNotificacion));
        usuario.setLocalizacion(guardarLocalizacion(ctx));

        if (!horarios.isEmpty() && estrategiaDeNotificacion.equals("SinApuros")) {
            SinApuros sinApuros;
            if (usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion() instanceof SinApuros) {
                sinApuros = (SinApuros) usuario.getConfiguracionDeNotificaciones().getEstrategiaDeNotificacion();
            } else {
                sinApuros = new SinApuros();
            }

            for (String horario : horarios) {
                Date horarioDate = new Date();
                horarioDate.setHours(Integer.parseInt(horario.split(":")[0]));
                horarioDate.setMinutes(Integer.parseInt(horario.split(":")[1]));
                sinApuros.agregarHorario(horarioDate);
            }

            usuario.getConfiguracionDeNotificaciones().setEstrategiaDeNotificacion(sinApuros);
        }

        repoUsuario.modificar(usuario);
        ctx.redirect("/");
    }

    private Localizacion guardarLocalizacion(Context ctx) {
        String idProvincia = ctx.formParam("provincia");
        String idMunicipio = ctx.formParam("municipio");
        String idLocalidad = ctx.formParam("localidad");

        if (idProvincia == null || idProvincia.isEmpty()) {
            return null;
        }

        if (idMunicipio == null || idMunicipio.isEmpty()) {
            Localizacion localizacion = new Localizacion();
            localizacion.setUbicacionAsProvincia(Integer.parseInt(idProvincia));
            repoLocalizacion.agregarOModificar(localizacion);
            return localizacion;
        }

        if (idLocalidad == null || idLocalidad.isEmpty()) {
            Localizacion localizacion = new Localizacion();
            localizacion.setUbicacionAsMunicipio(Integer.parseInt(idMunicipio));
            repoLocalizacion.agregarOModificar(localizacion);
            return localizacion;
        }

        Localizacion localizacion = new Localizacion();
        localizacion.setUbicacionAsLocalidad(Long.parseLong(idLocalidad));
        repoLocalizacion.agregarOModificar(localizacion);
        return localizacion;
    }
}
