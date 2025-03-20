package finance.command;

import finance.domain.BankAccount;
import finance.domain.Category;
import finance.domain.DomainFactory;
import finance.domain.Operation;
import finance.importexport.DataImporter;
import finance.importexport.DataImporterFactory;
import finance.service.FinanceManager;
import finance.types.CategoryType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportDataCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final DataImporterFactory importerFactory;
    private final Logger logger = Logger.getLogger(ImportDataCommand.class.getName());

    public ImportDataCommand(FinanceManager manager, Scanner scanner, DataImporterFactory importerFactory) {
        this.manager = manager;
        this.scanner = scanner;
        this.importerFactory = importerFactory;
    }

    @Override
    public void execute() {
        System.out.print("Введите путь к файлу для импорта: ");
        String filePath = scanner.nextLine();
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("Файл не найден: " + filePath);
            return;
        }
        System.out.print("Выберите формат импорта (CSV/JSON/YAML): ");
        String format = scanner.nextLine().trim().toUpperCase();
        DataImporter importer;
        try {
            importer = importerFactory.getImporter(format);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка выбора импортера", e);
            System.out.println("Ошибка: " + e.getMessage());
            return;
        }
        String data;
        try {
            data = Files.readString(path);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            logger.log(Level.SEVERE, "Ошибка чтения файла", e);
            return;
        }

        Map<String, Object> importedData = importer.parseData(data);

        if (importedData == null || importedData.isEmpty()) {
            System.out.println("Данные пусты или некорректны");
            return;
        }
        try {
            List<Map<String, Object>> accountsList = (List<Map<String, Object>>) importedData.get("accounts");
            if (accountsList != null) {
                for (Map<String, Object> accMap : accountsList) {
                    String name = (String) accMap.get("name");
                    double initialBalance = ((Number) accMap.get("initialBalance")).doubleValue();
                    BankAccount account = DomainFactory.createBankAccount(name, initialBalance);
                    manager.addBankAccount(account);
                }
            }
            List<Map<String, Object>> categoriesList = (List<Map<String, Object>>) importedData.get("categories");
            if (categoriesList != null) {
                for (Map<String, Object> catMap : categoriesList) {
                    String name = (String) catMap.get("name");
                    String typeStr = (String) catMap.get("type");
                    CategoryType type = CategoryType.valueOf(typeStr.toUpperCase());
                    Category category = DomainFactory.createCategory(type, name);
                    manager.addCategory(category);
                }
            }
            List<Map<String, Object>> operationsList = (List<Map<String, Object>>) importedData.get("operations");
            if (operationsList != null) {
                for (Map<String, Object> opMap : operationsList) {
                    String bankAccountId = (String) opMap.get("bankAccountId");
                    String categoryId = (String) opMap.get("categoryId");
                    double amount = ((Number) opMap.get("amount")).doubleValue();
                    String dateStr = (String) opMap.get("date");
                    LocalDateTime date = LocalDateTime.parse(dateStr);
                    String description = (String) opMap.get("description");
                    // Поиск категории по id
                    Category cat = null;
                    for (Category c : manager.getAllCategories()) {
                        if (c.getId().equals(categoryId)) {
                            cat = c;
                            break;
                        }
                    }
                    if (cat == null) continue;
                    Operation op = DomainFactory.createOperation(bankAccountId, cat, amount, date, description);
                    manager.addOperation(op);
                }
            }
            System.out.println("Импорт завершен (если файл корректен).");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка импорта данных", e);
            System.out.println("Ошибка импорта данных: " + e.getMessage());
        }
    }
}