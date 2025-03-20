package finance.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import finance.domain.BankAccount;
import finance.domain.Category;
import finance.domain.Operation;
import finance.importexport.DataExportVisitor;
import finance.repository.FinanceRepository;
import finance.types.OperationType;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
public class FinanceManager {

    private final FinanceRepository repository;
    private final Logger fmLogger = Logger.getLogger(FinanceManager.class.getName());

    @Inject
    public FinanceManager(FinanceRepository repository) {
        this.repository = repository;
    }

    // Методы для работы со счетами
    public void addBankAccount(BankAccount account) {
        if (account == null) throw new IllegalArgumentException("Счет не может быть null");
        repository.addAccount(account);
        fmLogger.info("Добавлен счет: " + account);
    }

    public void updateBankAccount(BankAccount account) {
        if (account == null || repository.getAccount(account.getId()) == null)
            throw new IllegalArgumentException("Счет не найден");
        repository.updateAccount(account);
        fmLogger.info("Обновлен счет: " + account);
    }

    public void removeBankAccount(String accountId) {
        if (repository.getAccount(accountId) == null)
            throw new IllegalArgumentException("Счет не найден");
        repository.removeAccount(accountId);
        fmLogger.info("Удален счет с ID: " + accountId);
    }

    public Collection<BankAccount> getAllBankAccounts() { return repository.getAllAccounts(); }

    public BankAccount getBankAccount(String accountId) { return repository.getAccount(accountId); }

    // Методы для работы с категориями
    public void addCategory(Category category) {
        if (category == null) throw new IllegalArgumentException("Категория не может быть null");
        repository.addCategory(category);
        fmLogger.info("Добавлена категория: " + category);
    }

    public void updateCategory(Category category) {
        if (category == null || repository.getCategory(category.getId()) == null)
            throw new IllegalArgumentException("Категория не найдена");
        repository.updateCategory(category);
        fmLogger.info("Обновлена категория: " + category);
    }

    public void removeCategory(String categoryId) {
        if (repository.getCategory(categoryId) == null)
            throw new IllegalArgumentException("Категория не найдена");
        repository.removeCategory(categoryId);
        fmLogger.info("Удалена категория с ID: " + categoryId);
    }

    public Collection<Category> getAllCategories() { return repository.getAllCategories(); }

    // Методы для работы с операциями
    public void addOperation(Operation operation) {
        if (operation == null) throw new IllegalArgumentException("Операция не может быть null");
        if (repository.getAccount(operation.getBankAccountId()) == null)
            throw new IllegalArgumentException("Счет не найден");
        if (repository.getCategory(operation.getCategoryId()) == null)
            throw new IllegalArgumentException("Категория не найдена");
        repository.addOperation(operation);
        // Обновляем баланс счета
        BankAccount account = repository.getAccount(operation.getBankAccountId());
        try {
            if (operation.getType() == OperationType.INCOME) {
                account.deposit(operation.getAmount());
            } else {
                account.withdraw(operation.getAmount());
            }
            repository.updateAccount(account);
            fmLogger.info("Добавлена операция: " + operation);
        } catch (Exception e) {
            fmLogger.log(Level.WARNING, "Ошибка обновления баланса", e);
        }
    }

    public void updateOperation(Operation operation) {
        if (operation == null)
            throw new IllegalArgumentException("Операция не может быть null");
        if (repository.getAllOperations().stream().noneMatch(op -> op.getId().equals(operation.getId())))
            throw new IllegalArgumentException("Операция не найдена");
        repository.updateOperation(operation);
        fmLogger.info("Обновлена операция: " + operation);
    }

    public void removeOperation(String operationId) {
        if (repository.getAllOperations().stream().noneMatch(op -> op.getId().equals(operationId)))
            throw new IllegalArgumentException("Операция не найдена");
        repository.removeOperation(operationId);
        fmLogger.info("Удалена операция с ID: " + operationId);
    }

    public Collection<Operation> getAllOperations() { return repository.getAllOperations(); }

    // Аналитика
    public double calculateNetDifference(LocalDateTime from, LocalDateTime to) {
        double net = 0;
        for (Operation op : repository.getAllOperations()) {
            if (op.getDate().isAfter(from) && op.getDate().isBefore(to)) {
                net += (op.getType() == OperationType.INCOME) ? op.getAmount() : -op.getAmount();
            }
        }
        return net;
    }

    public Map<Category, List<Operation>> groupOperationsByCategory(LocalDateTime from, LocalDateTime to) {
        Map<Category, List<Operation>> result = new HashMap<>();
        for (Operation op : repository.getAllOperations()) {
            if (op.getDate().isAfter(from) && op.getDate().isBefore(to)) {
                Category cat = repository.getCategory(op.getCategoryId());
                if (cat != null) {
                    result.putIfAbsent(cat, new ArrayList<>());
                    result.get(cat).add(op);
                }
            }
        }
        return result;
    }

    // Экспорт данных для JSON/YAML
    public Map<String, Object> exportDataAsMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("accounts", new ArrayList<>(repository.getAllAccounts()));
        data.put("categories", new ArrayList<>(repository.getAllCategories()));
        data.put("operations", new ArrayList<>(repository.getAllOperations()));
        return data;
    }

    // Экспорт через Visitor
    public String exportData(DataExportVisitor visitor) {
        for (BankAccount account : repository.getAllAccounts()) { account.accept(visitor); }
        for (Category category : repository.getAllCategories()) { category.accept(visitor); }
        for (Operation operation : repository.getAllOperations()) { operation.accept(visitor); }
        return visitor.getResult();
    }

    // Пересчет баланса счета
    public void recalcBalance(String accountId) {
        BankAccount account = repository.getAccount(accountId);
        if (account == null)
            throw new IllegalArgumentException("Счет не найден");
        double newBalance = account.getInitialBalance();
        for (Operation op : repository.getAllOperations()) {
            if (op.getBankAccountId().equals(accountId)) {
                newBalance += (op.getType() == OperationType.INCOME) ? op.getAmount() : -op.getAmount();
            }
        }
        account.setBalance(newBalance);
        repository.updateAccount(account);
        fmLogger.info("Пересчитан баланс для счета " + accountId + ". Новый баланс: " + account.getBalance());
    }
}