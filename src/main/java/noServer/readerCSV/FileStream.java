package noServer.readerCSV;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
public abstract class FileStream {
    public void leerCSV(String ruta){
        List<String[]> csvComoLista = csvALista(ruta);
        levantarObjetos(csvComoLista);
    }


    protected List<String[]> csvALista(String ruta) {
        try (Stream<String> streamFile = Files.lines(Paths.get(ruta))) {
            return streamFile.map(line -> line.split(";")).toList();
        } catch (IOException csv) {
            return null;
        }
    }

    protected abstract void levantarObjetos(List<String[]> csvComoLista);

}
