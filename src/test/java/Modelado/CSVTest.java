package Modelado;

import configs.Config;
import org.junit.jupiter.api.Test;
import readerCSV.LectorEntidadPrestadora;
import readerCSV.LectorOrganismoDeControl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVTest {
    @Test
    public void testLeeTodasLasEntidades() {
        LectorEntidadPrestadora lectorEntidadPrestadora = new LectorEntidadPrestadora();

        lectorEntidadPrestadora.leerCSV(Config.CSV_PATH_PRESTADORAS);

        assertEquals(lectorEntidadPrestadora.getEntidadesLeidas().size(), 3);
    }

    @Test
    public void testLeeTodosLosOrganismos(){
        LectorOrganismoDeControl lectorOrganismoDeControl = new LectorOrganismoDeControl();

        lectorOrganismoDeControl.leerCSV(Config.CSV_PATH_ORGANISMOS);

        assertEquals(lectorOrganismoDeControl.getOrganismosLeidos().size(), 2);
    }
}
