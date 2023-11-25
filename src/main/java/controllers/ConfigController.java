package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.configs.Config;
import models.repositorios.RepoConfig;

import java.util.HashMap;
import java.util.Map;

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
        ctx.render("admin/configuracion.hbs", model);
    }
    public void save(Context ctx){
        String latitud = ctx.formParam("latitud");
        String longitud = ctx.formParam("longitud");
        assert latitud != null;
        Config.getInstance().LATITUD_MAXIMA = Double.parseDouble(latitud);
        assert longitud != null;
        Config.getInstance().LONGITUD_MAXIMA = Double.parseDouble(longitud);

        repoConfig.modificar(Config.getInstance());
        ctx.redirect("/config");
    }
}
