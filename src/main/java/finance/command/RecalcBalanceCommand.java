package finance.command;

import finance.domain.BankAccount;
import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecalcBalanceCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(RecalcBalanceCommand.class.getName());

    public RecalcBalanceCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID счета для пересчета баланса: ");
            String accountId = scanner.nextLine();
            manager.recalcBalance(accountId);
            BankAccount account = manager.getBankAccount(accountId);
            System.out.println("Новый баланс счета: " + account.getBalance());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка пересчета баланса", e);
            System.out.println("Ошибка пересчета баланса: " + e.getMessage());
        }
    }
}
