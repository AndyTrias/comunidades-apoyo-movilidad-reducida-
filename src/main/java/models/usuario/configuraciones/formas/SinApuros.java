package models.usuario.configuraciones.formas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import models.notificaciones.Notificacion;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "sin_apuros")
public class SinApuros implements EstrategiaDeNotificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ElementCollection
    @CollectionTable(name = "horarios", joinColumns = @JoinColumn(name = "sinApuros_id"))
    @Column(name = "horario")
    private List<Date> horarios;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Notificacion> aNotificar;

    public SinApuros(Date horarioInicial){
        this.aNotificar = new ArrayList<>();
        this.horarios = new ArrayList<>();
        this.horarios.add(horarioInicial);
    }

    public SinApuros() {
        this.aNotificar = new ArrayList<>();
        this.horarios = new ArrayList<>();
    }

    public void agregarHorario(Date horario) {
        horarios.add(horario);
    }

    public void notificar(Notificacion notificacion) {
        aNotificar.add(notificacion);
        enviarNotificacionesCuandoCorresponda();
    }

    public void enviarNotificacionesCuandoCorresponda() {
        Timer timer = new Timer();

        for (Date horario : horarios) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Notificacion notificacion : aNotificar) {
                        new Thread(() -> {
                            notificacion.getDestinatario().getConfiguracionDeNotificaciones().getMedioPreferido().notificar(notificacion);
                        }).start();
                    }

                    aNotificar.clear();
                }
            }, horario);
        }
    }

}
