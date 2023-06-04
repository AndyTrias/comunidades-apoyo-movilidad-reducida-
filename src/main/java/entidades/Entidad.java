package entidades;

import localizacion.Localizacion;

import java.util.List;

//comente cosas para probar lo del csv porque por ahora solo instancio en nombre de la clase
public class Entidad {
   // private List<Establecimiento> establecimientos;
   // private Localizacion localizacion;
    private String nombre;

    // Implementaci�n

    public Entidad(String nombre) {
        this.nombre = nombre;
       // this.localizacion = localizacion;
       // this.establecimientos = establecimientos;
    }

    public String getNombre() {
        return nombre;
    }
}
