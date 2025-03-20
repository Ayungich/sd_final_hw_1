package finance.command;

import finance.domain.BankAccount;
import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditAccountCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(EditAccountCommand.class.getName());

    public EditAccountCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID счета для редактирования: ");
            String id = scanner.nextLine();
            BankAccount account = manager.getBankAccount(id);
            if (account == null) {
                System.out.println("Счет не найден");
                return;
            }
            System.out.print("Введите новое название счета: ");
            String newName = scanner.nextLine();
            if (newName == null || newName.trim().isEmpty()) {
                throw new IllegalArgumentException("Название не может быть пустым");
            }
            account.setName(newName);
            manager.updateBankAccount(account);
            System.out.println("Счет обновлен: " + account);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка редактирования счета", e);
            System.out.println("Ошибка редактирования счета: " + e.getMessage());
        }
    }
}
