package models.configs;

public class Config {
  private static Config instance = null;

  public static Config getInstance() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

  public static final String PEORES_CONTRASENIAS = "src/main/java/models/usuario/Contrasenia/10000Peores.txt";
  public static final String CSV_PATH_ORGANISMOS = "src/main/java/models/readerCSV/organismos_de_control.csv";
  public static final String CSV_PATH_PRESTADORAS = "src/main/java/models/readerCSV/entidades_prestadoras.csv";
  public static final String PATH_INFORMES = "src/main/java/models/rankings/informes/";

}