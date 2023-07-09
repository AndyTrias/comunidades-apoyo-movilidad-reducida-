package comunidades.incidentes;

import comunidades.Membresia;
import comunidades.usuario.Usuario;

import java.util.Date;

public class IncidenteDeComunidad {
    private Incidente incidente;
    private Date fechaDeCierre;

    public IncidenteDeComunidad(Incidente incidente) {
        this.incidente = incidente;
    }

    public void cerrarIncidente() {
        fechaDeCierre = new Date();
    }
}
