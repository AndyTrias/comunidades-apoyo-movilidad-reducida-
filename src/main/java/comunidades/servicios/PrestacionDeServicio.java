package comunidades.servicios;

import comunidades.incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PrestacionDeServicio {
    @Getter private Servicio servicio;
    @Getter private List<Incidente> incidentes;

    public PrestacionDeServicio(Servicio servicio) {
        this.servicio = servicio;
        this.incidentes = new ArrayList<>();
    }

    public void agregarIncidente(Incidente incidente){
        incidentes.add(incidente);
    }

}


