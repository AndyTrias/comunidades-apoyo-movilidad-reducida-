package servicios;

import incidentes.Incidente;
import localizacion.UbicacionExacta;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class PrestacionDeServicio {
    @Getter private Servicio servicio;
    @Getter private List<Incidente> incidentes;
    @Getter private String nombre;
    @Getter private UbicacionExacta ubicacionExacta;

    public PrestacionDeServicio(Servicio servicio, String nombre, UbicacionExacta ubicacionExacta) {
        this.servicio = servicio;
        this.nombre = nombre;
        this.incidentes = new ArrayList<>();
        this.ubicacionExacta = ubicacionExacta;
    }

    public void agregarIncidente(Incidente incidente){
        incidentes.add(incidente);
    }

}


