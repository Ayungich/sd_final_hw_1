package finance.command;

import finance.domain.Category;
import finance.domain.DomainFactory;
import finance.domain.Operation;
import finance.service.FinanceManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateOperationCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(CreateOperationCommand.class.getName());

    public CreateOperationCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID счета: ");
            String accountId = scanner.nextLine();
            System.out.print("Введите ID категории: ");
            String categoryId = scanner.nextLine();
            Category category = null;
            for (Category cat : manager.getAllCategories()) {
                if (cat.getId().equals(categoryId)) {
                    category = cat;
                    break;
                }
            }
            if (category == null) {
                System.out.println("Категория не найдена");
                return;
            }
            System.out.print("Введите сумму операции: ");
            double amount = Double.parseDouble(scanner.nextLine());
            System.out.print("Введите дату операции (например, 2023-03-15T10:15): ");
            String dateStr = scanner.nextLine();
            LocalDateTime date;
            try {
                date = LocalDateTime.parse(dateStr);
            } catch (DateTimeParseException dtpe) {
                throw new IllegalArgumentException("Неверный формат даты. Используйте ISO-8601 (например, 2023-03-15T10:15).");
            }
            System.out.print("Введите описание операции (опционально): ");
            String description = scanner.nextLine();
            Operation operation = DomainFactory.createOperation(accountId, category, amount, date, description);
            manager.addOperation(operation);
            System.out.println("Операция создана: " + operation);
        } catch (NumberFormatException nfe) {
            logger.log(Level.WARNING, "Неверный формат числа", nfe);
            System.out.println("Ошибка: сумма должна быть числом.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка создания операции", e);
            System.out.println("Ошибка создания операции: " + e.getMessage());
        }
    }
}