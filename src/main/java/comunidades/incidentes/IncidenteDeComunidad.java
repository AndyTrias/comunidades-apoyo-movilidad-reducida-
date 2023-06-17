package comunidades.incidentes;

import comunidades.Membresia;
import comunidades.usuario.Usuario;

import java.util.Date;

public class IncidenteDeComunidad {
    private Incidente incidente;
    private Membresia miembro;
    private String observaciones;
    private Date fechaDeCierre;

    public IncidenteDeComunidad(Incidente incidente, Membresia miembro, String observaciones) {
        this.incidente = incidente;
        this.miembro = miembro;
        this.observaciones = observaciones;
    }


}
