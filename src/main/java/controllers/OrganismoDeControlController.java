package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.entidades.Entidad;
import models.entidades.EntidadPrestadora;
import models.entidades.OrganismoDeControl;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoInformes;
import models.repositorios.RepoOrganismoDeControl;
import server.exceptions.EntidadNoExistenteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@AllArgsConstructor
public class OrganismoDeControlController extends BaseController {
  private RepoOrganismoDeControl repoOrganismoDeControl;
  private RepoInformes repoInformes;

  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("organismoDeControl", true);
    model.put("organismo", repoOrganismoDeControl.buscarPorUsarioDesignado(usuarioLogueado(ctx).getId()));
    model.put("rankings", repoInformes.buscarTodos());
    ctx.render("rankings/rankings.hbs", model);
  }

}
