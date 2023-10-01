package models.repositorios;

import models.incidentes.IncidenteDeComunidad;

import java.util.List;

public class RepoIncidenteDeComunidad extends RepoGenerico<IncidenteDeComunidad>{
    public RepoIncidenteDeComunidad() {
        super(IncidenteDeComunidad.class);
    }

  public List<IncidenteDeComunidad> buscarTodosDeComunidad(Long comunidadId) {
    return entityManager().createQuery(
            "SELECT c FROM Comunidad c JOIN c.incidentes WHERE c.id = :comunidadId",
            IncidenteDeComunidad.class)
        .setParameter("comunidadId", comunidadId)
        .getResultList();
  }




}
