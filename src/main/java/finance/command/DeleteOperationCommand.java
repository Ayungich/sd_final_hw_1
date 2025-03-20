package finance.command;

import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteOperationCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(DeleteOperationCommand.class.getName());

    public DeleteOperationCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID операции для удаления: ");
            String id = scanner.nextLine();
            manager.removeOperation(id);
            System.out.println("Операция удалена.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка удаления операции", e);
            System.out.println("Ошибка удаления операции: " + e.getMessage());
        }
    }
}