package models.repositorios;

import models.incidentes.Incidente;
import models.incidentes.IncidenteDeComunidad;

import java.util.List;

public class RepoIncidenteDeComunidad extends RepoGenerico<IncidenteDeComunidad> {
    public RepoIncidenteDeComunidad(){
        super(IncidenteDeComunidad.class);
    }

    public List<IncidenteDeComunidad> buscarPorIncidente(Incidente incidente) {
        return this.buscarTodos().stream().filter(incidenteDeComunidad -> incidenteDeComunidad.getIncidente().equals(incidente)).toList();
    }
}
