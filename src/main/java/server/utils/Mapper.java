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
import models.repositorios.RepoComunidad;
import models.repositorios.RepoMembresia;
import models.repositorios.RepoPrestacion;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        Comunidad comunidad;
        if (c.getId() == null) {
            comunidad = new Comunidad("Comunidad fusionada");
        } else {
            comunidad = repoComunidad.buscar(Long.parseLong(String.valueOf(c.getId())));
        }
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

    public static List<EntidadDTO> mapEntidadesToEntidadesDTO(List<Entidad> entidades) {
        List<EntidadDTO> entidadesDTO = new ArrayList<>();
        System.out.println("entrando al for de lista de entidadesDTO: " + entidadesDTO);
        for (Entidad e : entidades) {
            entidadesDTO.add(mapEntidadToEntidadDTO(e));
        }
        System.out.println("devolviendo lista de entidadesDTO: " + entidadesDTO);
        return entidadesDTO;
    }

    public static EntidadDTO mapEntidadToEntidadDTO(Entidad e) {
        EntidadDTO entidadDTO = new EntidadDTO();
        entidadDTO.setId(Integer.parseInt(String.valueOf(e.getId())));
        entidadDTO.setIncidentes(mapIncidentesToIncidentesDTO(e.getIncidentes()));
        System.out.println("entidadDTO: " + entidadDTO);
        return entidadDTO;
    }

    public static Entidad mapEntidadDTOToEntidad(EntidadDTO e, List<Entidad> entidades) {
        return entidades.stream().filter(entidad -> entidad.getId() == e.getId()).findFirst().orElse(null);
    }

    public static IncidenteDTO mapIncidenteToIncidenteDTO(Incidente i) {
        IncidenteDTO incidenteDTO = new IncidenteDTO();
        incidenteDTO.setFechaApertura(obtenerFechaComoString(i.getFechaDeApertura()));
        incidenteDTO.setFechaCierre(obtenerFechaComoString(new Date(i.calcularPromedioFechasCierre())));
        List<Comunidad> comunidadesConIncidente = RepoComunidad.INSTANCE.buscarTodosPorIncidente(i);
        System.out.println("comunidadesConIncidente: " + comunidadesConIncidente);
        int cantidadDeAfectados = 0;
        for (Comunidad c : comunidadesConIncidente) {
            cantidadDeAfectados += c.getCantidadDeAfectados(i.getPrestacionDeServicio());
            System.out.println("cantidadDeAfectados: " + cantidadDeAfectados);
        }
        incidenteDTO.setMiembrosAfectados(cantidadDeAfectados);
        System.out.println("incidenteDTO: " + incidenteDTO);
        return incidenteDTO;
    }


    public static String obtenerFechaComoString(Date fecha) {
        Instant instant = fecha.toInstant();
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatoFecha);
    }

    public static List<IncidenteDTO> mapIncidentesToIncidentesDTO(List<Incidente> incidentes) {
        List<IncidenteDTO> incidentesDTO = new ArrayList<>();
        System.out.println("entrando al for de lista de incidentesDTO: " + incidentesDTO);
        for (Incidente i : incidentes) {
            incidentesDTO.add(mapIncidenteToIncidenteDTO(i));
        }
        System.out.println("devolviendo lista de incidentesDTO: " + incidentesDTO);
        return incidentesDTO;
    }
}
