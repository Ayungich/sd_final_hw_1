package finance.importexport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVDataImporter extends DataImporter {

    @Override
    public Map<String, Object> parseData(String data) {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> accounts = new ArrayList<>();
        List<Map<String, Object>> categories = new ArrayList<>();
        List<Map<String, Object>> operations = new ArrayList<>();
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 2) continue;
            String type = parts[0];
            switch (type) {
                case "finance.domain.BankAccount":
                    if (parts.length >= 4) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", parts[1]);
                        map.put("name", parts[2]);
                        map.put("initialBalance", Double.parseDouble(parts[3]));
                        accounts.add(map);
                    }
                    break;
                case "finance.domain.Category":
                    if (parts.length >= 4) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", parts[1]);
                        map.put("type", parts[2]);
                        map.put("name", parts[3]);
                        categories.add(map);
                    }
                    break;
                case "finance.domain.Operation":
                    if (parts.length >= 8) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", parts[1]);
                        map.put("type", parts[2]);
                        map.put("bankAccountId", parts[3]);
                        map.put("amount", Double.parseDouble(parts[4]));
                        map.put("date", parts[5]);
                        map.put("description", parts[6]);
                        map.put("categoryId", parts[7]);
                        operations.add(map);
                    }
                    break;
            }
        }
        result.put("accounts", accounts);
        result.put("categories", categories);
        result.put("operations", operations);
        return result;
    }
}