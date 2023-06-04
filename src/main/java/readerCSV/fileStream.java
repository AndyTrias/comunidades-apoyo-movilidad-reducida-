package readerCSV;

import comunidades.Usuario;
import entidades.Entidad;
import localizacion.Localizacion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class fileStream {

    private String filePath = "src/main/java/readerCSV/Instancias.csv";
    private List<Entidad> entidadades = new ArrayList<>();

    public List<String> levantarCSV() {
        try (Stream<String> streamFile = Files.lines(Paths.get(filePath))) {
            entidadades = streamFile.map(Line -> Line.split(",")).map(arreglo -> {
                return new Entidad(arreglo[0]);
            }).collect(Collectors.toList());

            return streamFile.collect(Collectors.toList());
        } catch (IOException csv) {
            return null;
        }
    }
}
