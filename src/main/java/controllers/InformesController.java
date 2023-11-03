package controllers;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.entidades.Entidad;
import models.entidades.OrganismoDeControl;
import models.external.json.ServicioJson;
import models.rankings.criterios.MayorCantidad;
import models.rankings.criterios.MayorTiempo;
import models.rankings.estrategiaDeExportacion.EstrategiaDeExportacion;
import models.rankings.estrategiaDeExportacion.ExportarAJson;
import models.rankings.informes.Exportador;
import models.rankings.informes.GeneradorDeInformes;
import models.rankings.informes.Informe;
import models.repositorios.RepoEntidad;
import models.repositorios.RepoInformes;
import models.repositorios.RepoOrganismoDeControl;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;


@AllArgsConstructor
public class InformesController extends BaseController {



    private RepoEntidad repoEntidad;
    private RepoInformes repoInformes;
    private RepoOrganismoDeControl repoOrganismoDeControl;

   public void mostarEntidadesDeCadaRankings(Context ctx){
       OrganismoDeControl organismoDeControlrol = repoOrganismoDeControl.buscarPorUsarioDesignado(usuarioLogueado(ctx).getId());
       Map<String, Object> model = new HashMap<>();
       try {
           // Lectura del archivo JSON
           JsonParser parser = new JsonParser();
           Informe informe = repoInformes.buscar(Long.valueOf(ctx.formParam("id")));
           JsonElement jsonElement = parser.parse(new FileReader(informe.getPath())); // Reemplaza con la ruta correcta
           JsonArray jsonArray = jsonElement.getAsJsonArray();

           // Creación del mapa para almacenar el nombre del criterio y la lista de entidades
           Map<String, List<Entidad>> mapCriterioEntidades = new HashMap<>();

           // Iterar sobre el JSONArray y procesar los datos
           for (JsonElement element : jsonArray) {
               JsonObject jsonObject = element.getAsJsonObject();
               String criterio = jsonObject.get("Criterio").getAsString();
               JsonArray rankingArray = jsonObject.get("Ranking").getAsJsonArray();

               List<Entidad> entidades = new ArrayList<>();
               for (JsonElement rankElement : rankingArray) {
                   String[] ids = rankElement.getAsString().split(",\\s*");
                   for (String id : ids) {
                       Long entityId = Long.parseLong(id);
                       Entidad entidad = repoEntidad.buscar(entityId);
                       if ( perteneceEntidadAOrganismoDeControl(organismoDeControlrol,entidad)){
                           entidades.add(entidad);
                       }
                   }
               }

               mapCriterioEntidades.put(criterio, entidades);
               model.put("mapCriterioEntidades", mapCriterioEntidades);
           }

       } catch (Exception e) {
           e.printStackTrace();
       }





   }



 public boolean perteneceEntidadAOrganismoDeControl(OrganismoDeControl organismo, Entidad entidad) {
     return organismo.getEntidadesQuePosee().contains(entidad);
 }


    public void generarRankings() {
        EstrategiaDeExportacion estrategia = new ExportarAJson(new ServicioJson());

        GeneradorDeInformes generadorDeInformes = new GeneradorDeInformes();
        generadorDeInformes.agregarCriterioDeEntidad(new MayorTiempo("Tiempo de resolucion"));
        generadorDeInformes.agregarCriterioDeEntidad(new MayorCantidad("Cantidad de incidentes"));
        String rutaArchivo = "ranking_" + LocalDate.now() + ".json";

        Exportador exportador = new Exportador(generadorDeInformes, estrategia);
        exportador.exportarConEstrategia(repoEntidad.buscarTodos(), rutaArchivo);
        Informe informe = new Informe(new Date(), rutaArchivo);
        repoInformes.agregar(informe);
    }

}
