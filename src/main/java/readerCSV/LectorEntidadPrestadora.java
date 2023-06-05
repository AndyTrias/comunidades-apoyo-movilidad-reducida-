package readerCSV;

import configs.Config;
import entidades.EntidadPrestadora;

import java.util.List;

public class LectorEntidadPrestadora {
    private FileStream fileStream = new FileStream();
    private static String ruta = Config.CSV_PATH_PRESTADORAS;

    public void leerCSV() {
        List<String[]> csvComoLista = fileStream.levantarCSV(ruta);
        for (int i = 1; i < csvComoLista.size(); i++) {
            EntidadPrestadora entidadPrestadora = new EntidadPrestadora(csvComoLista.get(i)[0], Integer.parseInt(csvComoLista.get(i)[1]));
        }
    }
}
