package comunidades.servicios;

import comunidades.incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PrestacionDeServicio {
    @Getter private Servicio servicio;
    @Getter @Setter private boolean funciona;
    @Getter private List<Incidente> incidentes;

    public PrestacionDeServicio(Servicio servicio) {
        this.servicio = servicio;
        this.funciona = true;
        this.incidentes = new ArrayList<>();
    }

    public Incidente nuevoIncidente(){
        if (funciona) {
            Incidente incidente = new Incidente();
            this.incidentes.add(incidente);
            return incidente;
        } else {
            return incidentes.get(incidentes.size() - 1);
        }
    }

}


