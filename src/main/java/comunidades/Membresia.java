package comunidades;

import lombok.Getter;

public class Membresia {
    @Getter private Comunidad comunidad;
    private Rol rol;

    public Membresia(Comunidad comunidad, Rol rol) {
        this.comunidad = comunidad;
        this.rol = rol;
    }

}
