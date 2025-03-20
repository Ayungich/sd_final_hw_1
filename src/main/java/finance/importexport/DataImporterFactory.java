package finance.importexport;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DataImporterFactory {

    private final JSONDataImporter jsonImporter;
    private final YAMLDataImporter yamlImporter;
    private final CSVDataImporter csvImporter;

    @Inject
    public DataImporterFactory(JSONDataImporter jsonImporter, YAMLDataImporter yamlImporter, CSVDataImporter csvImporter) {
        this.jsonImporter = jsonImporter;
        this.yamlImporter = yamlImporter;
        this.csvImporter = csvImporter;
    }

    public DataImporter getImporter(String format) {
        if ("CSV".equalsIgnoreCase(format)) {
            return csvImporter;
        } else if ("JSON".equalsIgnoreCase(format)) {
            return jsonImporter;
        } else if ("YAML".equalsIgnoreCase(format)) {
            return yamlImporter;
        } else {
            throw new IllegalArgumentException("Неверный формат импорта: " + format);
        }
    }
}
