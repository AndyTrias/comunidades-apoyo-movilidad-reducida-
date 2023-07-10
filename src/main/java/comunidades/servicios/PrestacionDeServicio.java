package comunidades.servicios;

import comunidades.incidentes.Incidente;
import localizacion.Localizacion;
import localizacion.UbicacionExacta;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PrestacionDeServicio {
    @Getter private Servicio servicio;
    @Getter private List<Incidente> incidentes;
    @Getter private String nombre;
    @Getter private UbicacionExacta ubicacionExacta;

    public PrestacionDeServicio(Servicio servicio, String nombre) {
        this.servicio = servicio;
        this.nombre = nombre;
        this.incidentes = new ArrayList<>();
    }

    public void agregarIncidente(Incidente incidente){
        incidentes.add(incidente);
    }

}


