package controllers;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import models.entidades.EntidadPrestadora;
import models.entidades.OrganismoDeControl;
import models.readerCSV.LectorEntidadPrestadora;
import models.readerCSV.LectorOrganismoDeControl;
import models.repositorios.RepoEntidadPrestadora;
import models.repositorios.RepoOrganismoDeControl;

import java.util.List;
import java.util.Set;

public class CargaMasivaController {
    private RepoEntidadPrestadora repoEntidadPrestadora;
    private RepoOrganismoDeControl repoOrganismoDeControl;

    public CargaMasivaController(RepoEntidadPrestadora repoEntidadPrestadora, RepoOrganismoDeControl repoOrganismoDeControl){
        this.repoEntidadPrestadora = repoEntidadPrestadora;
        this.repoOrganismoDeControl = repoOrganismoDeControl;
    }

    public void show(Context ctx){
        ctx.render("generales/cargaMasiva.hbs");
    }
    public void cargaMasiva(Context ctx){
        LectorEntidadPrestadora lectorEntidadPrestadora = new LectorEntidadPrestadora();
        LectorOrganismoDeControl lectorOrganismoDeControl = new LectorOrganismoDeControl();

        UploadedFile uploadedFile = ctx.uploadedFile("archivo");

        if (uploadedFile == null) {
            ctx.redirect("/cargaMasiva");
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
            ctx.redirect("/cargaMasiva");
        }

        ctx.redirect("/cargaMasiva");
    }

    private void cargarEntidadesPrestadoras(Set<EntidadPrestadora> entidadPrestadoras){
        entidadPrestadoras.forEach(entidadPrestadora -> {
            repoEntidadPrestadora.agregar(entidadPrestadora);
        });
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
