package repositiorios;

import lombok.Getter;
import lombok.Setter;
import servicios.PrestacionDeServicio;
import entidades.Entidad;

import java.util.ArrayList;
import java.util.List;

public class RepoEntidades {
    private static RepoEntidades instance = null;
    @Getter @Setter private List<Entidad> entidades;

    private RepoEntidades() {
        this.entidades = new ArrayList<>();
    }

    public static RepoEntidades getInstance() {
        if (instance == null) {
            instance = new RepoEntidades();
        }
        return instance;
    }

    public void agregarEntidad(Entidad entidad) {
        this.entidades.add(entidad);
    }

    public void eliminarEntidad(Entidad entidad) {
        this.entidades.remove(entidad);
    }

    public List<Entidad> getEntidadesConPrestacion(PrestacionDeServicio prestacionDeServicio){
        return this.entidades.stream().filter(entidad -> entidad.getPrestacionesDeServicios().contains(prestacionDeServicio)).toList();
    }

    public void borrarEntidades() {
        this.entidades.clear();
    }
}
