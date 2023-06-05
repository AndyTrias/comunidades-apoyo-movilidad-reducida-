package configs;

import comunidades.Rol;

public class Config {
  private static Config instance = null;
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }


    public static final String CSV_PATH = "src/main/java/readerCSV/Instancias.csv";
    public static final Rol ROL_BASE = new Rol("Miembro", null);
    public static final Rol ROL_DUENIO = new Rol("Dueño", null);

}
