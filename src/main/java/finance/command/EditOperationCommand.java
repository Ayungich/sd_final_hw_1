package finance.command;

import finance.domain.Operation;
import finance.service.FinanceManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditOperationCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(EditOperationCommand.class.getName());

    public EditOperationCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID операции для редактирования: ");
            String id = scanner.nextLine();
            Operation target = null;
            for (Operation op : manager.getAllOperations()) {
                if (op.getId().equals(id)) {
                    target = op;
                    break;
                }
            }
            if (target == null) {
                System.out.println("Операция не найдена");
                return;
            }
            System.out.print("Введите новую сумму операции: ");
            double newAmount = Double.parseDouble(scanner.nextLine());
            System.out.print("Введите новую дату операции (например, 2023-03-15T10:15): ");
            String dateStr = scanner.nextLine();
            LocalDateTime newDate;
            try {
                newDate = LocalDateTime.parse(dateStr);
            } catch (DateTimeParseException dtpe) {
                throw new IllegalArgumentException("Неверный формат даты.");
            }
            System.out.print("Введите новое описание операции: ");
            String newDesc = scanner.nextLine();
            target.setAmount(newAmount);
            target.setDate(newDate);
            target.setDescription(newDesc);
            manager.updateOperation(target);
            System.out.println("Операция обновлена: " + target);
        } catch (NumberFormatException nfe) {
            logger.log(Level.WARNING, "Неверный формат числа", nfe);
            System.out.println("Ошибка: сумма должна быть числом.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка редактирования операции", e);
            System.out.println("Ошибка редактирования операции: " + e.getMessage());
        }
    }
}