package controllers;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import lombok.AllArgsConstructor;
import models.entidades.EntidadPrestadora;
import models.entidades.OrganismoDeControl;
import models.readerCSV.LectorEntidadPrestadora;
import models.readerCSV.LectorOrganismoDeControl;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoOrganismoDeControl;
import server.exceptions.EntidadNoExistenteException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class CargaMasivaController {
  private RepoEntidadPrestadora repoEntidadPrestadora;
  private RepoOrganismoDeControl repoOrganismoDeControl;


  public void show(Context ctx) {
    Map<String, Object> model = new HashMap<>();
    model.put("administrador", true);
    ctx.render("admin/cargaMasiva.hbs", model);
  }

  public void cargaEntidades(Context ctx) {
    LectorEntidadPrestadora lectorEntidadPrestadora = new LectorEntidadPrestadora();

    UploadedFile uploadedFile = obtenerArchivo(ctx);
    lectorEntidadPrestadora.leerCSV("src/main/resources/uploads/" + uploadedFile.filename());
    Map<EntidadPrestadora, Long> entidades = lectorEntidadPrestadora.getEntidadesLeidas();


    for (Map.Entry<EntidadPrestadora, Long> entidad : entidades.entrySet()) {
      OrganismoDeControl organismo = repoOrganismoDeControl.buscar(entidad.getValue());
      if (organismo == null) {
        throw new EntidadNoExistenteException("No existe el organismo de control con id: " + entidad.getValue());
      }
      organismo.agregarPrestadora(entidad.getKey());
      repoOrganismoDeControl.modificar(organismo);
    }

    ctx.redirect("/admin/cargaMasiva");
  }

  public void cargaOrganismos(Context ctx) {
    LectorOrganismoDeControl lectorOrganismoDeControl = new LectorOrganismoDeControl();

    UploadedFile uploadedFile = obtenerArchivo(ctx);
    lectorOrganismoDeControl.leerCSV("src/main/resources/uploads/" + uploadedFile.filename());

    for (OrganismoDeControl organismo : lectorOrganismoDeControl.getOrganismosLeidos()) {
      repoOrganismoDeControl.agregar(organismo);
    }

    ctx.redirect("/admin/cargaMasiva");
  }

  private UploadedFile obtenerArchivo(Context ctx) {
    UploadedFile uploadedFile = ctx.uploadedFile("archivo");

    if (uploadedFile == null) {
      ctx.redirect("/admin/cargaMasiva");
      throw new EntidadNoExistenteException("No se ha cargado ningun archivo");
    }

    FileUtil.streamToFile(uploadedFile.content(), "src/main/resources/uploads/" + uploadedFile.filename());

    return uploadedFile;
  }
}
