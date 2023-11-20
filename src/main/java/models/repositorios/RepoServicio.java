package models.repositorios;

import models.servicios.Servicio;

public class RepoServicio extends RepoGenerico<Servicio>{

    public static RepoServicio INSTANCE = new RepoServicio();

    public RepoServicio() {
        super(Servicio.class);
    }
}
