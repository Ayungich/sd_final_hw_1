package finance.domain;

import finance.importexport.DataExportVisitor;

public class BankAccount implements Visitable {
    private final String id;
    private String name;
    private double balance;
    private final double initialBalance;

    public BankAccount(String id, String name, double initialBalance) {
        this.id = id;
        this.name = name;
        this.balance = initialBalance;
        this.initialBalance = initialBalance;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
    public double getInitialBalance() { return initialBalance; }

    public void setName(String name) { this.name = name; }
    public void setBalance(double newBalance) { this.balance = newBalance; }

    public void deposit(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Сумма должна быть положительной");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Сумма должна быть положительной");
        if (amount > balance) throw new IllegalArgumentException("Недостаточно средств");
        balance -= amount;
    }

    @Override
    public void accept(DataExportVisitor visitor) { visitor.visit(this); }

    @Override
    public String toString() {
        return "finance.domain.BankAccount{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}