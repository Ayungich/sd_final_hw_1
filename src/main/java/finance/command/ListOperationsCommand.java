package finance.command;

import finance.domain.Operation;
import finance.service.FinanceManager;

import java.util.Collection;

public class ListOperationsCommand implements Command {

    private final FinanceManager manager;

    public ListOperationsCommand(FinanceManager manager) { this.manager = manager; }

    @Override
    public void execute() {
        Collection<Operation> operations = manager.getAllOperations();
        if (operations.isEmpty()) {
            System.out.println("Нет созданных операций");
        } else {
            System.out.println("Операции:");
            operations.forEach(System.out::println);
        }
    }
}

