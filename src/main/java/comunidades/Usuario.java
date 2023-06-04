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
        // Implementación
    }

    public void unirseAComunidad(Comunidad comunidad) {
        Rol rol = comunidad.solicitarUnirse(this);
        Membresia membresia = new Membresia(comunidad, rol);
        this.membresias.add(membresia);
    }

    public void abandonarComunidad(Comunidad comunidad) {
        // Implementación
    }
}
