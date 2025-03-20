package finance.command;

import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteAccountCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(DeleteAccountCommand.class.getName());

    public DeleteAccountCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID счета для удаления: ");
            String id = scanner.nextLine();
            manager.removeBankAccount(id);
            System.out.println("Счет удален.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка удаления счета", e);
            System.out.println("Ошибка удаления счета: " + e.getMessage());
        }
    }
}
