package models.repositorios;

import models.entidades.Establecimiento;

public class RepoEstablecimiento extends RepoGenerico<Establecimiento>{
    public static RepoEstablecimiento INSTANCE = new RepoEstablecimiento();
    public RepoEstablecimiento() {
        super(Establecimiento.class);
    }
}
