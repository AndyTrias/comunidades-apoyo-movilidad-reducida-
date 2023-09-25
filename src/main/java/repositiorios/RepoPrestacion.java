package repositiorios;

import servicios.PrestacionDeServicio;

public class RepoPrestacion extends RepoGenerico<PrestacionDeServicio>{
    public RepoPrestacion() {
        super(PrestacionDeServicio.class);
    }
}
