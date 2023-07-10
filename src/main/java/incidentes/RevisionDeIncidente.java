package incidentes;

import comunidades.usuario.Usuario;
import lombok.Setter;
import notificaciones.notificador.Notificador;
import notificaciones.notificador.RevisionIncidente;

import java.util.ArrayList;
import java.util.List;

public class RevisionDeIncidente {
    @Setter private Notificador notificador;
    private List<Incidente> incidentes;
    private static final double LATITUD_MAXIMA = 0.1;
    private static final double LONGITUD_MAXIMA = 0.1;

    public RevisionDeIncidente() {
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
        if (estaCerca(usuario, incidente)) {
            notificador.notificar(usuario, incidente);
        }
    }

    public void agregarIncidente(Incidente incidente){
        this.incidentes.add(incidente);
    }
}
