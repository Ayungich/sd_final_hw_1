package finance.command;

import finance.domain.BankAccount;
import finance.service.FinanceManager;

import java.util.Collection;

public class ListAccountsCommand implements Command {

    private final FinanceManager manager;

    public ListAccountsCommand(FinanceManager manager) { this.manager = manager; }

    @Override
    public void execute() {
        Collection<BankAccount> accounts = manager.getAllBankAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Нет созданных счетов");
        } else {
            System.out.println("Счета:");
            accounts.forEach(System.out::println);
        }
    }
}
