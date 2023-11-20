package models.repositorios;

import models.incidentes.Incidente;

public class RepoIncidentes extends RepoGenerico<Incidente>{
    public static RepoIncidentes INSTANCE = new RepoIncidentes();

    public RepoIncidentes() {
        super(Incidente.class);
    }


}
