package finance.domain;

import finance.types.CategoryType;
import finance.types.OperationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class DomainFactory {

    public static BankAccount createBankAccount(String name, double initialBalance) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Имя счета не может быть пустым");
        if (initialBalance < 0)
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        String id = UUID.randomUUID().toString();
        return new BankAccount(id, name, initialBalance);
    }

    public static Category createCategory(CategoryType type, String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Имя категории не может быть пустым");
        String id = UUID.randomUUID().toString();
        return new Category(id, type, name);
    }

    public static Operation createOperation(String bankAccountId, Category category, double amount,
                                            LocalDateTime date, String description) {
        if (bankAccountId == null || bankAccountId.trim().isEmpty())
            throw new IllegalArgumentException("ID счета не может быть пустым");
        if (category == null)
            throw new IllegalArgumentException("Категория не может быть null");
        if (amount <= 0)
            throw new IllegalArgumentException("Сумма операции должна быть положительной");
        if (date == null)
            throw new IllegalArgumentException("Дата операции не может быть null");
        String id = UUID.randomUUID().toString();
        OperationType type = (category.getType() == CategoryType.INCOME)
                ? OperationType.INCOME : OperationType.EXPENSE;
        return new Operation(id, type, bankAccountId, amount, date, description, category.getId());
    }
}
