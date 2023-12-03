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
import models.external.retrofit.apiServicio1.responseClases.FusionDTO;
import models.external.retrofit.apiServicio1.responseClases.PayloadDTO;
import models.external.retrofit.apiServicio3.ApiServicio3;
import models.external.retrofit.apiServicio3.responseClases.EntidadDTO;
import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import models.repositorios.*;
import server.exceptions.EntidadNoExistenteException;
import server.utils.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ApiServicioController {
    private RepoComunidad repoComunidad;
    private RepoFusion repoFusion;
    private RepoMembresia repoMembresia;
    private RepoEstablecimiento repoEstablecimiento;
    private RepoPrestacion repoPrestacion;
    private RepoEntidad repoEntidad;

    public void fusionDeComunidades(Context ctx) {

        List<Comunidad> comunidades = ctx.formParams("comunidades").stream()
            .filter(id -> id.length() < 6)
            .map(id -> repoComunidad.buscar(Long.parseLong(id)))
            .collect(Collectors.toList());

        List<Fusion> fusiones = repoFusion.buscarTodos();
        List<Establecimiento> establecimientos = repoEstablecimiento.buscarTodos();

        List<ComunidadDTO> comunidadDTOS = Mapper.mapComunidadesToComunidadesDTO(comunidades, establecimientos);
        List<FusionDTO> fusionesDTO = Mapper.mapFusionesToFusionesDTO(fusiones, establecimientos);
        PayloadDTO payloadDTO = new PayloadDTO(comunidadDTOS, fusionesDTO);

        try {
            PayloadDTO response = ApiServicio1.getInstancia().comunidadesYFusiones(payloadDTO);
            asignarComunidades(response.getComunidades());
            asignarFusiones(response.getFusiones());
            ctx.redirect("/admin/config");
        } catch (Exception e) {
            throw new EntidadNoExistenteException("No se pudo conectar con el servicio de fusion");
        }
    }

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
            repoFusion.modificar(fusionBuscada);

        });
    }
}
