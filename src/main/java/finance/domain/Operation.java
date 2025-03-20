package finance.domain;

import finance.importexport.DataExportVisitor;
import finance.types.OperationType;

import java.time.LocalDateTime;

public class Operation implements Visitable {

    private final String id;
    private final OperationType type;
    private final String bankAccountId;
    private double amount;
    private LocalDateTime date;
    private String description;
    private final String categoryId;

    public Operation(String id, OperationType type, String bankAccountId, double amount,
                     LocalDateTime date, String description, String categoryId) {
        this.id = id;
        this.type = type;
        this.bankAccountId = bankAccountId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getId() { return id; }
    public OperationType getType() { return type; }
    public String getBankAccountId() { return bankAccountId; }
    public double getAmount() { return amount; }
    public LocalDateTime getDate() { return date; }
    public String getDescription() { return description; }
    public String getCategoryId() { return categoryId; }

    public void setAmount(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Сумма должна быть положительной");
        this.amount = amount;
    }

    public void setDate(LocalDateTime date) {
        if (date == null) throw new IllegalArgumentException("Дата не может быть null");
        this.date = date;
    }

    public void setDescription(String description) { this.description = description; }

    @Override
    public void accept(DataExportVisitor visitor) { visitor.visit(this); }

    @Override
    public String toString() {
        return "finance.domain.Operation{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", bankAccountId='" + bankAccountId + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}