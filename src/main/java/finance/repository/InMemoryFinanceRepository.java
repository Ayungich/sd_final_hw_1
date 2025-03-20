package finance.repository;

import com.google.inject.Singleton;
import finance.domain.BankAccount;
import finance.domain.Category;
import finance.domain.Operation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryFinanceRepository implements FinanceRepository {

    private final Map<String, BankAccount> accounts = new HashMap<>();
    private final Map<String, Category> categories = new HashMap<>();
    private final Map<String, Operation> operations = new HashMap<>();

    // Счета
    @Override
    public void addAccount(BankAccount account) { accounts.put(account.getId(), account); }

    @Override
    public void updateAccount(BankAccount account) { accounts.put(account.getId(), account); }

    @Override
    public void removeAccount(String accountId) {
        accounts.remove(accountId);
        operations.values().removeIf(op -> op.getBankAccountId().equals(accountId));
    }

    @Override
    public BankAccount getAccount(String accountId) { return accounts.get(accountId); }

    @Override
    public Collection<BankAccount> getAllAccounts() { return accounts.values(); }

    // Категории
    @Override
    public void addCategory(Category category) { categories.put(category.getId(), category); }

    @Override
    public void updateCategory(Category category) { categories.put(category.getId(), category); }

    @Override
    public void removeCategory(String categoryId) {
        categories.remove(categoryId);
        operations.values().removeIf(op -> op.getCategoryId().equals(categoryId));
    }

    @Override
    public Category getCategory(String categoryId) { return categories.get(categoryId); }

    @Override
    public Collection<Category> getAllCategories() { return categories.values(); }

    // Операции
    @Override
    public void addOperation(Operation operation) { operations.put(operation.getId(), operation); }

    @Override
    public void updateOperation(Operation operation) { operations.put(operation.getId(), operation); }

    @Override
    public void removeOperation(String operationId) { operations.remove(operationId); }

    @Override
    public Collection<Operation> getAllOperations() { return operations.values(); }

}
