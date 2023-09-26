package noServer.readerCSV;

import noServer.entidades.EntidadPrestadora;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LectorEntidadPrestadora extends FileStream {

    @Getter Set<EntidadPrestadora> entidadesLeidas;

    public LectorEntidadPrestadora() {
        this.entidadesLeidas = new HashSet<>();
    }

    protected void levantarObjetos(List<String[]> csvComoLista) {
        for (int i = 1; i < csvComoLista.size(); i++) {
            EntidadPrestadora entidadPrestadora = new EntidadPrestadora(csvComoLista.get(i)[0]);
            entidadPrestadora.setId(Long.parseLong(csvComoLista.get(i)[1]));
            entidadesLeidas.add(entidadPrestadora);
        }
    }
}