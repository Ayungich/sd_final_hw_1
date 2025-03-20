package finance.importexport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public abstract class DataImporter {

    public final Map<String, Object> importData(String filePath) {
        String data = readFile(filePath);
        if (data == null) {
            System.out.println("Ошибка чтения файла: " + filePath);
            return Collections.emptyMap();
        }
        return parseData(data);
    }

    private String readFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        }
    }

    public abstract Map<String, Object> parseData(String data);
}