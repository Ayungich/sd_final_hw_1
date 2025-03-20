package finance.importexport;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Map;

public class JSONDataImporter extends DataImporter {

    @Override
    public Map<String, Object> parseData(String data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(data, Map.class);
        } catch (Exception e) {
            System.out.println("Ошибка парсинга JSON: " + e.getMessage());
            return Collections.emptyMap();
        }
    }
}
