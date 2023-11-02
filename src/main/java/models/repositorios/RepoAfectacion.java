package models.repositorios;

import models.comunidades.Afectacion;

public class RepoAfectacion extends RepoGenerico<Afectacion> {
  public static RepoAfectacion INSTANCE = new RepoAfectacion();

    public RepoAfectacion() {
        super(Afectacion.class);
    }

}
