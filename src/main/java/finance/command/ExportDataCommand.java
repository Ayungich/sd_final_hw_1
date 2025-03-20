package finance.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import finance.importexport.ExportCSVVisitor;
import finance.service.FinanceManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportDataCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(ExportDataCommand.class.getName());

    public ExportDataCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Выберите формат экспорта (CSV/JSON/YAML): ");
            String format = scanner.nextLine().trim().toUpperCase();
            String data;
            switch (format) {
                case "CSV":
                    ExportCSVVisitor visitor = new ExportCSVVisitor();
                    data = manager.exportData(visitor);
                    break;
                case "JSON":
                    ObjectMapper jsonMapper = new ObjectMapper();
                    jsonMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                    jsonMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    data = jsonMapper.writerWithDefaultPrettyPrinter()
                            .writeValueAsString(manager.exportDataAsMap());
                    break;
                case "YAML":
                    YAMLMapper yamlMapper = new YAMLMapper();
                    yamlMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                    yamlMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    data = yamlMapper.writeValueAsString(manager.exportDataAsMap());
                    break;
                default:
                    System.out.println("Неверный формат экспорта");
                    return;
            }
            System.out.print("Введите путь для сохранения файла: ");
            String filePath = scanner.nextLine();
            try {
                Files.writeString(Paths.get(filePath), data);
                System.out.println("Экспорт успешно сохранён в файл " + filePath);
            } catch (IOException e) {
                System.out.println("Ошибка сохранения файла: " + e.getMessage());
                logger.log(Level.SEVERE, "Ошибка записи файла", e);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка экспорта данных", e);
            System.out.println("Ошибка экспорта: " + e.getMessage());
        }
    }
}