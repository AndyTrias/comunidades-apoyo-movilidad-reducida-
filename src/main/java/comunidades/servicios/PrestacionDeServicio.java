package comunidades.servicios;

import lombok.Getter;
import lombok.Setter;

public class PrestacionDeServicio {
    @Getter
    private Servicio servicio;
    @Getter @Setter
    private boolean funciona;

    public PrestacionDeServicio(Servicio servicio) {
        this.servicio = servicio;
        this.funciona = true;
    }

}


