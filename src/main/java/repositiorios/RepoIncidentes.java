package repositiorios;

import incidentes.Incidente;

public class RepoIncidentes extends RepoGenerico<Incidente>{
    public RepoIncidentes() {
        super(Incidente.class);
    }
}
