package controllers;

import controllers.factories.FactoryController;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.configs.Config;
import models.repositorios.RepoConfig;
import server.init.Initializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class ConfigController {
    private RepoConfig repoConfig;

    public void show(Context ctx){
        Config config = Config.getInstance();

        double latitud = Double.parseDouble(String.valueOf(config.LATITUD_MAXIMA));
        double longitud = Double.parseDouble(String.valueOf(config.LONGITUD_MAXIMA));
        Map<String, Object> model = new HashMap<>();
        model.put("latitud", latitud);
        model.put("longitud", longitud);
        model.put("frecuencia", config.FRECUENCIA_RANKING);
        model.put("unidad", config.UNIDAD_FRECUENCIA_RANKING);
        model.put("API_SERVICIO1", System.getenv("API_SERVICIO1"));
        ctx.render("admin/configuracion.hbs", model);
    }
    public void save(Context ctx){
        String latitud = ctx.formParam("latitud");
        String longitud = ctx.formParam("longitud");
        String frecuencia = ctx.formParam("frecuencia");
        String unidad = ctx.formParam("unidadFrecuencia");
        assert latitud != null;
        Config.getInstance().LATITUD_MAXIMA = Double.parseDouble(latitud);
        assert longitud != null;
        Config.getInstance().LONGITUD_MAXIMA = Double.parseDouble(longitud);
        assert frecuencia != null;
        Config.getInstance().FRECUENCIA_RANKING = Integer.parseInt(frecuencia);
        assert unidad != null;
        Config.getInstance().UNIDAD_FRECUENCIA_RANKING = getTimeUnit(unidad).name();

        repoConfig.modificar(Config.getInstance());
        Initializer.activarProcesos();

        ctx.redirect("/admin/config");
    }

    private TimeUnit getTimeUnit(String unidad){
        return switch (unidad) {
            case "MINUTES" -> TimeUnit.MINUTES;
            case "HOURS" -> TimeUnit.HOURS;
            case "DAYS" -> TimeUnit.DAYS;
            case "SECONDS" -> TimeUnit.SECONDS;
            default -> TimeUnit.MINUTES;
        };
    }
}
