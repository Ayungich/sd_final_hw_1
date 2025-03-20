package finance.importexport;

import finance.domain.BankAccount;
import finance.domain.Category;
import finance.domain.Operation;

public interface DataExportVisitor {
    void visit(BankAccount account);
    void visit(Category category);
    void visit(Operation operation);
    String getResult();
}