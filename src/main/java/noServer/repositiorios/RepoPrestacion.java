package noServer.repositiorios;

import noServer.servicios.PrestacionDeServicio;

public class RepoPrestacion extends RepoGenerico<PrestacionDeServicio>{
    public RepoPrestacion() {
        super(PrestacionDeServicio.class);
    }
}
