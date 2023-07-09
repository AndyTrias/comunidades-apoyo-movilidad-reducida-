package comunidades.incidentes;

import comunidades.servicios.PrestacionDeServicio;
import comunidades.usuario.Usuario;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Incidente {
    private Date fechaDeApertura;
    private List<Date> fechasDeCierre;
    private String observaciones;
    @Getter private Usuario abiertoPor;
    @Getter private PrestacionDeServicio prestacionDeServicio;

    public Incidente(Usuario usuario, String observaciones, PrestacionDeServicio prestacionDeServicio) {
        this.fechaDeApertura = new Date();
        this.fechasDeCierre = new ArrayList<>();
        this.observaciones = observaciones;
        this.abiertoPor = usuario;
        this.prestacionDeServicio = prestacionDeServicio;
        this.prestacionDeServicio.agregarIncidente(this);
    }

    public void cerrar() {
        fechasDeCierre.add(new Date());
    }

    public boolean estaAbierto() {
        return fechasDeCierre.isEmpty();
    }
}
