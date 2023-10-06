package models.comunidades;

import models.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "nombre")
    private String nombre;

    @Getter
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "rol_permiso",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private List<Permiso> permisos;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoRol tipoRol;

    public Rol(String nombre, List<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public Rol() {
        this.permisos = new ArrayList<>();
    }

    public boolean tenesPermiso(Permiso permiso) {
        return this.permisos.contains(permiso);
    }

    public void agregarPermiso(Permiso permiso) {
        this.permisos.add(permiso);
    }

    public void agregarPermisos(Permiso ... permisos) {
        Collections.addAll(this.permisos, permisos);
    }
}

