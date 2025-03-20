package finance.importexport;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Collections;
import java.util.Map;

public class YAMLDataImporter extends DataImporter {

    @Override
    public Map<String, Object> parseData(String data) {
        try {
            YAMLMapper mapper = new YAMLMapper();
            return mapper.readValue(data, Map.class);
        } catch (Exception e) {
            System.out.println("Ошибка парсинга YAML: " + e.getMessage());
            return Collections.emptyMap();
        }
    }
}