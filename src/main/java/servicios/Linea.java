package servicios;

import servicios.clasesAuxiliares.TipoMedioDeTransporte;

import java.util.ArrayList;

public class Linea {
    private ArrayList<Estacion> estaciones;
    private String nombre;
    private TipoMedioDeTransporte tipoMedioDeTransporte;

    public Linea( ArrayList<Estacion> estaciones, String nombre, TipoMedioDeTransporte tipoMedioDeTransporte) {
        this.estaciones = estaciones;
        this.nombre = nombre;
        this.tipoMedioDeTransporte = tipoMedioDeTransporte;
    }

    public Estacion estacionDeOrigen(){
        return estaciones.get(0);
    }

    public Estacion estacionDeDestino(){
        return estaciones.get(estaciones.size() - 1);
    }
}
