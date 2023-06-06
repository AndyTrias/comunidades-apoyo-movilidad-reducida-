package readerCSV;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
public class FileStream {

    public List<String[]> levantarCSV(String ruta) {
        try (Stream<String> streamFile = Files.lines(Paths.get(ruta))) {
            return streamFile.map(line -> line.split(";")).toList();
        } catch (IOException csv) {
            return null;
        }
    }

}
