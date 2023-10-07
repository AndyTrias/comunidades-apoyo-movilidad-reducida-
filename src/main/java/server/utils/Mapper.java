package server.utils;

import models.comunidades.Comunidad;
import models.comunidades.Fusion;
import models.entidades.Establecimiento;
import models.external.apiServicio.responseClases.ComunidadDTO;
import models.external.apiServicio.responseClases.FusionDTO;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoMembresia;
import models.repositorios.RepoPrestacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mapper {
    public static List<ComunidadDTO> mapComunidadesToComunidadesDTO(List<Comunidad> comunidad, List<Establecimiento> establecimientos) {
        List<ComunidadDTO> comunidadesDTO = new ArrayList<>();
        for (Comunidad c : comunidad) {
            comunidadesDTO.add(mapComunidadToComunidadDTO(c, establecimientos));
         }
        return comunidadesDTO;
    }

    public static ComunidadDTO mapComunidadToComunidadDTO(Comunidad c, List<Establecimiento> establecimientos) {
        ComunidadDTO comunidadDTO = new ComunidadDTO();
        comunidadDTO.setId(Integer.valueOf(String.valueOf(c.getId())));
        comunidadDTO.setIdEstablecimientoObservados(c.getIdEstablecimientoObservados(establecimientos));
        comunidadDTO.setIdServiciosObservados(c.getIdServiciosObservados());
        comunidadDTO.setGradoDeConfianza(c.getGradoDeConfianza());
        comunidadDTO.setIdMiembros(c.getIdMiembros());
        comunidadDTO.setEstado(c.getEstado());
        return comunidadDTO;
    }

    public static Comunidad mapComunidadDTOToComunidad(ComunidadDTO c, RepoMembresia repoMembresia, RepoPrestacion repoPrestacion, RepoComunidad repoComunidad) {
        Comunidad comunidad = repoComunidad.buscar(Long.parseLong(String.valueOf(c.getId())));
        c.getIdServiciosObservados().forEach(s -> comunidad.agregarServicioDeInteres(repoPrestacion.buscar(Long.parseLong(String.valueOf(s)))));
        c.getIdMiembros().forEach(m -> comunidad.agregarMembresia(repoMembresia.buscar(Long.parseLong(String.valueOf(m)))));
        return comunidad;
    }

    public static List<FusionDTO> mapFusionesToFusionesDTO(List<Fusion> fusiones, List<Establecimiento> establecimientos) {
        List<FusionDTO> fusionesDTO = new ArrayList<>();
        for (Fusion f : fusiones) {
            fusionesDTO.add(mapFusionToFusionDTO(f, establecimientos));
         }
        return fusionesDTO;
    }

    public static FusionDTO mapFusionToFusionDTO(Fusion f, List<Establecimiento> establecimientos) {
        FusionDTO fusionDTO = new FusionDTO();
        fusionDTO.setEstado(f.getEstado());
        fusionDTO.setComunidad1(mapComunidadToComunidadDTO(f.getComunidad1(), establecimientos));
        fusionDTO.setComunidad2(mapComunidadToComunidadDTO(f.getComunidad2(), establecimientos));
        fusionDTO.setFechaCreada(f.getFechaCreada().toString());
        return fusionDTO;
    }

    public static Fusion mapFusionDTOToFusion(FusionDTO f, RepoMembresia repoMembresia, RepoPrestacion repoPrestacion, RepoComunidad repoComunidad) {
        Fusion fusion = new Fusion();
        fusion.setEstado(f.getEstado());
        fusion.setComunidad1(mapComunidadDTOToComunidad(f.getComunidad1(), repoMembresia, repoPrestacion, repoComunidad));
        fusion.setComunidad2(mapComunidadDTOToComunidad(f.getComunidad2(), repoMembresia, repoPrestacion, repoComunidad));
        fusion.setFechaCreada(Date.from(LocalDateTime.parse(f.getFechaCreada()).toInstant(java.time.ZoneOffset.UTC)));
        return fusion;
    }
}
