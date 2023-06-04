package comunidades;

import java.util.List;

public class Usuario {
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String contrasenia;
    private Intereses intereses;
    private List<Membresia> membresias;

    public void setContrasenia(String contrasena) {
        // Implementaci�n
    }

    public void crearMembresia(comunidad Comunidad) {
        Rol rol = comunidad.getRol(this);
        Membresia membresia = new Membresia(comunidad, rol);
        membresias.add(membresia);
    }

    public void abandonarComunidad(Comunidad comunidad) {
        // Implementaci�n
    }
}


