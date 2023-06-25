package comunidades;

import comunidades.incidentes.Incidente;
import comunidades.servicios.PrestacionDeServicio;
import lombok.Getter;

public class Membresia {
    @Getter private Comunidad comunidad;
    @Getter private Rol rol;

    public Membresia(Comunidad comunidad, Rol rol) {
        this.comunidad = comunidad;
        this.rol = rol;
    }

    public void abrirIncidente(PrestacionDeServicio prestacionDeServicio, String observaciones) {
        Incidente incidente = prestacionDeServicio.nuevoIncidente();
        comunidad.nuevoIncidenteEn(incidente, this, observaciones);
    }

}
