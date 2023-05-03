package comunidades;

import comunidades.Comunidad;
import lombok.Getter;
import usuarios.Usuario;

public abstract class Rol {

    protected Comunidad comunidad;

    @Getter
    protected Usuario usuario;

    public Rol(Usuario usuario, Comunidad comunidad) {
        this.usuario = usuario;
        this.comunidad = comunidad;
    }
}
