package comunidades.incidentes;

import java.util.Date;

public class Incidente {
    private Date fechaDeApertura;
    private boolean resuelto;

    public Incidente() {
        this.fechaDeApertura = new Date();
        this.resuelto = false;
    }
}
