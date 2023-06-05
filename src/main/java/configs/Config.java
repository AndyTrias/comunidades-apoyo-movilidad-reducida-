package configs;

import comunidades.Rol;
import comunidades.usuario.Contrasenia.ValidadorDeContrasenia;

public class Config {
  private static Config instance = null;
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static final String CSV_PATH_ORGANISMOS = "src/main/java/readerCSV/organismos_de_control.csv";
    public static final String CSV_PATH_PRESTADORAS = "src/main/java/readerCSV/entidades_prestadoras.csv";
    public static final Rol ROL_BASE = new Rol("Miembro", null);
    public static final ValidadorDeContrasenia VALIDADOR = new ValidadorDeContrasenia();
}
