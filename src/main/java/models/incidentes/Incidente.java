package models.incidentes;

import lombok.Setter;
import models.converters.NotificadorConverter;
import models.servicios.PrestacionDeServicio;
import models.usuario.Usuario;
import lombok.Getter;
import models.notificaciones.notificador.AperturaDeIncidente;
import models.notificaciones.notificador.Notificador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "incidente")
public class Incidente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "fecha_de_apertura")
    private Date fechaDeApertura;

    @Getter
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "fechas_de_cierre", joinColumns = @JoinColumn(name = "incidente_id"))
    private List<Date> fechasDeCierre;

    @Setter
    @Column(name = "observaciones", columnDefinition = "longtext")
    private String observaciones;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Usuario abiertoPor;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private PrestacionDeServicio prestacionDeServicio;

    @Convert(converter = NotificadorConverter.class)
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "notificador")
    private Notificador notificador;

    public Incidente(Usuario usuario, String observaciones, PrestacionDeServicio prestacionDeServicio) {
        this.fechaDeApertura = new Date();
        this.fechasDeCierre = new ArrayList<>();
        this.observaciones = observaciones;
        this.abiertoPor = usuario;
        this.prestacionDeServicio = prestacionDeServicio;
        this.notificador = new AperturaDeIncidente();

        this.prestacionDeServicio.agregarIncidente(this);
//        this.notificarApertura();
//        RevisionDeIncidente.getInstance().agregarIncidente(this);
    }

    public Incidente() {

    }

    public void cerrar() {
        fechasDeCierre.add(new Date());
        RevisionDeIncidente.getInstance().eliminarIncidente(this);
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

    public boolean ocurrioEstaSemana(){
        Date hoy = new Date();
        Date haceUnaSemana = new Date(hoy.getTime() - 7 * 24 * 3600 * 1000);
        return fechaDeApertura.after(haceUnaSemana);
    }


}
