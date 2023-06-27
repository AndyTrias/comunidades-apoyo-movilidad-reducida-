package comunidades.incidentes;

import comunidades.Membresia;
import comunidades.usuario.Usuario;

import java.util.Date;

public class IncidenteDeComunidad {
    private Incidente incidente;
    private Usuario usuario;
    private String observaciones;
    private Date fechaDeCierre;

    public IncidenteDeComunidad(Incidente incidente, Usuario usuario, String observaciones) {
        this.incidente = incidente;
        this.usuario = usuario;
        this.observaciones = observaciones;
    }


}
