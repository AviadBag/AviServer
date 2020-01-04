import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileReader {
    private File file;

    public FileReader(String fileName) {
        this.file = new File(fileName);
    }

    public String read() throws IOException {
        StringBuilder data = new StringBuilder();
        List<String> lines = Files.readAllLines( this.file.toPath() );
        for (String line : lines) {
            data.append(line);
        }

        return data.toString();
    }
}
