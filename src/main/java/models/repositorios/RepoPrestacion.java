package models.repositorios;

import models.servicios.PrestacionDeServicio;

public class RepoPrestacion extends RepoGenerico<PrestacionDeServicio>{

    public static RepoPrestacion INSTANCE = new RepoPrestacion();

    public RepoPrestacion() {
        super(PrestacionDeServicio.class);
    }
}
