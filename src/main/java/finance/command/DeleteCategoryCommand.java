package finance.command;

import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCategoryCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(DeleteCategoryCommand.class.getName());

    public DeleteCategoryCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID категории для удаления: ");
            String id = scanner.nextLine();
            manager.removeCategory(id);
            System.out.println("Категория удалена.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка удаления категории", e);
            System.out.println("Ошибка удаления категории: " + e.getMessage());
        }
    }
}

