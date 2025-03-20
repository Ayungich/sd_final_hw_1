package finance.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import finance.domain.BankAccount;
import finance.domain.Category;
import finance.domain.Operation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class CachedFinanceRepository implements FinanceRepository {

    private final InMemoryFinanceRepository delegate;
    private final Map<String, BankAccount> accountCache = new HashMap<>();
    private final Map<String, Category> categoryCache = new HashMap<>();

    @Inject
    public CachedFinanceRepository(InMemoryFinanceRepository delegate) {
        this.delegate = delegate;
    }

    // Счета
    @Override
    public void addAccount(BankAccount account) {
        delegate.addAccount(account);
        accountCache.put(account.getId(), account);
    }

    @Override
    public void updateAccount(BankAccount account) {
        delegate.updateAccount(account);
        accountCache.put(account.getId(), account);
    }

    @Override
    public void removeAccount(String accountId) {
        delegate.removeAccount(accountId);
        accountCache.remove(accountId);
    }

    @Override
    public BankAccount getAccount(String accountId) {
        if (accountCache.containsKey(accountId)) {
            return accountCache.get(accountId);
        }
        BankAccount account = delegate.getAccount(accountId);
        if (account != null) {
            accountCache.put(accountId, account);
        }
        return account;
    }

    @Override
    public Collection<BankAccount> getAllAccounts() {
        Collection<BankAccount> accounts = delegate.getAllAccounts();
        // Обновляем кэш
        accounts.forEach(acc -> accountCache.put(acc.getId(), acc));
        return accounts;
    }

    // Категории
    @Override
    public void addCategory(Category category) {
        delegate.addCategory(category);
        categoryCache.put(category.getId(), category);
    }

    @Override
    public void updateCategory(Category category) {
        delegate.updateCategory(category);
        categoryCache.put(category.getId(), category);
    }

    @Override
    public void removeCategory(String categoryId) {
        delegate.removeCategory(categoryId);
        categoryCache.remove(categoryId);
    }

    @Override
    public Category getCategory(String categoryId) {
        if (categoryCache.containsKey(categoryId)) {
            return categoryCache.get(categoryId);
        }
        Category category = delegate.getCategory(categoryId);
        if (category != null) {
            categoryCache.put(categoryId, category);
        }
        return category;
    }

    @Override
    public Collection<Category> getAllCategories() {
        Collection<Category> cats = delegate.getAllCategories();
        cats.forEach(cat -> categoryCache.put(cat.getId(), cat));
        return cats;
    }

    // Операции
    @Override
    public void addOperation(Operation operation) { delegate.addOperation(operation); }

    @Override
    public void updateOperation(Operation operation) { delegate.updateOperation(operation); }

    @Override
    public void removeOperation(String operationId) { delegate.removeOperation(operationId); }

    @Override
    public Collection<Operation> getAllOperations() { return delegate.getAllOperations(); }
}
