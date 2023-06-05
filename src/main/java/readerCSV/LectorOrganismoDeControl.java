package readerCSV;

import configs.Config;
import entidades.OrganismoDeControl;

import java.util.List;

public class LectorOrganismoDeControl {
    private FileStream fileStream = new FileStream();
    private static String ruta = Config.CSV_PATH_ORGANISMOS;

    public void leerCSV() {
        List<String[]> csvComoLista = fileStream.levantarCSV(ruta);
        csvComoLista.forEach(l -> {
            OrganismoDeControl organismo = new OrganismoDeControl(l[1]);
        });
    }

    public static void main(String[] args) {
        LectorOrganismoDeControl lector = new LectorOrganismoDeControl();
        lector.leerCSV();
    }
}
