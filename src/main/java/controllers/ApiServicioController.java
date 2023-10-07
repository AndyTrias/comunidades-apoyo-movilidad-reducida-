package controllers;

import io.javalin.http.Context;
import lombok.AllArgsConstructor;
import models.comunidades.Comunidad;
import models.comunidades.Fusion;
import models.comunidades.Membresia;
import models.entidades.Establecimiento;
import models.external.apiServicio.ApiServicio;
import models.external.apiServicio.responseClases.ComunidadDTO;
import models.external.apiServicio.responseClases.FusionDTO;
import models.external.apiServicio.responseClases.PayloadDTO;
import models.repositorios.*;
import models.servicios.PrestacionDeServicio;
import server.utils.Mapper;
import server.utils.PrettyProperties;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class ApiServicioController {
    private RepoComunidad repoComunidad;
    private RepoFusion repoFusion;
    private RepoMembresia repoMembresia;
    private RepoEstablecimiento repoEstablecimiento;
    private RepoPrestacion repoPrestacion;

    public void fusionDeComunidades(Context ctx){
        List<Comunidad> comunidades = repoComunidad.buscarTodos();
        List<Fusion> fusiones = repoFusion.buscarTodos();
        List<Establecimiento> establecimientos = repoEstablecimiento.buscarTodos();

        List<ComunidadDTO> comunidadDTOS = Mapper.mapComunidadesToComunidadesDTO(comunidades, establecimientos);
        List<FusionDTO> fusionesDTO = Mapper.mapFusionesToFusionesDTO(fusiones, establecimientos);
        PayloadDTO payloadDTO = new PayloadDTO(comunidadDTOS, fusionesDTO);

        try {
            PayloadDTO response = ApiServicio.getInstancia().comunidadesYFusiones(payloadDTO);
            asignarComunidades(response.getComunidades());
            asignarFusiones(response.getFusiones());
        } catch (Exception e) {
            e.printStackTrace();
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
            repoFusion.agregar(fusion);
        });
    }
}
