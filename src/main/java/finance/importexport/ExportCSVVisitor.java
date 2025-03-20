package finance.importexport;

import finance.domain.BankAccount;
import finance.domain.Category;
import finance.domain.Operation;

public class ExportCSVVisitor implements DataExportVisitor {

    private final StringBuilder sb = new StringBuilder();

    @Override
    public void visit(BankAccount account) {
        sb.append("finance.domain.BankAccount,")
                .append(account.getId()).append(",")
                .append(account.getName()).append(",")
                .append(account.getBalance()).append("\n");
    }

    @Override
    public void visit(Category category) {
        sb.append("finance.domain.Category,")
                .append(category.getId()).append(",")
                .append(category.getType()).append(",")
                .append(category.getName()).append("\n");
    }

    @Override
    public void visit(Operation operation) {
        sb.append("finance.domain.Operation,")
                .append(operation.getId()).append(",")
                .append(operation.getType()).append(",")
                .append(operation.getBankAccountId()).append(",")
                .append(operation.getAmount()).append(",")
                .append(operation.getDate()).append(",")
                .append(operation.getDescription() == null ? "" : operation.getDescription()).append(",")
                .append(operation.getCategoryId()).append("\n");
    }

    @Override
    public String getResult() { return sb.toString(); }
}
