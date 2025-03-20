package finance.command;

import finance.domain.BankAccount;
import finance.domain.DomainFactory;
import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateAccountCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(CreateAccountCommand.class.getName());

    public CreateAccountCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите название счета: ");
            String name = scanner.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Название не может быть пустым");
            }
            System.out.print("Введите начальный баланс: ");
            double balance = Double.parseDouble(scanner.nextLine());
            BankAccount account = DomainFactory.createBankAccount(name, balance);
            manager.addBankAccount(account);
            System.out.println("Счет создан: " + account);
        } catch (NumberFormatException nfe) {
            logger.log(Level.WARNING, "Неверный формат числа", nfe);
            System.out.println("Ошибка: баланс должен быть числом.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка создания счета", e);
            System.out.println("Ошибка создания счета: " + e.getMessage());
        }
    }
}
