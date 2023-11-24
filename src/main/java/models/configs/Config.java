package models.configs;

import server.utils.PrettyProperties;

import javax.persistence.*;

@Entity
@Table(name = "configuraciones")
public class Config {
  private static Config instance = null;

  public static Config getInstance() {
    if (instance == null) {
      instance = new Config();
    }
    return instance;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public static final String PEORES_CONTRASENIAS = "src/main/java/models/usuario/Contrasenia/10000Peores.txt";
  public static final String CSV_PATH_ORGANISMOS = "src/main/java/models/readerCSV/organismos_de_control.csv";
  public static final String CSV_PATH_PRESTADORAS = "src/main/java/models/readerCSV/entidades_prestadoras.csv";
  public static final String PATH_INFORMES = "src/main/resources/informes/";
  public static final String API_GEOREF = PrettyProperties.getInstance().propertyFromName("API_GEOREF");

  @Column(name = "latitud_maxima")
  public double LATITUD_MAXIMA = 0.1;

  @Column(name = "longitud_maxima")
  public double LONGITUD_MAXIMA = 0.1;

  @Column(name = "frecuencia_ranking")
  public int FRECUENCIA_RANKING = 10080;

}