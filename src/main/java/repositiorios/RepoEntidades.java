package repositiorios;

import comunidades.servicios.PrestacionDeServicio;
import comunidades.servicios.Servicio;
import comunidades.usuario.Usuario;
import entidades.Entidad;

import java.util.ArrayList;
import java.util.List;

public class RepoEntidades {
    private static RepoEntidades instance = null;
    private List<Entidad> entidades;

    private RepoEntidades() {
        this.entidades = new ArrayList<>();
    }

    public static RepoEntidades getInstance() {
        if (instance == null) {
            instance = new RepoEntidades();
        }
        return instance;
    }

    public void agregarUsuario(Entidad entidad) {
        this.entidades.add(entidad);
    }

    public void eliminarUsuario(Entidad entidad) {
        this.entidades.remove(entidad);
    }

    public List<Entidad> getEntidadesConPrestacion(PrestacionDeServicio prestacionDeServicio){
        return this.entidades.stream().filter(entidad -> entidad.getPrestacionesDeServicios().contains(prestacionDeServicio)).toList();
    }
}
