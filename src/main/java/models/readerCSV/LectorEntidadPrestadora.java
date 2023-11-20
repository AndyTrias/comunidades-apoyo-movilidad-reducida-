package models.readerCSV;

import models.entidades.EntidadPrestadora;
import lombok.Getter;

import java.util.*;

@Getter
public class LectorEntidadPrestadora extends FileStream {

    private Map<EntidadPrestadora, Long> entidadesLeidas;

    public LectorEntidadPrestadora() {
        this.entidadesLeidas = new HashMap<>();
    }

    protected void levantarObjetos(List<String[]> csvComoLista) {
        for (String[] strings : csvComoLista) {
            EntidadPrestadora entidadPrestadora = new EntidadPrestadora(strings[1]);
            entidadesLeidas.put(entidadPrestadora, Long.parseLong(strings[0]));
        }
    }
}
