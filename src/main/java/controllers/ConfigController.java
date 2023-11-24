package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.configs.Config;
import models.repositorios.RepoConfig;

@AllArgsConstructor
public class ConfigController {
    private RepoConfig repoConfig;

    public void save(Context ctx){
        String latitud = ctx.formParam("latitud");
        String longitud = ctx.formParam("longitud");
        assert latitud != null;
        Config.getInstance().LATITUD_MAXIMA = Double.parseDouble(latitud);
        assert longitud != null;
        Config.getInstance().LONGITUD_MAXIMA = Double.parseDouble(longitud);

        repoConfig.modificar(Config.getInstance());
    }
}
