package models.rankings.criterios;

import models.entidades.Entidad;
import models.external.retrofit.apiServicio3.ApiServicio3;
import models.external.retrofit.apiServicio3.responseClases.EntidadDTO;
import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import models.rankings.informes.Ranking;
import models.repositorios.RepoComunidad;
import server.utils.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MayorImpacto extends CriteriosEntidadesQueUsanIncidentes{


  public MayorImpacto(String nombre) {
    super(nombre);
  }

  public List<Ranking> generarRanking(List<Entidad> entidades) {
    System.out.println("Generando ranking de MayorImpacto");
    System.out.println("entidades: " + entidades);
    List<EntidadDTO> entidadesDTO = Mapper.mapEntidadesToEntidadesDTO(entidades);
    System.out.println("entidadesDTO: " + entidadesDTO);
    entidadesDTO.forEach(e -> System.out.println(e.getId()));
    PayloadServicio3DTO payloadServicio3DTO = new PayloadServicio3DTO(entidadesDTO);
    System.out.println(payloadServicio3DTO);
    try {
      System.out.println("Llamando a ApiServicio3");
      PayloadServicio3DTO response = ApiServicio3.getInstancia().rankingEntidades(payloadServicio3DTO);
      System.out.println("hizo la respuesta: " + response);

      return response.getEntidades().stream()
              .map(entidadDTO -> new Ranking(Mapper.mapEntidadDTOToEntidad(entidadDTO, entidades), doubleStringToInt(entidadDTO.getValor())))
              .toList();

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("rompio");
      System.out.println(e);
      return Collections.emptyList();
    }
  }

  private int doubleStringToInt(String doubleString) {
    return (int) Double.parseDouble(doubleString);
  }

}

