package repositiorios;

import comunidades.Comunidad;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class RepoComunidades {
    private static RepoComunidades instance = null;
    @Getter private List<Comunidad> comunidades;

    private RepoComunidades() {
        this.comunidades = new ArrayList<>();
    }

    public static RepoComunidades getInstance() {
        if (instance == null) {
            instance = new RepoComunidades();
        }
        return instance;
    }

    public void agregarComunidad(Comunidad comunidades) {
        this.comunidades.add(comunidades);
    }

    public void eliminarComunidad(Comunidad comunidades) {
        this.comunidades.remove(comunidades);
    }

    public void borrarComunidades() {
        this.comunidades.clear();
    }
}
