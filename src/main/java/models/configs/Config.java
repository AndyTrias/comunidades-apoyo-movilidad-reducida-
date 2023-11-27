package models.configs;

import models.repositorios.RepoConfig;
import server.utils.PrettyProperties;

import javax.persistence.*;

@Entity
@Table(name = "configuraciones")
public class Config {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "path_peores_contrasenias")
  public final String PEORES_CONTRASENIAS = "src/main/resources/public/10000Peores.txt";
  @Column(name = "path_csv_organismos")
  public final String CSV_PATH_ORGANISMOS = "src/main/java/models/readerCSV/organismos_de_control.csv";
  @Column(name = "path_csv_prestadoras")
  public final String CSV_PATH_PRESTADORAS = "src/main/java/models/readerCSV/entidades_prestadoras.csv";
  @Column(name = "path_informes")
  public final String PATH_INFORMES = "src/main/resources/informes/";
  @Column(name = "api_georef")
  public final String API_GEOREF = PrettyProperties.getInstance().propertyFromName("API_GEOREF");
  @Column(name = "latitud_maxima")
  public double LATITUD_MAXIMA = 0.1;
  @Column(name = "longitud_maxima")
  public double LONGITUD_MAXIMA = 0.1;
  @Column(name = "frecuencia_ranking")
  public int FRECUENCIA_RANKING = 10080;

  private static Config INSTANCE = null;

  public static Config getInstance() {
    if (INSTANCE == null) {
      try {
        Config config = RepoConfig.INSTANCE.buscar(1L);
        if (config == null) {
          config = new Config();
          RepoConfig.INSTANCE.agregar(config);
        }
        INSTANCE = config;
        return config;
      } catch (Exception e) {
        return new Config();
      }
    }
    return INSTANCE;
  }

}