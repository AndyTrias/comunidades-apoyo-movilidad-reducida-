package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.comunidades.Fusion;
import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.external.retrofit.apiServicio1.ApiServicio1;
import models.external.retrofit.apiServicio1.responseClases.ComunidadDTO;
import models.external.retrofit.apiServicio1.responseClases.EstadoFusion;
import models.external.retrofit.apiServicio1.responseClases.FusionDTO;
import models.external.retrofit.apiServicio1.responseClases.PayloadDTO;
import models.external.retrofit.apiServicio3.ApiServicio3;
import models.external.retrofit.apiServicio3.responseClases.EntidadDTO;
import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import models.repositorios.*;
import server.exceptions.EntidadNoExistenteException;
import server.utils.Mapper;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ApiServicioController {
    private RepoComunidad repoComunidad;
    private RepoFusion repoFusion;
    private RepoMembresia repoMembresia;
    private RepoEstablecimiento repoEstablecimiento;
    private RepoPrestacion repoPrestacion;
    private RepoEntidad repoEntidad;

    public void show(Context ctx) {
        List<Comunidad> comunidades = repoComunidad.buscarTodos();
        List<Fusion> fusiones = repoFusion.buscarNoRealizadas();

        Map<String, Object> model = new HashMap<>();
        model.put("comunidades", comunidades);
        model.put("fusiones", fusiones);

        ctx.render("comunidades/fusion.hbs", model);
    }

    public void sugerirComunidades(Context ctx){
        List<String> ids = ctx.formParams("comunidades");

        List<Comunidad> comunidades = ids.stream().map(id -> repoComunidad.buscar(Long.parseLong(id))).toList();
        List<Fusion> fusiones = new ArrayList<>();
        List<Establecimiento> establecimientos = repoEstablecimiento.buscarTodos();

        llamarALaApi(comunidades, fusiones, establecimientos);
        ctx.redirect("/admin/sugerenciaDeFusion");
    }

    public void fusionarComunidades(Context ctx){
        List<String> ids = ctx.formParams("fusiones");

        List<Fusion> fusiones = ids.stream().map(id -> repoFusion.buscar(Long.parseLong(id))).toList();
        List<Comunidad> comunidades = new ArrayList<>(fusiones.stream().map(Fusion::getComunidad1).toList());
        comunidades.addAll(fusiones.stream().map(Fusion::getComunidad2).toList());
        fusiones.forEach(f -> {
            f.setEstado(EstadoFusion.ACEPTADA);
            f.setRealizada(true);
        });
        List<Establecimiento> establecimientos = repoEstablecimiento.buscarTodos();

        llamarALaApi(comunidades, fusiones, establecimientos);
        ctx.redirect("/admin/sugerenciaDeFusion");
    }

    private void llamarALaApi(List<Comunidad> comunidades, List<Fusion> fusiones, List<Establecimiento> establecimientos) {
        List<ComunidadDTO> comunidadDTOS = Mapper.mapComunidadesToComunidadesDTO(comunidades, establecimientos);
        List<FusionDTO> fusionesDTO = Mapper.mapFusionesToFusionesDTO(fusiones, establecimientos);
        PayloadDTO payloadDTO = new PayloadDTO(comunidadDTOS, fusionesDTO);

        try {
            PayloadDTO response = ApiServicio1.getInstancia().comunidadesYFusiones(payloadDTO);
            asignarFusiones(response.getFusiones());
            asignarComunidades(response.getComunidades());
        } catch (Exception e) {
            fusiones.forEach(f -> {
                f.setEstado(EstadoFusion.PROPUESTA);
                f.setRealizada(false);
            });
            e.printStackTrace();
            throw new EntidadNoExistenteException("No se pudo conectar con el servicio de fusion");
        }
    }

    /*public void fusionDeComunidades(Context ctx) {
        List<Comunidad> comunidades = repoComunidad.buscarTodos();

        List<Fusion> fusiones = repoFusion.buscarNoRealizadas();
        List<Establecimiento> establecimientos = repoEstablecimiento.buscarTodos();

        List<ComunidadDTO> comunidadDTOS = Mapper.mapComunidadesToComunidadesDTO(comunidades, establecimientos);
        List<FusionDTO> fusionesDTO = Mapper.mapFusionesToFusionesDTO(fusiones, establecimientos);
        PayloadDTO payloadDTO = new PayloadDTO(comunidadDTOS, fusionesDTO);

        try {
            PayloadDTO response = ApiServicio1.getInstancia().comunidadesYFusiones(payloadDTO);
            asignarComunidades(response.getComunidades());
            asignarFusiones(response.getFusiones());
            ctx.redirect("/comunidades");
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntidadNoExistenteException("No se pudo conectar con el servicio de fusion");
        }
    }*/

    private void asignarComunidades(List<ComunidadDTO> comunidades){
        comunidades.forEach(c -> {
            if (c.getEstado() == ComunidadDTO.EstadoComunidad.DESACTIVADA) {
                repoComunidad.eliminar(repoComunidad.buscar(Long.parseLong(String.valueOf(c.getId()))));
            }
            if (c.getId() == null) {
                Comunidad comunidad = Mapper.mapComunidadDTOToComunidad(c, repoMembresia, repoPrestacion, repoComunidad);
                repoComunidad.agregar(comunidad);
            }
        });
    }

    private void asignarFusiones(List<FusionDTO> fusiones){
        fusiones.forEach(f -> {
            Fusion fusion = Mapper.mapFusionDTOToFusion(f, repoMembresia, repoPrestacion, repoComunidad);
            Fusion fusionBuscada = repoFusion.buscarPorComunidades(fusion).orElse(null);
            if (fusionBuscada == null) {
                repoFusion.agregar(fusion);
                return;
            }
            fusionBuscada.setEstado(fusion.getEstado());
            fusionBuscada.setFechaCreada(fusion.getFechaCreada());
            if (fusionBuscada.getEstado() == EstadoFusion.ACEPTADA) {
                fusionBuscada.setRealizada(true);
            }
            repoFusion.modificar(fusionBuscada);

        });
    }
}
