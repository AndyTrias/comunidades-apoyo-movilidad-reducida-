package readerCSV;

import configs.Config;
import entidades.Entidad;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class fileStream {

    private String filePath = Config.CSV_PATH;
    private List<Entidad> entidadades = new ArrayList<>();

    public List<Entidad> levantarCSV() {
        try (Stream<String> streamFile = Files.lines(Paths.get(filePath))) {
            entidadades = streamFile.map(Line -> Line.split(",")).map(arreglo -> {
                return new Entidad(arreglo[0]);
            }).collect(Collectors.toList());

            return entidadades;
        } catch (IOException csv) {
            return null;
        }
    }
}
