package noServer.incidentes;

import noServer.usuario.Usuario;
import lombok.Setter;
import noServer.notificaciones.notificador.Notificador;
import noServer.notificaciones.notificador.RevisionIncidente;

import java.util.ArrayList;
import java.util.List;

public class RevisionDeIncidente {
    @Setter private Notificador notificador;
    private List<Incidente> incidentes;
    private static final double LATITUD_MAXIMA = 0.1;
    private static final double LONGITUD_MAXIMA = 0.1;
    private static RevisionDeIncidente instancia = null;

    private RevisionDeIncidente() {
        this.notificador = new RevisionIncidente();
        this.incidentes = new ArrayList<>();
    }

    public boolean estaCerca(Usuario usuario, Incidente incidente) {
        return Math.abs(usuario.getUbicacionExacta().getLatitud() - incidente.getPrestacionDeServicio().getUbicacionExacta().getLatitud())
                < LATITUD_MAXIMA &&
                Math.abs(usuario.getUbicacionExacta().getLongitud() - incidente.getPrestacionDeServicio().getUbicacionExacta().getLongitud())
                < LONGITUD_MAXIMA;
    }

    public void notificar(Usuario usuario, Incidente incidente) {
        notificador.notificar(usuario, incidente);
    }

    public void comprobarCercania(Usuario usuario){
        List<Incidente> incidentesCercanos = new ArrayList<>();
        for (Incidente incidente : this.incidentes) {
            if (estaCerca(usuario, incidente)) {
                incidentesCercanos.add(incidente);
            }
        }

        for (Incidente incidente : incidentesCercanos) {
            notificar(usuario, incidente);
        }
    }

    public void agregarIncidente(Incidente incidente){
        this.incidentes.add(incidente);
    }

    public void eliminarIncidente(Incidente incidente){
        this.incidentes.remove(incidente);
    }

    public static RevisionDeIncidente getInstance() {
        if (instancia == null) {
            instancia = new RevisionDeIncidente();
        }
        return instancia;
    }

    public void eliminarTodosLosIncidentes() {
        this.incidentes.clear();
    }
}
