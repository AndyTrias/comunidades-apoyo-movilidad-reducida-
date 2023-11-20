package models.incidentes;

import lombok.NoArgsConstructor;
import models.converters.NotificadorConverter;
import models.usuario.Usuario;
import lombok.Setter;
import models.notificaciones.notificador.Notificador;
import models.notificaciones.notificador.RevisionIncidente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "revision_de_incidentes")
public class RevisionDeIncidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Convert(converter = NotificadorConverter.class)
    @Column(name = "notificador")
    private static Notificador notificador;

    @Column(name = "latitud_maxima")
    private static final double LATITUD_MAXIMA = 0.1;

    @Column(name = "longitud_maxima")
    private static final double LONGITUD_MAXIMA = 0.1;

    public RevisionDeIncidente() {
        notificador = new RevisionIncidente();
    }

    public static boolean estaCerca(Usuario usuario, Incidente incidente) {
        return Math.abs(usuario.getUbicacionExacta().getLatitud() - incidente.getPrestacionDeServicio().getUbicacionExacta().getLatitud())
                < LATITUD_MAXIMA &&
                Math.abs(usuario.getUbicacionExacta().getLongitud() - incidente.getPrestacionDeServicio().getUbicacionExacta().getLongitud())
                < LONGITUD_MAXIMA;
    }

    public static void notificar(Usuario usuario, Incidente incidente) {
        notificador.notificar(usuario, incidente);
    }

    public static void comprobarCercania(Usuario usuario, List<Incidente> incidentes){
        List<Incidente> incidentesCercanos = new ArrayList<>();
        for (Incidente incidente : incidentes) {
            if (estaCerca(usuario, incidente)) {
                incidentesCercanos.add(incidente);
            }
        }

        for (Incidente incidente : incidentesCercanos) {
            usuario.agregarRevisionDeIncidente(incidente);
            notificar(usuario, incidente);
        }
    }

}
