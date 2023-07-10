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
    @Getter private Date fechaDeApertura;
    @Getter private List<Date> fechasDeCierre;
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
        this.notificarApertura();
    }

    public void cerrar() {
        fechasDeCierre.add(new Date());
    }


    public Date calcularPromedioFechasCierre() {
        if (fechasDeCierre.isEmpty()) {
            return null;
        }

        long totalMillis = 0;
        for (Date fechaCierre : fechasDeCierre) {
            totalMillis += fechaCierre.getTime();
        }

        long promedioMillis = totalMillis / fechasDeCierre.size();
        return new Date(promedioMillis);
    }


    public long tiempoActivo() {
        Date fechaPromedioCierre = calcularPromedioFechasCierre();

        if (fechaPromedioCierre == null) {
            return 0;  // O devuelve otro valor adecuado en caso de que no haya fechas de cierre
        }

        long aperturaMillis = fechaDeApertura.getTime();
        long promedioCierreMillis = fechaPromedioCierre.getTime();
        long diferenciaMillis = promedioCierreMillis - aperturaMillis;

        return diferenciaMillis / (1000 * 60);  // Convertir de milisegundos a minutos
    }

    public void notificarApertura(){
        notificador.notificar(this.abiertoPor, this);
    }

    public boolean estaAbierto(){
        return fechasDeCierre.isEmpty();
    }


}

