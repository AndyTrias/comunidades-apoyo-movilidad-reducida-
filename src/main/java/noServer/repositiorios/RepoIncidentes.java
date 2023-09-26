package noServer.repositiorios;

import noServer.incidentes.Incidente;

public class RepoIncidentes extends RepoGenerico<Incidente>{
    public RepoIncidentes() {
        super(Incidente.class);
    }
}
