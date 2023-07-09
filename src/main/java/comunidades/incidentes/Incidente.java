package comunidades.incidentes;

import comunidades.servicios.PrestacionDeServicio;
import comunidades.usuario.Usuario;
import lombok.Getter;
import notificaciones.notificador.AperturaDeIncidente;
import notificaciones.notificador.Notificador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Incidente {
    private Date fechaDeApertura;
    private List<Date> fechasDeCierre;
    private String observaciones;
    @Getter private Usuario abiertoPor;
    @Getter private PrestacionDeServicio prestacionDeServicio;
    private Notificador notificador;

    public Incidente(Usuario usuario, String observaciones, PrestacionDeServicio prestacionDeServicio) {
        this.fechaDeApertura = new Date();
        this.fechasDeCierre = new ArrayList<>();
        this.observaciones = observaciones;
        this.abiertoPor = usuario;
        this.prestacionDeServicio = prestacionDeServicio;
        this.notificador = new AperturaDeIncidente();

        this.prestacionDeServicio.agregarIncidente(this);
        notificarApertura();
    }

    public void cerrar() {
        fechasDeCierre.add(new Date());
    }

<<<<<<< HEAD
    public boolean estaAbierto() {
        return fechasDeCierre.isEmpty();
=======
    public void notificarApertura(){
        //notificador.notificar(this);
>>>>>>> 67697ea001b4a1747112eb5b96968e7e36c5e334
    }
}
