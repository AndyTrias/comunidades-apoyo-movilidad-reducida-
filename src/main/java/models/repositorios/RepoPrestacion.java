package models.repositorios;

import models.servicios.PrestacionDeServicio;

public class RepoPrestacion extends RepoGenerico<PrestacionDeServicio>{
    public RepoPrestacion() {
        super(PrestacionDeServicio.class);
    }
}
