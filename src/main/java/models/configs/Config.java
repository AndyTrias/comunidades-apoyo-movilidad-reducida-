package models.configs;

import models.repositorios.RepoConfig;

import javax.persistence.*;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "configuraciones")
public class Config {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "path_peores_contrasenias")
  public final String PEORES_CONTRASENIAS = "src/main/resources/public/10000Peores.txt";
  @Column(name = "path_csv_organismos")
  public final String CSV_PATH_ORGANISMOS = "resources/public/organismos_de_control.csv";
  @Column(name = "path_csv_prestadoras")
  public final String CSV_PATH_PRESTADORAS = "resources/public/entidades_prestadoras.csv";
  @Column(name = "path_informes")
  public final String PATH_INFORMES = "src/main/resources/public/informes/";
  @Column(name = "api_georef")
  public final String API_GEOREF = System.getenv("API_GEOREF");
  @Column(name = "latitud_maxima")
  public double LATITUD_MAXIMA = 0.1;
  @Column(name = "longitud_maxima")
  public double LONGITUD_MAXIMA = 0.1;
  @Column(name = "frecuencia_ranking")
  public int FRECUENCIA_RANKING = 10080;
  @Column(name = "unidad_frecuencia_ranking")
  public String UNIDAD_FRECUENCIA_RANKING = TimeUnit.MINUTES.name();

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