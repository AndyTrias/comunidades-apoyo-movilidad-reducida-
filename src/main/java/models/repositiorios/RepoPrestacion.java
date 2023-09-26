package models.repositiorios;

import models.servicios.PrestacionDeServicio;

public class RepoPrestacion extends RepoGenerico<PrestacionDeServicio>{
    public RepoPrestacion() {
        super(PrestacionDeServicio.class);
    }
}
