package finance.repository;

import finance.domain.BankAccount;
import finance.domain.Category;
import finance.domain.Operation;

import java.util.Collection;

public interface FinanceRepository {
    // Счета
    void addAccount(BankAccount account);
    void updateAccount(BankAccount account);
    void removeAccount(String accountId);
    BankAccount getAccount(String accountId);
    Collection<BankAccount> getAllAccounts();
    // Категории
    void addCategory(Category category);
    void updateCategory(Category category);
    void removeCategory(String categoryId);
    Category getCategory(String categoryId);
    Collection<Category> getAllCategories();
    // Операции
    void addOperation(Operation operation);
    void updateOperation(Operation operation);
    void removeOperation(String operationId);
    Collection<Operation> getAllOperations();
}