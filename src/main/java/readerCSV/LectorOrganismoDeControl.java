package readerCSV;

import configs.Config;
import entidades.OrganismoDeControl;

import java.util.List;

public class LectorOrganismoDeControl {
    private FileStream fileStream = new FileStream();
    private static String ruta = Config.CSV_PATH_ORGANISMOS;

    public void leerCSV() {
        List<String[]> csvComoLista = fileStream.levantarCSV(ruta);
        for (int i = 1; i < csvComoLista.size(); i++) {
            OrganismoDeControl organismoDeControl = new OrganismoDeControl(csvComoLista.get(i)[0]);
            System.out.println(organismoDeControl.getNombre());
        }
    }

}
