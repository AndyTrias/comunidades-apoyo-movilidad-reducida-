package comunidades;


import comunidades.roles.Administrador;
import comunidades.roles.Miembro;
import lombok.Getter;
import lombok.Setter;
import usuarios.Usuario;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

@Setter @Getter
public class Comunidad {
    private Set<Administrador> administradores;
    private Set<Miembro> miembros;
    private String nombre;

    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.administradores = new HashSet<>();
        this.miembros = new HashSet<>();
    }
    public void agregarMiembro(Miembro miembro) {
        miembros.add(miembro);
    }

    public void agregarAdministrador(Usuario usuario) {
        Administrador administrador = new Administrador(usuario, this);
        administradores.add(administrador);
    }
}
