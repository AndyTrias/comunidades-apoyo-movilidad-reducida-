package models.readerCSV;

import models.entidades.EntidadPrestadora;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LectorEntidadPrestadora extends FileStream {

    @Getter
    private Set<EntidadPrestadora> entidadesLeidas;

    public LectorEntidadPrestadora() {
        this.entidadesLeidas = new HashSet<>();
    }

    protected void levantarObjetos(List<String[]> csvComoLista) {
        for (int i = 1; i < csvComoLista.size(); i++) {
            EntidadPrestadora entidadPrestadora = new EntidadPrestadora(csvComoLista.get(i)[1]);
            entidadPrestadora.setId(Long.parseLong(csvComoLista.get(i)[0]));
            entidadesLeidas.add(entidadPrestadora);
        }
    }
}
