package Modelado;

import models.configs.Config;
import org.junit.jupiter.api.Test;
import models.readerCSV.LectorEntidadPrestadora;
import models.readerCSV.LectorOrganismoDeControl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVTest {
    @Test
    public void testLeeTodasLasEntidades() {
        LectorEntidadPrestadora lectorEntidadPrestadora = new LectorEntidadPrestadora();

        lectorEntidadPrestadora.leerCSV(Config.getInstance().CSV_PATH_PRESTADORAS);

        assertEquals(lectorEntidadPrestadora.getEntidadesLeidas().size(), 3);
    }

    @Test
    public void testLeeTodosLosOrganismos(){
        LectorOrganismoDeControl lectorOrganismoDeControl = new LectorOrganismoDeControl();

        lectorOrganismoDeControl.leerCSV(Config.getInstance().CSV_PATH_ORGANISMOS);

        assertEquals(lectorOrganismoDeControl.getOrganismosLeidos().size(), 3);
    }
}
