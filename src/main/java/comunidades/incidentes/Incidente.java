package comunidades.incidentes;

import java.util.Date;

public class Incidente {
    private Date fechaDeApertura;
    private Date fechaDeCierre;
    private boolean resuelto;

    public Incidente() {
        this.fechaDeApertura = new Date();
        this.resuelto = false;
    }

    public void cerrar() {
        this.resuelto = true;
        this.fechaDeCierre = new Date();
    }
}
