package models.readerCSV;

import models.entidades.EntidadPrestadora;
import lombok.Getter;

import java.util.*;

public class LectorEntidadPrestadora extends FileStream {

    @Getter
    private Map<EntidadPrestadora, Long> entidadesLeidas;

    public LectorEntidadPrestadora() {
        this.entidadesLeidas = new HashMap<>();
    }

    protected void levantarObjetos(List<String[]> csvComoLista) {
        for (int i = 1; i < csvComoLista.size(); i++) {
            EntidadPrestadora entidadPrestadora = new EntidadPrestadora(csvComoLista.get(i)[1]);
            entidadesLeidas.put(entidadPrestadora, Long.parseLong(csvComoLista.get(i)[0]));;
        }
    }
}
