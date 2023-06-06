package comunidades;

import lombok.Getter;

public class Membresia {
    @Getter private Comunidad comunidad;
    @Getter private Rol rol;

    public Membresia(Comunidad comunidad, Rol rol) {
        this.comunidad = comunidad;
        this.rol = rol;
    }

}
