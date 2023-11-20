package models.incidentes;

import lombok.Setter;
import models.converters.NotificadorConverter;
import models.servicios.PrestacionDeServicio;
import models.usuario.Usuario;
import lombok.Getter;
import models.notificaciones.notificador.AperturaDeIncidente;
import models.notificaciones.notificador.Notificador;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "incidente")
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @Column(name = "fecha_de_apertura")
    private Date fechaDeApertura;

    @Getter
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "fechas_de_cierre", joinColumns = @JoinColumn(name = "incidente_id"))
    private List<Date> fechasDeCierre;

    @Setter
    @Getter
    @Column(name = "observaciones", columnDefinition = "longtext")
    private String observaciones;

    @Getter
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Usuario abiertoPor;

    @Getter
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "prestacion_de_servicio_id")
    private PrestacionDeServicio prestacionDeServicio;

    @Convert(converter = NotificadorConverter.class)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "notificador")
    private Notificador notificador;

    public Incidente(Usuario usuario, String observaciones, PrestacionDeServicio prestacionDeServicio, Date date) {
        this.fechaDeApertura = date;
        this.fechasDeCierre = new ArrayList<>();
        this.observaciones = observaciones;
        this.abiertoPor = usuario;
        this.prestacionDeServicio = prestacionDeServicio;
        this.notificador = new AperturaDeIncidente();

        this.prestacionDeServicio.agregarIncidente(this);
        this.notificarApertura();
    }

    public Incidente() {

    }

    public void cerrar() {
        fechasDeCierre.add(new Date());
    }


    public long calcularPromedioFechasCierre() {
        if (fechasDeCierre.isEmpty()) {
            return fechaDeApertura.getTime() + (7 * 24 * 60 * 60 * 1000); // Si no hay fechas de cierre, tiempo activo de una semana
        }

        long totalMillis = fechasDeCierre.stream().mapToLong(Date::getTime).sum();
        return totalMillis / fechasDeCierre.size();
    }

    public long tiempoActivo() {
        long fechaPromedioCierreMillis = calcularPromedioFechasCierre();

        long aperturaMillis = fechaDeApertura.getTime();
        long diferenciaMillis = fechaPromedioCierreMillis - aperturaMillis;

        return diferenciaMillis / (60 * 1000); // Convertir de milisegundos a minutos
    }

    public void notificarApertura(){
        notificador.notificar(this.abiertoPor, this);
    }

    public boolean estaAbierto(){
        return fechasDeCierre.isEmpty();
    }

    public boolean ocurrioEstaSemana() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaDeAperturaLocalDate = fechaDeApertura.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate haceUnaSemana = hoy.minus(7, ChronoUnit.DAYS);
        return fechaDeAperturaLocalDate.isAfter(haceUnaSemana);
    }

    public boolean ocurrioHaceMasDe24Hs() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaDeAperturaLocalDate = fechaDeApertura.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate hace24Hs = hoy.minus(1, ChronoUnit.DAYS);
        return fechaDeAperturaLocalDate.isBefore(hace24Hs);
    }


}

