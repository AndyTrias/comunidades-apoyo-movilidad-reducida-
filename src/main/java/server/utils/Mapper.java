package server.utils;

import models.comunidades.Comunidad;
import models.comunidades.Fusion;
import models.entidades.Entidad;
import models.entidades.Establecimiento;
import models.external.retrofit.apiServicio1.responseClases.ComunidadDTO;
import models.external.retrofit.apiServicio1.responseClases.FusionDTO;
import models.external.retrofit.apiServicio3.responseClases.EntidadDTO;
import models.external.retrofit.apiServicio3.responseClases.IncidenteDTO;
import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;
import models.repositorios.RepoComunidad;
import models.repositorios.RepoIncidenteDeComunidad;
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
        fusionDTO.setFechaCreada(DateTimeConversion.dateToLocalDateTime(f.getFechaCreada()).toString());
        return fusionDTO;
    }

    public static Fusion mapFusionDTOToFusion(FusionDTO f, RepoMembresia repoMembresia, RepoPrestacion repoPrestacion, RepoComunidad repoComunidad) {
        Fusion fusion = new Fusion();
        fusion.setEstado(f.getEstado());
        fusion.setComunidad1(mapComunidadDTOToComunidad(f.getComunidad1(), repoMembresia, repoPrestacion, repoComunidad));
        fusion.setComunidad2(mapComunidadDTOToComunidad(f.getComunidad2(), repoMembresia, repoPrestacion, repoComunidad));
        fusion.setFechaCreada(DateTimeConversion.localDateTimeToDate(LocalDateTime.parse(f.getFechaCreada())));
        return fusion;
    }

    public static List<EntidadDTO> mapEntidadesToEntidadesDTO(List<Entidad> entidades, RepoComunidad repoComunidad) {
        List<EntidadDTO> entidadesDTO = new ArrayList<>();
        for (Entidad e : entidades) {
            entidadesDTO.add(mapEntidadToEntidadDTO(e, repoComunidad));
        }
        return entidadesDTO;
    }

    public static EntidadDTO mapEntidadToEntidadDTO(Entidad e, RepoComunidad repoComunidad) {
        EntidadDTO entidadDTO = new EntidadDTO();
        entidadDTO.setId(Integer.parseInt(String.valueOf(e.getId())));
        entidadDTO.setIncidentes(mapIncidentesToIncidentesDTO(e.getIncidentes(), repoComunidad));
        return entidadDTO;
    }

    public static IncidenteDTO mapIncidenteToIncidenteDTO(Incidente i, RepoComunidad repoComunidad) {
        IncidenteDTO incidenteDTO = new IncidenteDTO();
        incidenteDTO.setFechaApertura(DateTimeConversion.dateToLocalDateTime(i.getFechaDeApertura()).toString());
        incidenteDTO.setFechaCierre(DateTimeConversion.dateToLocalDateTime(i.calcularPromedioFechasCierre()).toString());
        List<Comunidad> comunidadesConIncidente = repoComunidad.buscarTodosPorIncidente(i);
        int cantidadDeAfectados = 0;
        for (Comunidad c : comunidadesConIncidente) {
            cantidadDeAfectados += c.getCantidadDeAfectados(i.getPrestacionDeServicio());
        }
        incidenteDTO.setMiembrosAfectados(cantidadDeAfectados);
        return incidenteDTO;
    }

    public static List<IncidenteDTO> mapIncidentesToIncidentesDTO(List<Incidente> incidentes, RepoComunidad repoComunidad) {
        List<IncidenteDTO> incidentesDTO = new ArrayList<>();
        for (Incidente i : incidentes) {
            incidentesDTO.add(mapIncidenteToIncidenteDTO(i, repoComunidad));
        }
        return incidentesDTO;
    }
}
