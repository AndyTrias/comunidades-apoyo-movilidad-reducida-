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



    public void show(Context ctx){
        Map<String, Object> model = new HashMap<>();
        model.put("administrador", true);
        ctx.render("admin/cargaMasiva.hbs", model);
    }
    public void cargaMasiva(Context ctx){
        LectorEntidadPrestadora lectorEntidadPrestadora = new LectorEntidadPrestadora();
        LectorOrganismoDeControl lectorOrganismoDeControl = new LectorOrganismoDeControl();

        UploadedFile uploadedFile = ctx.uploadedFile("archivo");

        if (uploadedFile == null) {
            ctx.redirect("/admin/cargaMasiva");
            return;
        }

        FileUtil.streamToFile(uploadedFile.content(), "src/main/resources/uploads/" + uploadedFile.filename());
        if (esEntidadPrestadora(uploadedFile.filename())){
            lectorEntidadPrestadora.leerCSV("src/main/resources/uploads/" + uploadedFile.filename());
            cargarEntidadesPrestadoras(lectorEntidadPrestadora.getEntidadesLeidas());

        } else if (esOrganismoDeControl(uploadedFile.filename())){
            lectorOrganismoDeControl.leerCSV("src/main/resources/uploads/" + uploadedFile.filename());
            cargarOrganismosDeControl(lectorOrganismoDeControl.getOrganismosLeidos());

        } else {
            ctx.redirect("/admin/cargaMasiva");
        }

        ctx.redirect("/admin/cargaMasiva");
    }

    private void cargarEntidadesPrestadoras(Map<EntidadPrestadora, Long> entidadPrestadoras){
        for (Map.Entry<EntidadPrestadora, Long> entidad : entidadPrestadoras.entrySet()){
            OrganismoDeControl organismo = repoOrganismoDeControl.buscar(entidad.getValue());
            if (organismo == null){
                throw new EntidadNoExistenteException("No existe el organismo de control con id: " + entidad.getValue());
            }
            organismo.agregarPrestadora(entidad.getKey());
            repoOrganismoDeControl.modificar(organismo);
        }
    }

    private void cargarOrganismosDeControl(Set<OrganismoDeControl> organismosDeControl){
        organismosDeControl.forEach(organismoDeControl -> {
            repoOrganismoDeControl.agregar(organismoDeControl);
        });
    }

    private boolean esEntidadPrestadora(String nombreArchivo){
        return nombreArchivo.contains("prestadora") || nombreArchivo.contains("entidad prestadora") || nombreArchivo.contains("entidades prestadoras");
    }

    private boolean esOrganismoDeControl(String nombreArchivo){
        return nombreArchivo.contains("organismo") || nombreArchivo.contains("organismos") || nombreArchivo.contains("control");
    }
}
