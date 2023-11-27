package models.rankings.criterios;

import models.entidades.Entidad;
import models.external.retrofit.apiServicio3.ApiServicio3;
import models.external.retrofit.apiServicio3.responseClases.EntidadDTO;
import models.external.retrofit.apiServicio3.responseClases.PayloadServicio3DTO;
import models.repositorios.RepoComunidad;
import server.utils.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MayorImpacto extends CriteriosEntidadesQueUsanIncidentes{


  public MayorImpacto(String nombre) {
    super(nombre);
  }

  public List<Entidad> generarRanking(List<Entidad> entidades) {
    List<EntidadDTO> entidadesDTO = Mapper.mapEntidadesToEntidadesDTO(entidades, new RepoComunidad());
    PayloadServicio3DTO payloadServicio3DTO = new PayloadServicio3DTO(entidadesDTO);

    try {
      PayloadServicio3DTO response = ApiServicio3.getInstancia().rankingEntidades(payloadServicio3DTO);

      List<Long> rankedIds =
          response.getEntidades().stream()
              .mapToLong(EntidadDTO::getId)
              .boxed()
              .toList();

      return rankedIds.stream()
          .flatMap(id -> entidades.stream().filter(entidad -> entidad.getId() == id))
          .collect(Collectors.toList());

    } catch (Exception e) {
      e.printStackTrace();
      return Collections.emptyList();
    }
  }

}

